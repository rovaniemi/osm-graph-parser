package osmparser;

public class Osmparser {

    public static void main(String[] args) {
        System.out.println("Reading oms file...");
        XmlFileReader xmlFileReader = new XmlFileReader();
        System.out.println("Transforming data...");
        xmlFileReader.readXmlFiles();
        Graph graph = xmlFileReader.getGraph();
        graph.transformWaysToWeights();
        System.out.println("Greating json...");
        graph.getNodeJson();
    }
}
