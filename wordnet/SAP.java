import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0) throw new IllegalArgumentException();
        if (v > G.V() - 1 || w > G.V() - 1) throw new IllegalArgumentException();
        ShortestPath path = new ShortestPath(new BreadthFirstDirectedPaths(G, v),
                                             new BreadthFirstDirectedPaths(G, w),
                                             G.V());
        return path.length() == 0 ? -1 : path.length() - 1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        if (containsNull(v) || containsNull(w)) throw new IllegalArgumentException();
        if (isEmpty(v) || isEmpty(w)) return -1;
        ShortestPath path = new ShortestPath(new BreadthFirstDirectedPaths(G, v),
                                             new BreadthFirstDirectedPaths(G, w),
                                             G.V());
        return path.length() == 0 ? -1 : path.length() - 1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v > G.V() - 1 || w > G.V() - 1) throw new IllegalArgumentException();
        ShortestPath path = new ShortestPath(new BreadthFirstDirectedPaths(G, v),
                                             new BreadthFirstDirectedPaths(G, w),
                                             G.V());
        return path.length() == 0 ? -1 : path.sca();
    }


    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        if (containsNull(v) || containsNull(w)) throw new IllegalArgumentException();
        if (isEmpty(v) || isEmpty(w)) return -1;
        ShortestPath path = new ShortestPath(new BreadthFirstDirectedPaths(G, v),
                                             new BreadthFirstDirectedPaths(G, w),
                                             G.V());
        return path.length() == 0 ? -1 : path.sca();
    }

    private <T> boolean containsNull(Iterable<T> items) {
        for (T item : items)
            if (item == null) return true;
        return false;
    }

    private <T> boolean isEmpty(Iterable<T> items) {
        return !items.iterator().hasNext();
    }

    private static class ShortestPath {
        private int sca;
        private int length = 0;

        public ShortestPath(
                BreadthFirstDirectedPaths vPaths,
                BreadthFirstDirectedPaths wPaths,
                int verticesCount
        ) {
            Iterable<Integer> vPath = null;
            for (int i = 0; i < verticesCount; i++) {
                if (!vPaths.hasPathTo(i) || !wPaths.hasPathTo(i)) continue;
                int totalLength = vPaths.distTo(i) + wPaths.distTo(i) + 1;
                if (this.length == 0 || totalLength < this.length) {
                    vPath = vPaths.pathTo(i);
                    this.length = totalLength;
                }
            }
            if (vPath != null)
                for (int item : vPath)
                    this.sca = item;
        }

        public int sca() {
            return this.sca;
        }

        public int length() {
            return this.length;
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
