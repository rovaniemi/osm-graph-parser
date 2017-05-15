package osmparser;

public class Osmparser {

    public static void main(String[] args) {
        System.out.println("reading oms file...");
        XmlFileReader xmlFileReader = new XmlFileReader();
        System.out.println("greating graph...");
        System.out.println("transforming data");
        xmlFileReader.readXmlFiles();
        Graph graph = xmlFileReader.getGraph();
        graph.modifyData();
        System.out.println("greating json...");
        graph.getNodeJson();
    }
}
