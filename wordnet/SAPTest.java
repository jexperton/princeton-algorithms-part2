import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import junit.framework.TestCase;

public class SAPTest extends TestCase {

    public void testNoLength() {
        In in = new In("input/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        assertEquals(sap.length(0, 6), -1);
    }

    public void testLength() {
        In in = new In("input/digraph25.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        assertEquals(sap.length(0, 5), 2);
        assertEquals(sap.length(22, 14), 5);
        assertEquals(sap.length(7, 24), 8);
        assertEquals(sap.length(21, 23), 10);
    }

    public void testNoAncestor() {
        In in = new In("input/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        assertEquals(sap.ancestor(0, 6), -1);
    }

    public void testAncestor() {
        In in = new In("input/digraph25.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        assertEquals(sap.ancestor(0, 5), 0);
        assertEquals(sap.ancestor(22, 14), 3);
        assertEquals(sap.ancestor(7, 24), 0);
        assertEquals(sap.ancestor(21, 23), 0);
    }

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
