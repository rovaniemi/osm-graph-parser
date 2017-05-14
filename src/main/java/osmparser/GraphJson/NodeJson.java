package osmparser.GraphJson;

import java.util.List;

public class NodeJson {

    private long id;
    private double lat;
    private double lon;
    private List<Long> edges;

    public NodeJson(long id, double lat, double lon, List<Long> edges){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edges = edges;
    }
}
