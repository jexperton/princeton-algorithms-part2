import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import junit.framework.TestCase;

import java.util.ArrayList;

public class SAPTest extends TestCase {
    public void testNoLength() {
        In in = new In("input/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        assertEquals(sap.length(0, 6), -1);
    }

    public void testLength() {
        In in3 = new In("input/digraph3.txt");
        Digraph G3 = new Digraph(in3);
        SAP sap3 = new SAP(G3);
        assertEquals(sap3.length(7, 9), 2);

        In in25 = new In("input/digraph25.txt");
        Digraph G25 = new Digraph(in25);
        SAP sap25 = new SAP(G25);
        assertEquals(sap25.length(0, 5), 2);
        assertEquals(sap25.length(22, 14), 5);
        assertEquals(sap25.length(7, 24), 8);
        assertEquals(sap25.length(21, 23), 10);
    }

    public void testNoAncestor() {
        In in1 = new In("input/digraph1.txt");
        Digraph G1 = new Digraph(in1);
        SAP sap1 = new SAP(G1);
        assertEquals(sap1.ancestor(0, 6), -1);
    }

    public void testAncestor() {
        In in3 = new In("input/digraph3.txt");
        Digraph G3 = new Digraph(in3);
        SAP sap3 = new SAP(G3);
        assertEquals(sap3.ancestor(7, 9), 9);

        In in25 = new In("input/digraph25.txt");
        Digraph G25 = new Digraph(in25);
        SAP sap25 = new SAP(G25);
        assertEquals(sap25.ancestor(0, 5), 0);
        assertEquals(sap25.ancestor(22, 14), 3);
        assertEquals(sap25.ancestor(7, 24), 0);
        assertEquals(sap25.ancestor(21, 23), 0);
    }

    public void testAncestorOfSubsets() {
        In in = new In("input/digraph25.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        Bag<Integer> v1 = makeBag(0, 1);
        Bag<Integer> w1 = makeBag(5, 6);
        assertEquals(sap.ancestor(v1, w1), 0);

        Bag<Integer> v2 = makeBag(15, 21, 22);
        Bag<Integer> w2 = makeBag(13, 14);
        assertEquals(sap.ancestor(v2, w2), 3);

        assertEquals(sap.ancestor(new ArrayList<>(0), new ArrayList<>(0)), -1);
    }

    private Bag<Integer> makeBag(int... items) {
        Bag<Integer> bag = new Bag<>();
        for (int item : items) bag.add(item);
        return bag;
    }

    public static void main(String[] args) {
        SAPTest test = new SAPTest();
        test.testLength();
        test.testNoLength();
        test.testAncestor();
        test.testNoAncestor();
        test.testAncestorOfSubsets();
    }
}
