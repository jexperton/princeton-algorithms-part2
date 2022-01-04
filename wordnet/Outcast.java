import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int[] distances = new int[nouns.length];
        int maxDistanceIndex = 0;
        for (int a = 0; a < nouns.length; a++) {
            if (!this.wordnet.isNoun(nouns[a])) throw new IllegalArgumentException();
            for (int b = 0; b < nouns.length; b++) {
                if (a == b) continue;
                if (!this.wordnet.isNoun(nouns[b])) throw new IllegalArgumentException();
                distances[a] += this.wordnet.distance(nouns[a], nouns[b]);
            }
            if (distances[a] > distances[maxDistanceIndex]) maxDistanceIndex = a;
        }
        return nouns[maxDistanceIndex];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
