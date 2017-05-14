package osmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import osmparser.GraphJson.NodeJson;
import osmparser.GraphJson.OneWeight;
import osmparser.GraphJson.WeightJson;

import java.io.*;
import java.util.*;

public class Graph {

    private Map<Long, Node> nodes;
    private Set<Way> ways;
    private Map<Long, Weight> weights;

    public Graph(){
        this.nodes = new HashMap<>();
        this.ways = new HashSet<>();
        this.weights = new HashMap<>();
    }

    public void modifyData(){
        for (Way way:this.ways) {
            List<Long> list = way.getNodes();
            for (int i = 0; i < list.size(); i++) {
                if(i == 0 && list.size() > 1){
                    Node node = this.nodes.get(list.get(i));
                    Node nextNode = this.nodes.get(list.get(i + 1));
                    node.addEdge(nextNode);
                    if(this.weights.containsKey(node.getId())){
                        this.weights.get(node.getId()).addWeight(nextNode);
                    } else {
                        Weight weight = new Weight(node);
                        weight.addWeight(nextNode);
                        this.weights.put(node.getId(),weight);
                    }
                } else if (i < list.size() - 1){
                    Node node = this.nodes.get(list.get(i));
                    Node prevNode = this.nodes.get(list.get(i - 1));
                    Node nextNode = this.nodes.get(list.get(i + 1));
                    node.addEdge(prevNode);
                    node.addEdge(nextNode);
                    if(this.weights.containsKey(node.getId())){
                        this.weights.get(node.getId()).addWeight(prevNode);
                        this.weights.get(node.getId()).addWeight(nextNode);
                    } else {
                        Weight weight = new Weight(node);
                        weight.addWeight(prevNode);
                        weight.addWeight(nextNode);
                        this.weights.put(node.getId(),weight);
                    }
                } else if (i == list.size() - 1 && list.size() > 1){
                    Node node = this.nodes.get(list.get(i));
                    Node prevNode = this.nodes.get(list.get(i - 1));
                    node.addEdge(prevNode);
                    if(this.weights.containsKey(node.getId())){
                        this.weights.get(node.getId()).addWeight(prevNode);
                    } else {
                        Weight weight = new Weight(node);
                        weight.addWeight(prevNode);
                        this.weights.put(node.getId(),weight);
                    }
                }
            }
        }
    }

    public void getNodeJson(){
        List<NodeJson> nodeJsonList = new ArrayList<>();
        for (Long lon:this.nodes.keySet()) {
            Node node = this.nodes.get(lon);
            if(this.weights.containsKey(node.getId())){
                Weight weight = this.weights.get(node.getId());
                List<OneWeight> weights = new ArrayList<>();
                for (int i = 0; i < weight.getWeight().size(); i++) {
                    OneWeight oneWeight = new OneWeight(weight.getWeight().get(i)[0], weight.getWeight().get(i)[1]);
                    weights.add(oneWeight);
                }
                if(!node.getNodeIds().isEmpty()) {
                    nodeJsonList.add(new NodeJson(node.getId(), node.getLat(), node.getLon(), weights));
                }
            }
        }
        try (Writer writer = new FileWriter("graph.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(nodeJsonList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getWeightJson(){
        List<WeightJson> weightJsonList = new ArrayList<>();
        for (Long lon:this.weights.keySet()){
            Weight weight = this.weights.get(lon);
            WeightJson weightJson = new WeightJson(weight.getId(), weight.getWeight());
            if(weight.getWeight().size() > 0){
                weightJsonList.add(weightJson);
            }
        }
        try (Writer writer = new FileWriter("weights.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(weightJsonList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOneNode(Node node){
        this.nodes.put(node.getId(), node);
    }

    public void addOneWay(Way way){
        this.ways.add(way);
    }
}
