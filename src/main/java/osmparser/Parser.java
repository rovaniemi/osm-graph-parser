package osmparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;

public class Parser {

    private final String filename = "map";

    private XmlReader xmlReader;
    private Graph graph;
    private List<String> tags;

    public Parser(List<String> tags){
        this.xmlReader = new XmlReader();
        this.graph = new Graph();
        this.tags = tags;
    }

    public void startParsing() throws IOException {
        parseDocumentsToGraph();
        new JsonMaker().getNodeJson(this.graph.getGraph(),"graph");
    }

    public void parseDocumentsToGraph(){
        for (int i = 1; i <= this.xmlReader.howManyDocuments(); i++) {
            parseDocument(this.xmlReader.getDocument(this.filename,i));
        }
    }

    private void parseDocument(Document document){
        parseNodes(document.getElementsByTagName("node"));
        parseWeights(document.getElementsByTagName("way"));
    }

    private void parseNodes(NodeList nodeList){
        final int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            org.w3c.dom.Node node = nodeList.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                parseNode((Element) node);
            }
        }
    }

    private void parseNode(Element element){
        long id = Long.parseLong(element.getAttribute("id"));
        double lat = Double.parseDouble(element.getAttribute("lat"));
        double lon = Double.parseDouble(element.getAttribute("lon"));
        this.graph.addNode(new Node(id,lat,lon));
    }

    private void parseWeights(NodeList nodeList){
        final int nodeListLenght = nodeList.getLength();
        for (int i = 0; i < nodeListLenght; i++) {
            org.w3c.dom.Node way = nodeList.item(i);
            if (way.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element wayElement = (Element) way;
                if(containsTags(wayElement, this.tags)){
                    addEdges(getIds(wayElement.getElementsByTagName("nd")));
                }
            }
        }
    }

    private List<Long> getIds(NodeList nodeList){
        final int ndListLength = nodeList.getLength();
        List<Long> nodeIds = new ArrayList<Long>();
        for (int k = 0; k < ndListLength; k++) {
            org.w3c.dom.Node nd = nodeList.item(k);
            if (nd.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                Element ndElement = (Element) nd;
                nodeIds.add(Long.parseLong(ndElement.getAttribute("ref")));
            }
        }
        return nodeIds;
    }

    private boolean containsTags(Element wayElement, List<String> tags){
        Set<String> set = new HashSet<>();
        NodeList tagList = wayElement.getElementsByTagName("tag");
        final int tagListLenght = tagList.getLength();
        for (int j = 0; j < tagListLenght; j++) {
            org.w3c.dom.Node tag = tagList.item(j);
            if (tag.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                Element tagElement = (Element) tag;
                set.add(tagElement.getAttribute("k"));
            }
        }
        for (int i = 0; i < tags.size(); i++) {
            if (!set.contains(tags.get(i))){
                return false;

            }
        }
        return true;
    }

    public void addEdges(List<Long> edges){
        long size = edges.size();
        for (int i = 0; i < size; i++) {
            if (i == 0 && size > 1) {
                this.graph.addEdge(edges.get(i),edges.get(i + 1));
            } else if(i > 0 && i < size - 1){
                this.graph.addEdge(edges.get(i),edges.get(i - 1));
                this.graph.addEdge(edges.get(i),edges.get(i + 1));
            } else if (i == size - 1 && size > 1){
                this.graph.addEdge(edges.get(i),edges.get(i - 1));
            }
        }
    }
}
