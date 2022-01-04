import junit.framework.TestCase;

public class WordNetTest extends TestCase {
    private WordNet wordnet = new WordNet("input/synsets.txt",
                                          "input/hypernyms.txt");

    public void testNouns() {
        int count = 0;
        for (String noun : wordnet.nouns()) count++;
        assertEquals(count, 119188);
    }

    public void testIsNoun() {
        assertEquals(wordnet.isNoun("white_marlin"), true);
        assertEquals(wordnet.isNoun("ornithorynque"), false);
    }

    public void testDistance() {
        assertEquals(wordnet.distance("white_marlin", "mileage"), 23);
        assertEquals(wordnet.distance("Black_Plague", "black_marlin"), 33);
        assertEquals(wordnet.distance("American_water_spaniel", "histology"), 27);
        assertEquals(wordnet.distance("Brown_Swiss", "barrel_roll"), 29);
    }

    public void testSap() {
        assertEquals(wordnet.sap("white_marlin mileage\n", "d"), "a");
        assertEquals(wordnet.sap("e", "d"), "b");
        assertEquals(wordnet.sap("j", "i"), "b");
        assertEquals(wordnet.sap("e", "o"), "b");
    }

    public static void main(String[] args) {
        WordNetTest test = new WordNetTest();
        test.testSap();
    }
}
