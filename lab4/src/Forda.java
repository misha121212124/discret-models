package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Forda {

    final int MAX_E = 100;
    final int MAX_V = 100;
    final int INF =Integer.MAX_VALUE;

    int sourceVertex, destinationVertex;
    int[] capacity = new int[MAX_E];
    int[] onEnd = new int[MAX_E];
    int[] nextEdge = new int[MAX_E];
    int edgeCount = 0;
    int[] firstEdge = new int[MAX_V];
    boolean[] visited = new boolean[MAX_V];

    void addEdge(int u, int v, int cap, int capR) {
        onEnd[edgeCount] = v;
        nextEdge[edgeCount] = firstEdge[u];
        firstEdge[u] = edgeCount;
        capacity[edgeCount++] = cap;

        onEnd[edgeCount] = u;
        nextEdge[edgeCount] = firstEdge[v];
        firstEdge[v] = edgeCount;
        capacity[edgeCount++] = capR;
    }

    int findFlow(int u, int flow) {
        if (u == destinationVertex) return flow;
        visited[u] = true;
        for (int edge = firstEdge[u]; edge != -1; edge = nextEdge[edge]) {
            int to = onEnd[edge];
            if (!visited[to] && capacity[edge] > 0) {
                int minResult = findFlow(to, Math.min(flow, capacity[edge]));
                if (minResult > 0) {
                    capacity[edge] -= minResult;
                    capacity[edge + 1] += minResult;
                    return minResult;
                }
            }
        }
        return 0;
    }

    int calculate() throws FileNotFoundException {

        File file = new File("input.txt");
        Scanner in = new Scanner(file);
        int n = in.nextInt();
        int[][] matrix = new int[n][n];
        Arrays.fill(firstEdge, -1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    addEdge(i, j, matrix[i][j], matrix[j][i]);
                }
            }
        }

        sourceVertex = 0;
        destinationVertex = 7;

        int maxFlow = 0;
        int iterationResult = 0;
        while ((iterationResult = findFlow(sourceVertex, INF)) > 0) {
            Arrays.fill(visited, false);
            maxFlow += iterationResult;
        }

        return maxFlow;
    }
}
