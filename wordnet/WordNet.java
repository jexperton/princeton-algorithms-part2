import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private final SAP sap;
    private final HashMap<String, SET<Integer>> nouns = new HashMap<>();
    private final ArrayList<Vertex> verticesIndex = new ArrayList<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // parse synsets
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            String[] nounsInLine = line[1].split("\\s+");
            this.verticesIndex.add(new Vertex(Integer.parseInt(line[0]), line[1]));
            for (String noun : nounsInLine) {
                SET<Integer> ids = this.nouns.get(noun);
                if (ids == null) ids = new SET<>();
                ids.add(this.verticesIndex.size() - 1);
                this.nouns.put(noun, ids);
            }
        }

        Digraph G = new Digraph(this.verticesIndex.size());

        // parse hypernyms
        In in1 = new In(hypernyms);
        while (in1.hasNextLine()) {
            String[] line = in1.readLine().split(",");
            if (line.length == 1) continue;
            for (int i = 1; i < line.length; i++)
                G.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
        }

        checkIfRootedDAG(G);

        this.sap = new SAP(G);
    }

    private void checkIfRootedDAG(Digraph digraph) {

        if (!new Topological(digraph).hasOrder())
            throw new IllegalArgumentException("Digraph is not a DAG");

        boolean hasRoot = false;
        for (Vertex vertex : this.verticesIndex)
            if (digraph.outdegree(vertex.id()) == 0)
                if (!hasRoot) hasRoot = true;
                else throw new IllegalArgumentException("Digraph has more than one root");

        if (!hasRoot) throw new IllegalArgumentException("Digraph has no root");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return this.nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!this.nouns.containsKey(nounA) || !this.nouns.containsKey(nounB))
            throw new IllegalArgumentException();
        return this.sap.length(this.nouns.get(nounA), this.nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!this.nouns.containsKey(nounA) || !this.nouns.containsKey(nounB))
            throw new IllegalArgumentException();
        int key = this.sap.ancestor(this.nouns.get(nounA), this.nouns.get(nounB));
        return this.verticesIndex.get(key).value();
    }

    private static class Vertex {
        private final int id;
        private final String value;

        Vertex(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public int id() {
            return this.id;
        }

        public String value() {
            return this.value;
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);

        while (!StdIn.isEmpty()) {
            String nounA = StdIn.readString();
            String nounB = StdIn.readString();
            int distance = wordnet.distance(nounA, nounB);
            String sap = wordnet.sap(nounA, nounB);
            StdOut.printf("distance = %d, ancestor = %s\n", distance, sap);
        }
    }
}
