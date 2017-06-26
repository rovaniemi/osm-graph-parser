package osmparser;

import java.util.*;

public class IdNormalizer {
    private Map<Long, Long> oldIdToNewId;
    private Map<Long, Node> newGraph;
    private Map<Long, Node> oldGraph;
    private long nextId;

    public List<Node> normalizeIds(Map<Long, Node> graph) {
        normalize(graph);
        List<Node> normalizedList = new ArrayList<>(newGraph.values());
        Collections.sort(normalizedList);
        return normalizedList;
    }

    private void normalize(Map<Long, Node> graph){
        reset();
        oldGraph = graph;
        createNodesWithNewIds();
        addNewEdges();
    }

    private void reset() {
        oldIdToNewId = new HashMap<>();
        newGraph = new HashMap<>();
        nextId = 0;
    }

    private void createNodesWithNewIds() {
        for (Map.Entry<Long, Node> entry : oldGraph.entrySet()) {
            Node n = entry.getValue();
            long newId = newId();
            newGraph.put(newId, new Node(newId, n.getLa(), n.getLo()));
            oldIdToNewId.put(entry.getKey(), newId);
        }
    }

    private void addNewEdges() {
        for (Map.Entry<Long, Node> entry : oldGraph.entrySet()) {
            Node oldNode = entry.getValue();
            Node newNode = newGraph.get(oldIdToNewId.get(entry.getKey()));
            addEdges(oldNode, newNode);
        }
    }

    private void addEdges(Node oldNode, Node newNode) {
        Set<Weight> weights = oldNode.getE();
        for (Weight weight : weights) {
            Long newTargetId = oldIdToNewId.get(weight.getI());
            newNode.addEdgeTo(newTargetId, weight.getW());
        }
    }

    private long newId() {
        return nextId++;
    }
}
