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
        PostmanTask task = new PostmanTask(graph);
        List<Edge> edgeList = task.calculate(0);
    }

}

