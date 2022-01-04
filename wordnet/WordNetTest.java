import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import junit.framework.TestCase;

public class WordNetTest extends TestCase {
    private WordNet wordnet = new WordNet("input/synsets.txt",
                                          "input/hypernyms.txt");

    public void testNotRootedDAG() {
        testNotRootedDAG("input/synsets3.txt", "input/hypernyms3InvalidTwoRoots.txt",
                         "Digraph has more than one root");
        testNotRootedDAG("input/synsets3.txt", "input/hypernyms3InvalidCycle.txt",
                         "Digraph is not a DAG");
        testNotRootedDAG("input/synsets6.txt", "input/hypernyms6InvalidTwoRoots.txt",
                         "Digraph has more than one root");
        testNotRootedDAG("input/synsets6.txt", "input/hypernyms6InvalidCycle.txt",
                         "Digraph is not a DAG");
        testNotRootedDAG("input/synsets6.txt", "input/hypernyms6InvalidCycle+Path.txt",
                         "Digraph has more than one root");
    }

    @SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    private void testNotRootedDAG(String synsets, String hypernyms, String message) {
        boolean thrown = false;
        try {
            WordNet ignored = new WordNet(synsets, hypernyms);
        }
        catch (IllegalArgumentException e) {
            thrown = true;
            assertEquals(message, e.getMessage());
        }
        assertTrue(thrown);
    }

    public void testNouns() {
        int count = 0;
        for (String ignored : wordnet.nouns()) count++;
        assertEquals(119188, count);
    }

    public void testIsNoun() {
        assertEquals(true, wordnet.isNoun("white_marlin"));
        assertEquals(false, wordnet.isNoun("ornithorynque"));
    }

    public void testDistance() {
        assertEquals(23, wordnet.distance("white_marlin", "mileage"));
        assertEquals(33, wordnet.distance("Black_Plague", "black_marlin"));
        assertEquals(27, wordnet.distance("American_water_spaniel", "histology"));
        assertEquals(29, wordnet.distance("Brown_Swiss", "barrel_roll"));
    }

    public void testSap() {
        assertEquals("abstraction abstract_entity",
                     wordnet.sap("Thirty_Years'_War", "family_Cruciferae")
        );
        assertEquals("object physical_object", wordnet.sap("wave_front", "laticifer"));
        assertEquals("organism being", wordnet.sap("Reich", "Centropristis_philadelphica"));
    }

    public static void main(String[] args) {
        WordNetTest test = new WordNetTest();
        test.testNotRootedDAG();
        test.testDistance();
        test.testNouns();
        test.testIsNoun();
        test.testSap();
    }
}
