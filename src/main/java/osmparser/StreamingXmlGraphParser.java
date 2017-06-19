package osmparser;

import com.fasterxml.aalto.stax.InputFactoryImpl;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class StreamingXmlGraphParser implements IGraphParser {

    private final XMLInputFactory inputFactory;
    private final String[] requiredWayTags;
    private Graph graph;

    public StreamingXmlGraphParser(String... requiredWayTags) {
        this.inputFactory = InputFactoryImpl.newFactory();
        this.requiredWayTags = requiredWayTags;
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
        HashSet<String> wayTags = new HashSet<>(64);
        readIdsAndTags(reader, wayNodesIds, wayTags);

        if (containsAllRequiredTags(wayTags)) {
            addEdgesToGraph(wayNodesIds);
        }
    }

    private static void readIdsAndTags(XMLStreamReader reader,
                                       ArrayList<Long> nodesOut,
                                       HashSet<String> tagsOut) throws XMLStreamException {
        // when we're here, reader cursor is currently at START_ELEMENT, that is, <way>
        int nodeChildDepth = 1;

        while (reader.hasNext()) {
            int eventType = reader.next();

            if (eventType == XMLEvent.START_ELEMENT) {
                nodeChildDepth++;

                String elementName = reader.getLocalName();
                if (elementName.equals("nd")) {
                    String ndRefAttribute = reader.getAttributeValue(null, "ref");
                    Long id = Long.parseLong(ndRefAttribute);
                    nodesOut.add(id);
                } else if (elementName.equals("tag")) {
                    String tagKeyAttribute = reader.getAttributeValue(null, "k");
                    tagsOut.add(tagKeyAttribute);
                }
            } else if (eventType == XMLEvent.END_ELEMENT) {
                nodeChildDepth--;

                String elementName = reader.getLocalName();
                if (nodeChildDepth == 0 && elementName.equals("way")) {
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

    private boolean containsAllRequiredTags(HashSet<String> foundTags) {
        for (int i = 0; i < requiredWayTags.length; i++) {
            if (!foundTags.contains(requiredWayTags[i])) {
                return false;
            }
        }
        return true;
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
