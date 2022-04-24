import java.util.ArrayList;
import java.util.List;

class Graph {
    int n;
    List<Edge> edges;
    int[] vertex;

    List<Edge> result = new ArrayList<>();

    public Graph(int n, List<Edge> edges) {
        this.n = n;
        this.edges = edges;
        this.vertex = new int[n + 1];
    }

    public int find(int x) {
        if (vertex[x] != x) vertex[x] = find(vertex[x]);
        return vertex[x];
    }

    public int kruskal() {
        edges.sort(Edge::compareTo);

        int cnt = 0, res = 0;
        for (int i = 1; i <= n; i++) vertex[i] = i;

        for (Edge t : edges) {

            int a = find(t.v1);
            int b = find(t.v2);
            if (a != b) {
                vertex[a] = b;
                cnt++;
                res += t.w;
                result.add(t);
            }
        }
        return res;
    }

    public List<Edge> getResult() {
        if (result.size() == 0) throw new IllegalArgumentException();
        return result;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
