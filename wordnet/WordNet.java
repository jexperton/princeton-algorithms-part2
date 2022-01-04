import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {
    private final Digraph G;
    private final HashMap<String, SET<Integer>> nouns = new HashMap<>();
    private final String[] reverseNouns;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // parse synsets
        In in = new In(synsets);
        int count = 0;
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            String[] nounsInLine = line[1].split("\\s");

            for (String noun : nounsInLine) {
                SET<Integer> ids = this.nouns.get(noun);
                if (ids == null) ids = new SET<>();
                ids.add(Integer.parseInt(line[0]));
                this.nouns.put(noun, ids);
            }
            count++;
        }

        this.reverseNouns = new String[count];
        this.nouns.forEach((key, ids) -> {
            for (int id : ids)
                this.reverseNouns[id] = key;
        });

        this.G = new Digraph(count);

        // parse hypernyms
        In in1 = new In(hypernyms);
        while (in1.hasNextLine()) {
            String[] line = in1.readLine().split(",");
            if (line.length == 1) continue;
            for (int i = 1; i < line.length; i++)
                this.G.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
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
        int key = new SAP(this.G).ancestor(this.nouns.get(nounA).min(),
                                           this.nouns.get(nounB).min());
        return this.reverseNouns[key];
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
