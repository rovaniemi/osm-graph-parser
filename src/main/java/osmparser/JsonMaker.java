package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JsonMaker {

    public void getNodeJson(Set<Node> nodeSet, String filename) {
        try (Writer writer = new FileWriter(filename + ".json")) {
            List<Node> list = new ArrayList<Node>(nodeSet);
            Collections.sort(list);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
