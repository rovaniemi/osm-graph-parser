package osmparser;

public class Weight {

    private long id;
    private long weight;

    public Weight(long id, long weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return (int)(id / (weight + 1));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Weight w = (Weight) o;
        if (this.id != w.id) {
            return false;
        }
        return weight == w.weight;
    }
}
