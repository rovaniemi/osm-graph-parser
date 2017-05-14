package osmparser.GraphJson;

import java.util.List;

public class NodeJson {

    private long id;
    private double lat;
    private double lon;
    private List<OneWeight> edges;

    public NodeJson(long id, double lat, double lon, List<OneWeight> edges){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edges = edges;
    }
}
