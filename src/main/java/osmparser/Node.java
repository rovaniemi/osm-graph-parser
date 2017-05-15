package osmparser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Node {

    private long id;
    private double lat;
    private double lon;

    public Node(long id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
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
}
