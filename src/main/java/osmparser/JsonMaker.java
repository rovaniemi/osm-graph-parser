package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class JsonMaker {

    private Map<Long,Long> idConverter;
    private long id;

    public JsonMaker(){
        this.idConverter = new HashMap<>();
        this.id = 0;
    }

    public List<Node> convertIds(Map<Long, Node> graph){
        Map<Long,Node> newGraph = new HashMap<Long,Node>();
        for (long id:graph.keySet()) {
            Node n = graph.get(id);
            newGraph.put(this.id, new Node(this.id,n.getLat(),n.getLon()));
            this.idConverter.put(id,this.id);
            this.id++;
        }
        for (long id: graph.keySet()) {
            Node n = graph.get(id);
            Node newN = newGraph.get(this.idConverter.get(id));
            Set<Weight> weights = n.getEdges();
            Iterator i = weights.iterator();
            while(i.hasNext()){
                Weight weight = (Weight) i.next();
                newN.addEdge(idConverter.get(weight.getId()),weight.getWeight());
            }
        }
        List<Node> list = new ArrayList<>();
        for (long i:newGraph.keySet()) {
            list.add(newGraph.get(i));
        }
        return list;
    }

    public void getNodeJson(Map<Long,Node> graph, String filename) throws IOException {
        Writer writer = new FileWriter(filename + ".json");
        List<Node> list = convertIds(graph);
        Collections.sort(list);
        Gson gson = new GsonBuilder().create();
        gson.toJson(list, writer);
    }
}
