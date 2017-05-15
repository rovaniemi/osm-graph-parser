package osmparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Osmparser {

    static Graph graph;

    public static void main(String[] args) {
        graph = new Graph();
        addNodesAndWaysToGraphFromFile();
        graph.modifyData();
        graph.getNodeJson();
    }

    public static void addNodesAndWaysToGraphFromFile(){
        Document doc = getDocument();
        NodeList nodeList = doc.getElementsByTagName("node");
        parseNodes(nodeList);
        NodeList wayList = doc.getElementsByTagName("way");
        parseWays(wayList);
    }

    public static Document getDocument(){
        try {
            File map = new File(Osmparser.class.getClassLoader().getResource("map-01.osm").toURI());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(map);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void parseNodes(NodeList nodeList){
        final int nodeListLength = nodeList.getLength();
        for (int i = 0; i < nodeListLength; i++) {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                long id = Long.parseLong(eElement.getAttribute("id"));
                double lat = Double.parseDouble(eElement.getAttribute("lat"));
                double lon = Double.parseDouble(eElement.getAttribute("lon"));
                graph.addOneNode(new osmparser.Node(id,lat,lon));
            }
        }
    }

    public static void parseWays(NodeList nodeList){
        final int nodeListLenght = nodeList.getLength();
        for (int i = 0; i < nodeListLenght; i++) {
            Node way = nodeList.item(i);
            if (way.getNodeType() == Node.ELEMENT_NODE) {
                Element wayElement = (Element) way;
                if(containtsHighWayTag(wayElement)){
                    long id = Long.parseLong(wayElement.getAttribute("id"));
                    graph.addOneWay(new Way(id,getIds(wayElement.getElementsByTagName("nd"))));
                }
            }
        }
    }

    private static boolean containtsHighWayTag(Element wayElement){
        NodeList tagList = wayElement.getElementsByTagName("tag");
        final int tagListLenght = tagList.getLength();
        for (int j = 0; j < tagListLenght; j++) {
            Node tag = tagList.item(j);
            if (tag.getNodeType() == Node.ELEMENT_NODE){
                Element tagElement = (Element) tag;
                if(tagElement.getAttribute("k").equals("highway")){
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Long> getIds(NodeList nodeList){
        final int ndListLenght = nodeList.getLength();
        List<Long> nodeIds = new ArrayList<Long>();
        for (int k = 0; k < ndListLenght; k++) {
            Node nd = nodeList.item(k);
            if (nd.getNodeType() == Node.ELEMENT_NODE){
                Element ndElement = (Element) nd;
                nodeIds.add(Long.parseLong(ndElement.getAttribute("ref")));
            }
        }
        return nodeIds;
    }
}
