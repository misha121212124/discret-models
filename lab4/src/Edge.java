package src;

class Edge implements Comparable<Edge> {
    int v1, v2, w;

    public Edge(int v1, int v2, int w) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }

    public int compareTo(Edge p) {
        return this.w - p.w;
    }

    @Override
    public String toString() {
        return "{" +
                v1 +
                " " + v2 +
                " - " + w +
                '}';
    }
}
