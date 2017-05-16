package osmparser;

import java.util.*;

public class Graph {

    private List<Node> graph;

    public Graph() {
        this.graph = new ArrayList<>();
    }

    public void addNode(Node node){
        this.graph.add(node);
    }
}
