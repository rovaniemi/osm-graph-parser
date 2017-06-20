package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import osmparser.tools.Consumer;

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
        GraphParser parser = new StreamingXmlGraphParser("highway");
        convert(mapFiles, parser);
    }

    public static void convert(File[] files, GraphParser parser) throws IOException {
        convert(files, parser, null);
    }

    public static List<Node> convert(File[] files, GraphParser parser, Consumer<String> statusCallback) throws IOException {
        safeAccept(statusCallback, "At convert");
        Graph parsedGraph = parseAll(files, parser);

        safeAccept(statusCallback,"After parseAll()");
        Map<Long, Node> nonIsolatedNodes = parsedGraph.getNodesWithEdges();

        safeAccept(statusCallback,"After getNodesWithEdges");
        List<Node> normalizedGraph = new IdNormalizer().normalizeIds(nonIsolatedNodes);

        safeAccept(statusCallback,"After normalizeIds");
        dumpToJson(normalizedGraph);

        safeAccept(statusCallback,"After dumpToJson");

        return normalizedGraph;
    }

    private static Graph parseAll(File[] xmlFiles, GraphParser parser) throws IOException {
        Graph graph = new Graph();
        for (File file : xmlFiles) {
            parser.parseXml(file, graph);
        }

        return graph;
    }

    private static void dumpToJson(List<Node> nodes) throws IOException {
        Collections.sort(nodes);
        try (Writer writer = new FileWriter("graph.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(nodes, writer);
        }
    }

    private static <T> void safeAccept(Consumer<T> consumer, T value) {
        if (consumer != null) {
            try {
                consumer.accept(value);
            } catch (Exception ex) {
            }
        }
    }
}
