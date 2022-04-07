package reactiveAgent;

public class Perception {

    private Cell n, s, e, w;

    public Perception(Cell n, Cell s, Cell e, Cell w) {
        this.n = n;
        this.s = s;
        this.e = e;
        this.w = w;
    }

    public Cell getE() {
        return e;
    }

    public Cell getN() {
        return n;
    }

    public Cell getW() {
        return w;
    }

    public Cell getS() {
        return s;
    }
}