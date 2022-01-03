import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int[] shortest = shortestAncestorPath(
                new BreadthFirstDirectedPaths(G, v),
                new BreadthFirstDirectedPaths(G, w)
        );
        return shortest == null ? -1 : shortest.length - 1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int[] shortest = shortestAncestorPath(
                new BreadthFirstDirectedPaths(G, v),
                new BreadthFirstDirectedPaths(G, w)
        );
        return shortest == null ? -1 : shortest.length - 1;
    }

    private int[] shortestAncestorPath(
            BreadthFirstDirectedPaths vPaths,
            BreadthFirstDirectedPaths wPaths
    ) {
        int[] shortest = null;
        for (int i = 0; i < G.V(); i++) {
            if (!vPaths.hasPathTo(i) || !wPaths.hasPathTo(i)) continue;
            int totalLength = vPaths.distTo(i) + wPaths.distTo(i) + 1;
            if (shortest == null || totalLength < shortest.length) {
                int[] path = new int[totalLength];
                int j = 0;
                for (int item : vPaths.pathTo(i)) {
                    path[j] = item;
                    j++;
                }
                for (int item : wPaths.pathTo(i)) {
                    path[totalLength - (j - vPaths.distTo(i))] = item;
                    j++;
                }
                shortest = path;
            }
        }
        return shortest;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);
        int[] path = shortestAncestorPath(vPaths, wPaths);
        if (path == null) return -1;
        for (int i = 0; i < path.length; i++)
            if (vPaths.hasPathTo(path[i]) && wPaths.hasPathTo(path[i]))
                return path[i];
        return -1;
    }


    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);
        int[] path = shortestAncestorPath(vPaths, wPaths);
        if (path == null) return -1;
        for (int i = 0; i < path.length; i++)
            if (vPaths.hasPathTo(path[i]) && wPaths.hasPathTo(path[i]))
                return path[i];
        return -1;
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
