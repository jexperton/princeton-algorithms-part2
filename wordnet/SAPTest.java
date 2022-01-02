import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import junit.framework.TestCase;

public class SAPTest extends TestCase {

    public void testLength() {
        In in = new In("input/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        assertEquals(sap.length(0, 1), -1);
        assertEquals(sap.length(1, 0), 1);
        assertEquals(sap.length(2, 0), 1);
        assertEquals(sap.length(8, 0), 3);
        assertEquals(sap.length(11, 0), 4);
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
