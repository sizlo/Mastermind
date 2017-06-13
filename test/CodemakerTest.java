import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by tbrier on 13/06/2017.
 */
public class CodemakerTest {
    @BeforeClass
    public static void setUp() throws Exception {
        // Tests designed for a patten length of 4
        assertEquals("Pattern length",4, Pattern.patternLength);
    }

    @Test
    public void AllOneColour() throws Exception {
        System.out.println("AllOneColour");

        Codemaker maker = new Codemaker();
        Colour[] colours = new Colour[Pattern.patternLength];
        Arrays.fill(colours, Colour.RED);
        maker.targetPattern = new Pattern(colours);

        // Test full match
        System.out.println("Full match");
        Arrays.fill(colours, Colour.RED);
        Guess g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 4, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 0, g.numRightColourWrongPlace);

        // Test a full mismatch
        System.out.println("Full mismatch");
        Arrays.fill(colours, Colour.GREEN);
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 0, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 0, g.numRightColourWrongPlace);

        // Test a half match
        System.out.println("Half match");
        colours[0] = colours[1] = Colour.RED;
        colours[2] = colours[3] = Colour.GREEN;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 2, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 0, g.numRightColourWrongPlace);
    }

    @Test
    public void AllDifferentColours() throws Exception {
        System.out.println("AllDifferentColours");
        Codemaker maker = new Codemaker();
        Colour[] colours = new Colour[Pattern.patternLength];
        colours[0] = Colour.RED;
        colours[1] = Colour.YELLOW;
        colours[2] = Colour.BLUE;
        colours[3] = Colour.GREEN;
        maker.targetPattern = new Pattern(colours);

        // Test full match
        System.out.println("Full match");
        Guess g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 4, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 0, g.numRightColourWrongPlace);

        // Test full mismatch
        System.out.println("Full mismatch");
        Arrays.fill(colours, Colour.PURPLE);
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 0, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 0, g.numRightColourWrongPlace);

        // Test all right colours wrong place
        System.out.println("All right colour wrong place");
        colours[0] = Colour.GREEN;
        colours[1] = Colour.RED;
        colours[2] = Colour.YELLOW;
        colours[3] = Colour.BLUE;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 0, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 4, g.numRightColourWrongPlace);

        // Test 2 right colour right place, 2 right colour wrong place
        System.out.println("2 right colour right place, 2 right colour wrong place");
        colours[0] = Colour.RED;
        colours[1] = Colour.YELLOW;
        colours[2] = Colour.GREEN;
        colours[3] = Colour.BLUE;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 2, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 2, g.numRightColourWrongPlace);

        // Test 2 right colour right place, 1 right colour wrong place, 1 wrong
        System.out.println("2 right colour right place, 1 right colour wrong place");
        colours[0] = Colour.RED;
        colours[1] = Colour.YELLOW;
        colours[2] = Colour.GREEN;
        colours[3] = Colour.PURPLE;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 2, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 1, g.numRightColourWrongPlace);

        // Test 2 right colour right place, 1 right colour wrong place, 1 wrong
        // Where the wrong one appears elsewhere in the pattern in the right place
        System.out.println("2 right colour right place, 1 right colour wrong place, wrong appears elsewhere");
        colours[0] = Colour.RED;
        colours[1] = Colour.YELLOW;
        colours[2] = Colour.GREEN;
        colours[3] = Colour.YELLOW;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 2, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 1, g.numRightColourWrongPlace);
    }

    @Test
    public void ThreeDifferentColours() throws Exception {
        System.out.println("ThreeDifferenceColours");
        Codemaker maker = new Codemaker();
        Colour[] colours = new Colour[Pattern.patternLength];
        colours[0] = Colour.RED;
        colours[1] = Colour.RED;
        colours[2] = Colour.BLUE;
        colours[3] = Colour.GREEN;
        maker.targetPattern = new Pattern(colours);

        // Test full match
        System.out.println("Full match");
        Guess g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 4, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 0, g.numRightColourWrongPlace);

        // Test full mismatch
        System.out.println("Full mismatch");
        Arrays.fill(colours, Colour.PURPLE);
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 0, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 0, g.numRightColourWrongPlace);

        // Test all right colours wrong place
        System.out.println("Right colours wrong place");
        colours[0] = Colour.GREEN;
        colours[1] = Colour.BLUE;
        colours[2] = Colour.RED;
        colours[3] = Colour.RED;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 0, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 4, g.numRightColourWrongPlace);

        // Test 2 right colour right place, 2 right colour wrong place
        System.out.println("2 right colour right place, 2 right colour wrong place");
        colours[0] = Colour.RED;
        colours[1] = Colour.RED;
        colours[2] = Colour.GREEN;
        colours[3] = Colour.BLUE;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 2, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 2, g.numRightColourWrongPlace);

        // Test 2 right colour right place, 1 right colour wrong place, 1 wrong
        System.out.println("2 right colour right place, 1 right colour wrong place");
        colours[0] = Colour.RED;
        colours[1] = Colour.RED;
        colours[2] = Colour.GREEN;
        colours[3] = Colour.PURPLE;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 2, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 1, g.numRightColourWrongPlace);

        // Test 2 right colour right place, 1 right colour wrong place, 1 wrong
        // Where the wrong one appears elsewhere in the pattern in the right place
        System.out.println("2 right colour right place, 1 right colour wrong place, wrong appears elsewhere");
        colours[0] = Colour.RED;
        colours[1] = Colour.RED;
        colours[2] = Colour.GREEN;
        colours[3] = Colour.RED;
        g = new Guess(new Pattern(colours));
        maker.CheckGuess(g);
        assertEquals("numRightColourRightPlace", 2, g.numRightColourRightPlace);
        assertEquals("numRightColourWrongPlace", 1, g.numRightColourWrongPlace);
    }
}