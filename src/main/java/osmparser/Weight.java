package osmparser;

public class Weight {

    private long i;
    private long w;

    public Weight(long i, long w) {
        this.i = i;// index
        this.w = w;  // weight
    }

    @Override
    public int hashCode() {
        return (int) i;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Weight w = (Weight) o;
        if (this.i != w.i) {
            return false;
        }
        return true;
    }

    public long getI() {
        return i;
    }

    public long getW() {
        return w;
    }

    public String toString(){
        return "" + this.getI();
    }
}
