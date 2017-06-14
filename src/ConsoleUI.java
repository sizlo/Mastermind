import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tbrier on 14/06/2017.
 */
public class ConsoleUI implements IUserInterface {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Map char to a colour it represents
    private Map<Character, Colour> charToColour;

    public ConsoleUI(){
        charToColour = new HashMap<Character, Colour>();
        charToColour.put('R', Colour.RED);
        charToColour.put('G', Colour.GREEN);
        charToColour.put('B', Colour.BLUE);
        charToColour.put('Y', Colour.YELLOW);
        charToColour.put('O', Colour.ORANGE);
        charToColour.put('P', Colour.PURPLE);
    }

    @Override
    public void ShowState(){
        System.out.print("ShowState() STUB");
    }

    @Override
    public Guess WaitForGuess(){
        System.out.print("Enter guess pattern (RGBYOP) of length " + Pattern.patternLength);
        return new Guess(ReadPattern());

    }

    private Pattern ReadPattern(){
        Pattern p;
        System.out.print("\n> ");

        try {
            String s = reader.readLine();
            p = ParsePattern(s);
        } catch (IOException e) {
            System.out.print("Something went wrong, try again");
            return ReadPattern();
        }
        catch (ParseException e) {
            return ReadPattern();
        }

        return p;
    }

    private Pattern ParsePattern(String patternString) throws ParseException{
        // Early out for incorrect length
        if (patternString.length() != Pattern.patternLength){
            System.out.print("Incorrect pattern length");
            throw new ParseException();
        }

        Colour[] patternColours = new Colour[Pattern.patternLength];
        for (int i = 0; i < Pattern.patternLength; i++){
            char currentChar = patternString.charAt(i);
            Colour currentColour = charToColour.get(currentChar);
            if (currentColour == null){
                System.out.print("Invalid colour character: " + currentChar);
                throw new ParseException();
            }
            patternColours[i] = currentColour;
        }

        return new Pattern(patternColours);
    }
}
