import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;

import java.util.HashMap;

public class WordNet {
    private Digraph G;
    private HashMap<String, SET<Integer>> nouns = new HashMap<>();
    private String[] reverseNouns;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // parse synsets
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            String[] nounsInLine = line[1].split("\\s");

            for (String noun : nounsInLine) {
                SET<Integer> ids = this.nouns.get(noun);
                if (ids == null) ids = new SET<>();
                ids.add(Integer.parseInt(line[0]));
                this.nouns.put(noun, ids);
            }
        }

        this.reverseNouns = new String[this.nouns.size()];
        this.nouns.forEach((key, value) -> {
            for (int id : value) this.reverseNouns[id] = key;
        });

        this.G = new Digraph(this.reverseNouns.length - 1);

        // parse hypernyms
        In in1 = new In(hypernyms);
        while (in1.hasNextLine()) {
            String[] line = in1.readLine().split(",");
            if (line.length == 1) continue;
            String[] nounsInLine = line[1].split("\\s");
            for (String noun : nounsInLine)
                this.G.addEdge(Integer.parseInt(nounsInLine[0]), Integer.parseInt(noun));
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!this.nouns.containsKey(nounA) || !this.nouns.containsKey(nounB))
            throw new IllegalArgumentException();
        return new SAP(this.G).length(this.nouns.get(nounA), this.nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!this.nouns.containsKey(nounA) || !this.nouns.containsKey(nounB))
            throw new IllegalArgumentException();
        int key = new SAP(this.G).ancestor(this.nouns.get(nounA), this.nouns.get(nounB));
        return this.reverseNouns[key];
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
