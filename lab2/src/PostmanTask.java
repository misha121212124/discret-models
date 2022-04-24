import java.util.ArrayList;
import java.util.List;

class PostmanTask {
    Graph graph;
    private List<List<Edge>> cycles;
    private List<Edge> edges;

    public PostmanTask(Graph graph) {
        this.graph = graph;
        this.cycles = new ArrayList<>();
        this.edges = graph.getEdges();
    }


    List<Edge> calculate(int l) {
        while (!edges.isEmpty()) {
            try {
                cycles.add(makeCycle(l));
            } catch (Exception e) {
                if (cycles.size() == 0) {
                    System.out.println("Error");
                    return calculate(++l);
                }
                edges.addAll(cycles.remove(0));
                edges.sort(Edge::compareTo);
                l++;
            }
        }

        System.out.println("Cycles");
        cycles.forEach(System.out::println);

        if (cycles.size() > 1)
            return mergeCycles();
        return null;


    }

    List<Edge> makeCycle(int i) throws Exception {
        List<Edge> cycle = new ArrayList<>();
        Edge edge = edges.get(i % edges.size());
        cycle.add(edge);
        edges.remove(edge);

        while (cycle.get(cycle.size() - 1).v2 != cycle.get(0).v1) {
            List<Edge> edgesCopy = new ArrayList<>(edges);
            try {
                addToCycle(cycle, edgesCopy);
            } catch (Exception e) {
                edges.addAll(cycle);
                edges.sort(Edge::compareTo);
                if (edges.size() > i) {
                    cycle = new ArrayList<>();
                    edge = edges.get(i);
                    cycle.add(edge);
                    edges.remove(edge);
                    i++;
                } else {
                    throw new Exception("Can not find cycle");
                }
            }
        }

        return cycle;
    }

    void addToCycle(List<Edge> cycle, List<Edge> edgesCopy) throws Exception {
        if (!edgesCopy.isEmpty()) {
            if (!cycle.contains(edgesCopy.get(0)) && areConnected(cycle.get(cycle.size() - 1), edgesCopy)) {
                Edge temp = edgesCopy.get(0);
                cycle.add(temp);
                edges.remove(temp);
            } else {
                edgesCopy.remove(0);
                addToCycle(cycle, edgesCopy);
            }
        } else throw new Exception();
    }

    List<Edge> mergeCycles() {

        List<Edge> finalCycle = new ArrayList<>();
        int commonVertex = findCommonVertex(cycles.get(0), cycles.get(1));

        if (commonVertex == -1) {
            return finalCycle;
        }

        boolean isDeleted = false;

        for (Edge edge : cycles.get(1)) {
            if (!isDeleted && edge.v2 == commonVertex) {
                finalCycle.add(edge);
                finalCycle.addAll(cycles.get(0));
                cycles.remove(0);
                isDeleted = true;
            } else finalCycle.add(edge);
        }
        if (cycles.size() > 1) {
            finalCycle = mergeCycles();
        } else {
            System.out.println("Final cycle: " + finalCycle);
        }

        return finalCycle;
    }

    int findCommonVertex(List<Edge> cycle1, List<Edge> cycle2) {
        for (Edge edge1 : cycle1) {
            for (Edge edge2 : cycle2) {
                if (edge1.v2 == edge2.v1)
                    return edge1.v2;
            }
        }

        return -1;
    }

    boolean areConnected(Edge edge1, List<Edge> edgesCopy) {
        Edge edge2 = edgesCopy.get(0);

        if (edge1.v2 == edge2.v1) {
            return true;
        } else if (edge1.v2 == edge2.v2) {
            edges.remove(edge2);
            edgesCopy.set(0, new Edge(edge2.v2, edge2.v1, edge2.w));
            return true;
        } else return false;
    }
}
