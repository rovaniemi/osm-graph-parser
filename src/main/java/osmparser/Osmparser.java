package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class Osmparser {

    private final String[] mapFiles;
    private final String outFile;
    private final GraphParser parser;

    public Osmparser(String[] mapFiles, String outFile, GraphParser graphParser) {
        this.mapFiles = mapFiles;
        if(!outFile.endsWith(".json")) outFile += ".json";
        this.outFile = outFile;
        this.parser = graphParser;
    }

    public void start() throws IOException {
        Graph parsedGraph = parseAll();
        Map<Long, Node> nonIsolatedNodes = parsedGraph.getNodesWithEdges();
        List<Node> normalizedGraph = new IdNormalizer().normalizeIds(nonIsolatedNodes);
        dumpToJson(normalizedGraph);
    }

    private Graph parseAll() throws IOException {
        Graph graph = new Graph();

        for (String fileName : mapFiles) {
            File file = new File(fileName);
            parser.parseXml(file, graph);
        }

        return graph;
    }

    private void dumpToJson(List<Node> nodes) throws IOException {

        try (Writer writer = new FileWriter(outFile)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(nodes, writer);
        }
    }
}
