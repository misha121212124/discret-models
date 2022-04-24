import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dispatcher {


    public static void main(String[] args) throws IOException {

        File file = new File("input.txt");
        Scanner in = new Scanner(file);
        int n = in.nextInt();
        int[][] matrix = new int[n][n];
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = in.nextInt();
                if (matrix[i][j] != 0 && matrix[j][i] == 0) {
                    edges.add(new Edge(i + 1, j + 1, matrix[i][j]));
                }
            }
        }

        Graph graph = new Graph(n, edges);
        int res = graph.kruskal();
        System.out.println("Вага дерева: " + res);

        System.out.println(graph.getResult());
    }

}

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
}
