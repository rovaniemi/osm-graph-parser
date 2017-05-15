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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Osmparser {

    static Graph graph;

    public static void main(String[] args) {
        System.out.println("Greating graph...");
        graph = new Graph();
        System.out.println("Reading oms file...");
        addNodesAndWaysToGraphFromFile();
        System.out.println("Editing data...");
        graph.modifyData();
        System.out.println("Greating json...");
        graph.getNodeJson();
    }

    public static void addNodesAndWaysToGraphFromFile(){
        try {
            long size = Files.list(Paths.get("map/")).count();
            for (long j = 0; j < size; j++) {
                Document doc = getDocument("map-" + underTen(j + 1) + ".osm");
                NodeList nodeList = doc.getElementsByTagName("node");
                parseNodes(nodeList);
                NodeList wayList = doc.getElementsByTagName("way");
                parseWays(wayList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Document getDocument(String documentName){
        try {
            String path = Osmparser.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            File map = new File("map/" + documentName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(map);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                if(containsHighWayTag(wayElement)){
                    long id = Long.parseLong(wayElement.getAttribute("id"));
                    graph.addOneWay(new Way(id,getIds(wayElement.getElementsByTagName("nd"))));
                }
            }
        }
    }

    private static boolean containsHighWayTag(Element wayElement){
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

    public static String underTen(long j){
        if(j < 10){
            return "0" + j;
        } else{
            return "" + j;
        }
    }
}
