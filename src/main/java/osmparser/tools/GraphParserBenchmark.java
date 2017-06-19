package osmparser.tools;

import osmparser.DomXmlGraphParser;
import osmparser.IGraphParser;
import osmparser.MapFileDiscoverer;
import osmparser.Node;
import osmparser.NormalizedGraphDeepComparator;
import osmparser.Osmparser;
import osmparser.StreamingXmlGraphParser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class GraphParserBenchmark {

    private Date benchmarkStart;

    public static void main(String[] args) throws IOException {
        new GraphParserBenchmark().start();
    }

    private void start() throws IOException {
        File[] mapFiles = MapFileDiscoverer.discover("map/", "map-");

        Consumer<String> progressCallback = new Consumer<String>() {
            @Override
            public void accept(String value) {
                printMemoryUse(value);
            }
        };

        Runtime.getRuntime().gc();

        startBenchmark("Benchmarking StreamingXmlGraphParser");
        IGraphParser streamingParser = new StreamingXmlGraphParser("highway");
        List<Node> a = Osmparser.convert(mapFiles, streamingParser, progressCallback);
        finishBenchmark();

        Runtime.getRuntime().gc();

        startBenchmark("Benchmarking DomXmlGraphParser");
        IGraphParser domParser = new DomXmlGraphParser("highway");
        List<Node> b = Osmparser.convert(mapFiles, domParser, progressCallback);
        finishBenchmark();

        System.out.println(new NormalizedGraphDeepComparator().compare(a, b));
    }

    private void startBenchmark(String label) {
        benchmarkStart = new Date();
        System.out.println(label);
    }

    private void finishBenchmark() {
        Date now = new Date();
        long durationMs = now.getTime() - benchmarkStart.getTime();
        System.out.printf("Benchmark took %fs%n", durationMs / 1000d);
    }

    private void printMemoryUse(String label) {
        System.out.printf(
            "Memory use: %s: %.2f MB%n",
            label,
            (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024d / 1024d
        );
    }
}
