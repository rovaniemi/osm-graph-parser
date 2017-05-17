package osmparser;

import java.util.*;

public class Graph {

    private Set<Node> graph;

    public Graph() {
        this.graph = new HashSet<>();
    }

    public void addNode(Node node){
        this.graph.add(node);
    }

    public Set<Node> getGraph() {
        return graph;
    }
}
