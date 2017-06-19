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
        Parser parser = new Parser("highway");
        File[] mapFiles = MapFileDiscoverer.discover("map/", "map-");

        Graph parsedGraph = parser.parseAll(mapFiles);
        Map<Long, Node> nonIsolatedNodes = parsedGraph.getNodesWithEdges();
        List<Node> normalizedGraph = new IdNormalizer().normalizeIds(nonIsolatedNodes);
        dumpToJson(normalizedGraph);
    }

    private static void dumpToJson(List<Node> nodes) throws IOException {
        Collections.sort(nodes);

        try (Writer writer = new FileWriter("graph.json")) {
            new Gson().toJson(nodes, writer);
        }
    }
}
