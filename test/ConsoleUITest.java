import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by tbrier on 14/06/2017.
 */
public class ConsoleUITest {
    @BeforeClass
    public static void setUp() throws Exception {
        // Tests designed for a patten length of 4
        assertEquals("Pattern length",4, Pattern.patternLength);
    }

    private boolean useFakeStream = true;
    private void SetInput(InputStream stream){
        if (useFakeStream){
            System.setIn(stream);
        }
    }
    
    @Test
    public void AllOneColour() throws Exception {
        String input = "RRRR\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        SetInput(inputStream);

        Pattern expected = new Pattern(new Colour[]{Colour.RED, Colour.RED, Colour.RED, Colour.RED});

        ConsoleUI ui = new ConsoleUI();
        Guess g = ui.WaitForGuess();

        assertTrue("AllOneColour", expected.equals(g.guessPattern));

        SetInput(System.in);
    }

    @Test
    public void AllDifferentColours() throws Exception {
        String input = "RGBY\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        SetInput(inputStream);

        Pattern expected = new Pattern(new Colour[]{Colour.RED, Colour.GREEN, Colour.BLUE, Colour.YELLOW});

        ConsoleUI ui = new ConsoleUI();
        Guess g = ui.WaitForGuess();

        assertTrue("AllDifferentColours", expected.equals(g.guessPattern));

        SetInput(System.in);
    }

    @Test
    public void TooShort() throws Exception {
        String input = "";
        input += "RRR\n";
        input += "GGGG\n";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        SetInput(inputStream);

        Pattern expected = new Pattern(new Colour[]{Colour.GREEN, Colour.GREEN, Colour.GREEN, Colour.GREEN});

        ConsoleUI ui = new ConsoleUI();
        Guess g = ui.WaitForGuess();

        assertTrue("TooShort", expected.equals(g.guessPattern));

        SetInput(System.in);
    }

    @Test
    public void TooLong() throws Exception {
        String input = "";
        input += "RRRRR\n";
        input += "GGGG\n";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        SetInput(inputStream);

        Pattern expected = new Pattern(new Colour[]{Colour.GREEN, Colour.GREEN, Colour.GREEN, Colour.GREEN});

        ConsoleUI ui = new ConsoleUI();
        Guess g = ui.WaitForGuess();

        assertTrue("TooLong", expected.equals(g.guessPattern));

        SetInput(System.in);
    }

    @Test
    public void EmptyString() throws Exception {
        String input = "";
        input += "\n";
        input += "GGGG\n";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        SetInput(inputStream);

        Pattern expected = new Pattern(new Colour[]{Colour.GREEN, Colour.GREEN, Colour.GREEN, Colour.GREEN});

        ConsoleUI ui = new ConsoleUI();
        Guess g = ui.WaitForGuess();

        assertTrue("EmptyString", expected.equals(g.guessPattern));

        SetInput(System.in);
    }

    @Test
    public void InvalidCharacter() throws Exception {
        String input = "";
        input += "RRRZ\n";
        input += "GGGG\n";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        SetInput(inputStream);

        Pattern expected = new Pattern(new Colour[]{Colour.GREEN, Colour.GREEN, Colour.GREEN, Colour.GREEN});

        ConsoleUI ui = new ConsoleUI();
        Guess g = ui.WaitForGuess();

        assertTrue("InvalidCharacter", expected.equals(g.guessPattern));

        SetInput(System.in);
    }

    public static void main(String[] args) throws Exception{
        setUp();

        ConsoleUITest tester = new ConsoleUITest();
        tester.useFakeStream = false;

        tester.AllOneColour();
        tester.AllDifferentColours();
        tester.TooShort();
        tester.TooLong();
        tester.EmptyString();
        tester.InvalidCharacter();
    }
}