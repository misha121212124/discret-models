import java.io.*;
import java.util.Scanner;


public class Dispatcher {


    public static void main(String[] args) throws FileNotFoundException {
        File file1 = new File("input1.txt");
        File file2 = new File("input2.txt");
        Scanner in = new Scanner(file1);

        boolean checkResult;
        Graph g = new Graph();
        g.read(in);
        in = new Scanner(file2);
        Graph g2 = new Graph();
        g2.read(in);

        Graph.IsomorphusCheck isomorphusCheck = new Graph.IsomorphusCheck();

        checkResult = isomorphusCheck.heuristic(g, g2);

        System.out.println("Are isomorphic: " + checkResult);
    }
}


