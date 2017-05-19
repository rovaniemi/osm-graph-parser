package osmparser;

import java.util.HashSet;
import java.util.Set;

public class Node {

    private long id;
    private double lat;
    private double lon;
    private Set<Weight> edges;

    public Node(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edges = new HashSet<>();
    }

    public void addEdge(long id, long distance){
        this.edges.add(new Weight(id, distance));
    }

    public boolean haveEdges(){
        return !this.edges.isEmpty();
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Node node = (Node) o;
        if (id != node.id){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
