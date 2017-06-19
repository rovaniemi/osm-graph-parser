package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class JsonMaker {

    public void getNodeJson(Map<Long,Node> graph, String filename) throws IOException {
        Writer writer = new FileWriter(filename + ".json");
        List<Node> list = new IdNormalizer().normalizeIds(graph);
        Collections.sort(list);
        Gson gson = new GsonBuilder().create();
        gson.toJson(list, writer);
        writer.close();
    }
}
