package osmparser;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Osmparser {
    public static void main(String[] args) throws IOException {
        File[] mapFiles = MapFileDiscoverer.discover("map/", "map-");
        IGraphParser parser = new StreamingXmlGraphParser("highway");
        convert(mapFiles, parser);
    }

    private static void convert(File[] files, IGraphParser parser) throws IOException {
        Graph parsedGraph = parseAll(files, parser);
        Map<Long, Node> nonIsolatedNodes = parsedGraph.getNodesWithEdges();
        List<Node> normalizedGraph = new IdNormalizer().normalizeIds(nonIsolatedNodes);
        dumpToJson(normalizedGraph);
    }

    private static Graph parseAll(File[] xmlFiles, IGraphParser parser) throws IOException {
        Graph graph = new Graph();
        for (File file : xmlFiles) {
            parser.parseXml(file, graph);
        }

        return graph;
    }

    private static void dumpToJson(List<Node> nodes) throws IOException {
        Collections.sort(nodes);

        try (Writer writer = new FileWriter("graph.json")) {
            new Gson().toJson(nodes, writer);
        }
    }
}
