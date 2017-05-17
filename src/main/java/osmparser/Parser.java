package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Parser {

    private XmlReader xmlReader;
    private Map<Long, Node> graph;

    public Parser(){
        this.xmlReader = new XmlReader();
        this.graph = new HashMap<>();
        parseDocumentsToGraph();
        getNodeJson();
    }

    public void parseDocumentsToGraph(){
        List<Document> documentList = this.xmlReader.getListOfDocuments();
        for (int i = 0; i < documentList.size(); i++) {
            parseOneDocument(documentList.get(i));
        }
    }

    private void parseOneDocument(Document document){
        parseNodes(document.getElementsByTagName("node"));
        parseWeights(document.getElementsByTagName("way"));
    }

    private void parseNodes(NodeList nodeList){
        final int nodeListLength = nodeList.getLength();
        for (int i = 0; i < nodeListLength; i++) {
            org.w3c.dom.Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                long id = Long.parseLong(eElement.getAttribute("id"));
                double lat = Double.parseDouble(eElement.getAttribute("lat"));
                double lon = Double.parseDouble(eElement.getAttribute("lon"));
                this.graph.put(id, new Node(id,lat,lon));
            }
        }
    }

    private void parseWeights(NodeList nodeList){
        final int nodeListLenght = nodeList.getLength();
        for (int i = 0; i < nodeListLenght; i++) {
            org.w3c.dom.Node way = nodeList.item(i);
            if (way.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element wayElement = (Element) way;
                if(containsHighWayTag(wayElement)){
                    List<Long> ids = getIds(wayElement.getElementsByTagName("nd"));
                    addEdges(ids);
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

    private boolean containsHighWayTag(Element wayElement){
        NodeList tagList = wayElement.getElementsByTagName("tag");
        final int tagListLenght = tagList.getLength();
        for (int j = 0; j < tagListLenght; j++) {
            org.w3c.dom.Node tag = tagList.item(j);
            if (tag.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                Element tagElement = (Element) tag;
                if(tagElement.getAttribute("k").equals("highway")){
                    return true;
                }
            }
        }
        return false;
    }

    public void addEdges(List<Long> edges){
        long size = edges.size();
        for (int i = 0; i < size; i++) {
            if (i == 0 && size > 1) {
                this.graph.get(edges.get(i)).addWeight(this.graph.get(edges.get(i + 1)));
            } else if(i > 0 && i < size - 1){
                this.graph.get(edges.get(i)).addWeight(this.graph.get(edges.get(i - 1)));
                this.graph.get(edges.get(i)).addWeight(this.graph.get(edges.get(i + 1)));
            } else if (i == size - 1 && size > 1){
                this.graph.get(edges.get(i)).addWeight(this.graph.get(edges.get(i - 1)));
            }
        }
    }

    public void getNodeJson() {
        Graph graph = changeHashMapToGraph();
        try (Writer writer = new FileWriter("graph.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(graph.getGraph(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Graph changeHashMapToGraph(){
        Graph graph = new Graph();
        for(Iterator<Map.Entry<Long, Node>> it = this.graph.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, Node> entry = it.next();
            if(entry.getValue().haveWeights()) {
                graph.addNode(entry.getValue());
            }
            it.remove();
        }
        return graph;
    }
}
