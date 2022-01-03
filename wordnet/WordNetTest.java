import edu.princeton.cs.algs4.StdOut;
import junit.framework.TestCase;

public class WordNetTest extends TestCase {

    public void testNouns() {
        WordNet WN = new WordNet("input/synsets15.txt",
                                 "input/hypernyms15Tree.txt");

        StdOut.println(WN.nouns());

        assertEquals(true, true);
    }

    public static void main(String[] args) {

    }
}
