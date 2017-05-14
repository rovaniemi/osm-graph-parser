package osmparser;

import java.util.List;

public class Way {

    private long id;
    private List<Long> nodes;

    public Way (long id, List<Long> nodes) {
        this.id = id;
        this.nodes = nodes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getNodes() {
        return nodes;
    }

    public void setNodes(List<Long> nodes) {
        this.nodes = nodes;
    }
}
