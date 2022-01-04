import edu.princeton.cs.algs4.In;
import junit.framework.TestCase;

public class OutcastTest extends TestCase {
    private WordNet wordnet = new WordNet("input/synsets.txt",
                                          "input/hypernyms.txt");
    private Outcast oucast = new Outcast(this.wordnet);

    public void testOutcast() {
        In in3 = new In("input/outcast3.txt");
        String[] nouns3 = in3.readAllStrings();
        assertEquals(oucast.outcast(nouns3), "Mickey_Mouse");

        In in4 = new In("input/outcast4.txt");
        String[] nouns4 = in4.readAllStrings();
        assertEquals(oucast.outcast(nouns4), "probability");

        In in5 = new In("input/outcast5.txt");
        String[] nouns5 = in5.readAllStrings();
        assertEquals(oucast.outcast(nouns5), "table");

        In in5a = new In("input/outcast5a.txt");
        String[] nouns5a = in5a.readAllStrings();
        assertEquals(oucast.outcast(nouns5a), "heart");

        In in7 = new In("input/outcast7.txt");
        String[] nouns7 = in7.readAllStrings();
        assertEquals(oucast.outcast(nouns7), "India");

        In in8 = new In("input/outcast8.txt");
        String[] nouns8 = in8.readAllStrings();
        assertEquals(oucast.outcast(nouns8), "bed");

        In in8a = new In("input/outcast8a.txt");
        String[] nouns8a = in8a.readAllStrings();
        assertEquals(oucast.outcast(nouns8a), "playboy");

        In in8b = new In("input/outcast8b.txt");
        String[] nouns8b = in8b.readAllStrings();
        assertEquals(oucast.outcast(nouns8b), "cabbage");
    }

    public static void main(String[] args) {

    }
}
