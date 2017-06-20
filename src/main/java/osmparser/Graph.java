package osmparser;

import java.util.*;

public class Graph {

    private final static double AVERAGE_RADIUS_OF_EARTH_CM = 6371 * 1000 * 100;
    private final Map<Long, Node> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addNode(Node node) {
        this.graph.put(node.getId(), node);
    }

    public void addEdge(long fromId, long toId) {
        Node from = this.graph.get(fromId);
        Node to = this.graph.get(toId);
        if (from != null && to != null) {
            int distance = distanceOnEarth(from.getLa(), from.getLo(), to.getLa(), to.getLo());
            from.addEdgeTo(toId, distance);
        }
    }

    public Map<Long, Node> getNodesWithEdges() {
        Map<Long,Node> map = new HashMap<>();
        for (Map.Entry<Long, Node> entry : graph.entrySet()) {
            if (entry.getValue().haveEdges()) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    private int distanceOnEarth(double lat1, double lon1, double lat2, double lon2) {

        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(lon1 - lon2);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_CM * c));
    }
}
