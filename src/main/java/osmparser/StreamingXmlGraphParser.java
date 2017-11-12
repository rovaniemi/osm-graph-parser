package osmparser;

import com.fasterxml.aalto.stax.InputFactoryImpl;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class StreamingXmlGraphParser implements GraphParser {

    private final XMLInputFactory inputFactory;
    private final WayTag[] includedWayTags;
    private final WayTag[] excludedWayTags;
    private Graph graph;

    public StreamingXmlGraphParser(WayTag[] includedWayTags, WayTag[] excludedWayTags) {
        this.inputFactory = InputFactoryImpl.newFactory();
        this.includedWayTags = includedWayTags;
        this.excludedWayTags = excludedWayTags;
    }

    @Override
    public void parseXml(File file, Graph outputGraph) {
        this.graph = outputGraph;

        XMLStreamReader reader = null;
        try (InputStream stream = new FileInputStream(file)) {
            reader = inputFactory.createXMLStreamReader(stream);
            readGraph(reader);
        } catch (IOException | XMLStreamException ex) {
            throw new RuntimeException(ex);
        } finally {
            safeClose(reader);
        }
    }

    private void readGraph(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType != XMLEvent.START_ELEMENT) {
                continue;
            }

            String elementName = reader.getLocalName();
            if (elementName.equals("node")) {
                readNodeElement(reader);
            } else if (elementName.equals("way")) {
                readWayElement(reader);
            }
        }
    }

    private void readNodeElement(XMLStreamReader reader) {
        long id = Long.parseLong(reader.getAttributeValue(null, "id"));
        double lat = Double.parseDouble(reader.getAttributeValue(null,  "lat"));
        double lon = Double.parseDouble(reader.getAttributeValue(null, "lon"));
        Node node = new Node(id, lat, lon);
        this.graph.addNode(node);
    }

    private void readWayElement(XMLStreamReader reader) throws XMLStreamException {
        ArrayList<Long> wayNodesIds = new ArrayList<>(1024);
        HashMap<String, String> wayTags = new HashMap<>(64);
        readIdsAndTags(reader, wayNodesIds, wayTags);

        if (!containsAnyTargetTags(wayTags, excludedWayTags)
            && containsAnyTargetTags(wayTags, includedWayTags)
        ) {
            addEdgesToGraph(wayNodesIds);
        }
    }

    private static void readIdsAndTags(XMLStreamReader reader,
                                       ArrayList<Long> nodesOut,
                                       HashMap<String, String> tagsOut) throws XMLStreamException {
        // when we're here, reader cursor is currently at START_ELEMENT, that is, <way>
        int nodeChildDepth = 1;

        while (reader.hasNext()) {
            int eventType = reader.next();

            if (eventType == XMLEvent.START_ELEMENT) {
                nodeChildDepth++;

                String elementName = reader.getLocalName();
                if ("nd".equals(elementName)) {
                    String ndRefAttribute = reader.getAttributeValue(null, "ref");
                    Long id = Long.parseLong(ndRefAttribute);
                    nodesOut.add(id);
                } else if ("tag".equals(elementName)) {
                    // according to the schema, "k" and "v" are required
                    String tagKeyAttribute = reader.getAttributeValue(null, "k");
                    String tagKeyValue = reader.getAttributeValue(null, "v");
                    tagsOut.put(tagKeyAttribute, tagKeyValue);
                }
            } else if (eventType == XMLEvent.END_ELEMENT) {
                nodeChildDepth--;

                String elementName = reader.getLocalName();
                if (nodeChildDepth == 0 && "way".equals(elementName)) {
                    return;
                }
                if (nodeChildDepth <= 0) {
                    // Found the end of an XML tag but it was not </way> even though it should
                    // have been at this level, e.g. <way><nd /></notWayTag>
                    Location location = reader.getLocation();
                    String msg = String.format(
                        "Found unbalanced XML tags at line %d col %d, file character pos %d! Expecting </way> but was </%s>",
                        location.getLineNumber(),
                        location.getColumnNumber(),
                        location.getCharacterOffset(),
                        elementName
                    );
                    throw new RuntimeException(msg);
                }
            }
        }

        throw new RuntimeException("Reached end of XMLStream inside readIdsAndTags! Expected </way>!");
    }

    private static boolean containsAnyTargetTags(HashMap<String, String> foundTags, WayTag[] targetTags) {
        for (WayTag tag : targetTags) {
            String targetKey = tag.getTagKey();
            String targetValue = tag.getTagValue(); // may be null

            // foundValue is guaranteed to be non-null if the key was found
            String foundValue = foundTags.get(targetKey);
            if (foundValue != null && (targetMatchesAnyValue(targetValue) || targetMatchesValue(targetValue, foundValue))) {
                return true;
            }
        }

        return false;
    }

    private static boolean targetMatchesValue(String targetValue, String foundValue) {
        return Objects.equals(targetValue, foundValue);
    }

    private static boolean targetMatchesAnyValue(String targetValue) {
        return targetValue == null;
    }

    private void addEdgesToGraph(ArrayList<Long> edges){
        long size = edges.size();
        for (int i = 0; i < size; i++) {
            if (i == 0 && size > 1) {
                graph.addEdge(edges.get(i), edges.get(i + 1));
            } else if(i > 0 && i < size - 1){
                graph.addEdge(edges.get(i), edges.get(i - 1));
                graph.addEdge(edges.get(i), edges.get(i + 1));
            } else if (i == size - 1 && size > 1){
                graph.addEdge(edges.get(i), edges.get(i - 1));
            }
        }
    }

    private static void safeClose(XMLStreamReader reader) {
        if (reader == null)
            return;

        try {
            reader.close();
        } catch (Exception ex) {
        }
    }
}
