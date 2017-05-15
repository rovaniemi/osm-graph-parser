package osmparser.GraphJson;

import java.util.List;

public class NodeJson {

    private long id;
    private double lat;
    private double lon;
    private List<WeightJson> edges;

    public NodeJson(long id, double lat, double lon, List<WeightJson> edges){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edges = edges;
    }
}
