import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

class Graph {
    int size;
    BigInteger[][] m;

    void init(int sz) {
        size = sz;
        m = new BigInteger[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m[i][j] = new BigInteger("0");
            }
        }
    }

    Graph() {
        init(0);
    }

    Graph(int sz) {
        init(sz);
    }

    void read(Scanner in) {
        init(in.nextInt());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m[i][j] = in.nextBigInteger();
            }
        }
    }

    void copy(Graph gf) {
        init(gf.size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m[i][j] = gf.m[i][j];
            }
        }
    }

    void multiply(Graph gf) {
        Graph temp = new Graph();
        temp = new Graph(size);
        BigInteger sum;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum = new BigInteger("0");
                for (int k = 0; k < size; k++) {
                    sum = sum.add(m[i][k].multiply(gf.m[k][j]));
                }
                temp.m[i][j] = sum;
            }
        }
        copy(temp);
    }

    static class IsomorphusCheck {


        boolean heuristic(Graph gf1, Graph gf2) {
            UniqueGrafProperty gp1 = new UniqueGrafProperty(gf1);
            UniqueGrafProperty gp2 = new UniqueGrafProperty(gf2);

            if (gp1.size != gp2.size) return false;
            if (gp1.compareTo(gp2) != 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    static class UniqueGrafProperty implements Comparable<UniqueGrafProperty> {
        int size;
        UniqueVertexProperty v[];
        UniqueVertexProperty sv[];

        UniqueGrafProperty(Graph gf) {
            size = gf.size;
            v = new UniqueVertexProperty[size];
            sv = new UniqueVertexProperty[size];
            Graph temp = new Graph(gf.size);

            for (int i = 0; i < size; i++) {
                v[i] = new UniqueVertexProperty(size);
                v[i].num = i;
            }
            temp.copy(gf);
            for (int k = 0; k < gf.size; k++) {
                for (int i = 0; i < gf.size; i++) {
                    for (int j = 0; j < gf.size; j++) {
                        v[i].r[j].a[k] = temp.m[i][j];
                    }
                }
                temp.multiply(gf);
            }
            for (int i = 0; i < size; i++) {
                sv[i] = v[i];
                for (int j = 0; j < size; j++) {
                    sv[i].sr[j] = v[i].r[j];
                }
            }
            for (int i = 0; i < size; i++) {
                Arrays.sort(sv[i].sr);
            }
            Arrays.sort(sv);
        }

        public int compareTo(UniqueGrafProperty gp) {
            for (int i = 0; i < size; i++) {
                if (sv[i].compareTo(gp.sv[i]) != 0) {
                    return sv[i].compareTo(gp.sv[i]);
                }
            }
            return 0;
        }
    }

    static class UniqueRibProperty implements Comparable<UniqueRibProperty> {
        int size;
        BigInteger a[];

        UniqueRibProperty(int sz) {
            size = sz;
            a = new BigInteger[sz];
            for (int i = 0; i < size; i++) {
                a[i] = new BigInteger("0");
            }
        }

        public int compareTo(UniqueRibProperty rp) {
            for (int i = 0; i < size; i++) {
                if (a[i].compareTo(rp.a[i]) != 0) {
                    return a[i].compareTo(rp.a[i]);
                }
            }
            return 0;
        }
    }

    static class UniqueVertexProperty implements Comparable<UniqueVertexProperty> {
        int size;
        int num;
        UniqueRibProperty r[];
        UniqueRibProperty sr[];

        UniqueVertexProperty(int sz) {
            size = sz;
            r = new UniqueRibProperty[size];
            sr = new UniqueRibProperty[size];
            for (int i = 0; i < size; i++) {
                r[i] = new UniqueRibProperty(size);
            }
        }

        public int compareTo(UniqueVertexProperty vp) {
            for (int i = 0; i < size; i++) {
                if (sr[i].compareTo(vp.sr[i]) != 0) {
                    return sr[i].compareTo(vp.sr[i]);
                }
            }
            return 0;
        }
    }
}
