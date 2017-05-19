package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

public class JsonMaker {

    public void getNodeJson(Set<Node> nodeSet, String filename) {
        try (Writer writer = new FileWriter(filename + ".json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(nodeSet, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
