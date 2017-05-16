package osmparser;

public class Osmparser {

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parseDocumentsToGraph();
        parser.getNodeJson();
    }
}
