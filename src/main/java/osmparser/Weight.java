package osmparser;

import java.util.ArrayList;
import java.util.List;

public class Weight {

    private long id;
    private Node node;
    private List<Long[]> weight;

    public Weight(Node node){
        this.id = node.getId();
        this.node = node;
        this.weight = new ArrayList<Long[]>();
    }

    public void addWeight(Node node){
        Long[] longs = new Long[2];
        longs[0] = node.getId();
        longs[1] = distance(this.node.getLat(),this.node.getLon(), node.getLat(),node.getLon());
        this.weight.add(longs);
    }

    private static long distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = dist * 100000;
        return (int)(dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public long getId() {
        return id;
    }

    public List<Long[]> getWeight() {
        return weight;
    }
}
