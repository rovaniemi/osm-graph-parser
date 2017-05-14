package osmparser.GraphJson;

import java.util.ArrayList;
import java.util.List;

public class WeightJson {
    private long id;
    private List<OneWeight> weights;

    public WeightJson(long id, List<Long[]> weights){
        this.id = id;
        addWeights(weights);
    }

    private void addWeights(List<Long[]> weights){
        this.weights = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {

                OneWeight oneWeight = new OneWeight(weights.get(i)[0],weights.get(i)[1]);
                this.weights.add(oneWeight);

        }
    }

    public List<OneWeight> getWeights() {
        return weights;
    }
}
