package osmparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IdNormalizer {
    private Map<Long, Long> oldIdToNewId;
    private Map<Long, Node> newGraph;
    private Map<Long, Node> oldGraph;
    private long nextId;

    public List<Node> normalizeIds(Map<Long, Node> graph) {
        reset();
        oldGraph = graph;
        createNodesWithNewIds();
        addNewEdges();
        return new ArrayList<>(newGraph.values());
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
            newGraph.put(newId, new Node(newId, n.getLat(), n.getLon()));
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
        Set<Weight> weights = oldNode.getEdges();
        for (Weight weight : weights) {
            Long newTargetId = oldIdToNewId.get(weight.getId());
            newNode.addEdgeTo(newTargetId, weight.getWeight());
        }
    }

    private long newId() {
        return nextId++;
    }
}
