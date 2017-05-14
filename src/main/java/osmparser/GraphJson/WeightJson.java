package osmparser.GraphJson;

import java.util.List;

public class WeightJson {
    private long id;
    private List<Long[]> weights;

    public WeightJson(long id, List<Long[]> weights){
        this.id = id;
        this.weights = weights;
    }
}
