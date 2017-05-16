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

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void addWeight(Node node){
        long id = node.getId();
        long distance = distance(this.getLat(), this.getLon(),node.getLat(),node.getLon());
        Weight weight = new Weight(node.getId(), distance);
        if(!this.edges.contains(weight)) {
            this.edges.add(weight);
        }
    }

    public boolean haveWeights(){
        if(this.edges.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    private static long distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = dist * 100000;
        return (int) (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
