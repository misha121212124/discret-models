

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Branches {
    private int[][] matrix;
    private ArrayList<Boolean> flags = new ArrayList<>();
    private ArrayList<Integer> way = new ArrayList<>();
    private ArrayList<Integer> optimal_path = new ArrayList<>();

    private int n = 0;
    private int min_weight = Integer.MAX_VALUE;
    private int start = 0;
    private int finish = 0;


    public void start() throws FileNotFoundException {
        read();
        calculate(0, -1, n - 1, 0);
        showAnswer();
    }

    private void read() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner in = new Scanner(file);
        n = in.nextInt();
        matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
        for (int i = 0; i < n; i++) {
            way.add(-1);
            flags.add(false);
        }
    }


    private void calculate(int v, int from, int steps, int weight) {
        if (flags.get(v)) return;


        flags.set(v, true);
        way.set(v, from);
        if (steps == 0 && matrix[v][start] != 0) {
            weight += matrix[v][start];
            if (weight < min_weight) {
                min_weight = weight;
                optimal_path = (ArrayList<Integer>) way.clone();
                finish = v;
            }
        }

        for (int i = 0; i < n; i++) {
            if (matrix[v][i] == 0) continue;
            calculate(i, v, steps - 1, weight + matrix[v][i]);
        }
        flags.set(v, false);

    }

    private void showAnswer() {
        ArrayList<Integer> a = get_path();
        int last = 0;
        for (int i = 0; i < a.size(); i++) {
            System.out.println(i + ": " + last + " - " + a.get(i) + " Вага: " + matrix[last][a.get(i)]);
            last = a.get(i);
        }
        System.out.println(a.size() + ": " + last + " - " + start + " Вага: " + matrix[last][start]);
        System.out.println("\nМінімальна вага " + min_weight + "\n");
    }

    private ArrayList<Integer> get_path() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int v = finish; v != start; v = optimal_path.get(v)) {
            ans.add(v);
        }

        ans.add(start);
        Collections.reverse(ans);

        return ans;
    }


}