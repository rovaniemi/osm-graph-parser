package osmparser;

import java.util.*;

public class Graph {

    private Map<Long,Node> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addNode(Node node){
        this.graph.put(node.getId(),node);
    }

    public void addEdge(long from, long to){
        if(this.graph.containsKey(from) && this.graph.containsKey(to)){
            Node fromN = this.graph.get(from);
            Node toN = this.graph.get(to);
            long distance = distance(fromN.getLat(), fromN.getLon(), toN.getLat(), toN.getLon());
            this.graph.get(from).addEdge(to,distance);
        } else {
            return;
        }
    }

    public Set<Node> getGraph() {
        Set<Node> set = new HashSet<>();
        Iterator it = graph.keySet().iterator();
        while (it.hasNext()) {
            Node node = graph.get(it.next());
            if(node.haveEdges()){
                set.add(node);
            }
            it.remove();
        }
        return set;
    }

    private long distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist *= 60 * 1.1515 * 1.609344 * 100000;
        return (int) (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
