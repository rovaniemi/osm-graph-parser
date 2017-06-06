package osmparser;

import java.util.*;

public class Graph {

    public final static double AVERAGE_RADIUS_OF_EARTH_CM = 6371 * 1000 * 100;
    private Map<Long, Node> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addNode(Node node){
        this.graph.put(node.getId(), node);
    }

    public void addEdge(long from, long to){
        if(this.graph.containsKey(from) && this.graph.containsKey(to)){
            Node fromN = this.graph.get(from);
            Node toN = this.graph.get(to);
            long distance = distance(fromN.getLat(), fromN.getLon(), toN.getLat(), toN.getLon());
            this.graph.get(from).addEdge(to, distance);
        }
    }

    public Map<Long, Node> getGraph() {
        Map<Long,Node> map = new HashMap<>();
        graph.keySet().stream().filter(e -> this.graph.get(e).haveEdges()).forEach(e -> map.put(e, this.graph.get(e)));
        return map;
    }

    public int distance(double lat1, double lon1, double lat2, double lon2) {

        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(lon1 - lon2);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_CM * c));
    }
}
