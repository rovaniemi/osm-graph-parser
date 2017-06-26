package osmparser;

import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable{

    private transient long id;  // id
    private double la;          // latitude
    private double lo;          // longitude
    private Set<Weight> e;      // edges

    public Node(long id, double la, double lo) {
        this.id = id;
        this.la = la;
        this.lo = lo;
        this.e = new HashSet<>();
    }

    public void addEdgeTo(long id, int distance){
        this.e.add(new Weight(id, distance));
    }

    public boolean haveEdges(){
        return !this.e.isEmpty();
    }

    public long getId() {
        return id;
    }

    public double getLa() {
        return la;
    }

    public double getLo() {
        return lo;
    }

    public Set<Weight> getE() {
        return e;
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

    @Override
    public int compareTo(Object o) {
        if(o == null || o.getClass() != this.getClass()){
            return 0;
        }
        Node n = (Node) o;

        return (int)(this.getId() - n.getId());
    }

    @Override
    public String toString() {
        return "id: " + id;
    }
}
