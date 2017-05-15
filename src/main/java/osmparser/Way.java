package osmparser;

import java.util.List;

public class Way {

    private long id;
    private List<Long> nodes;

    public Way (long id, List<Long> nodes) {
        this.id = id;
        this.nodes = nodes;
    }

    public List<Long> getNodes() {
        return nodes;
    }
}
