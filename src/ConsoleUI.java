import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tbrier on 14/06/2017.
 */
public class ConsoleUI implements IUserInterface {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Map char to a colour it represents
    private Map<Character, Colour> charToColour;

    // Map a colour to the char that represents it
    private Map<Colour, Character> colourToChar;

    // Map a colour to the string needed to print in that colour
    private Map<Colour, String> colourToAnsiColour;

    private final String resetColour = "\u001B[0m";

    public ConsoleUI(){
        charToColour = new HashMap<Character, Colour>();
        charToColour.put('R', Colour.RED);
        charToColour.put('G', Colour.GREEN);
        charToColour.put('B', Colour.BLUE);
        charToColour.put('Y', Colour.YELLOW);
        charToColour.put('C', Colour.CYAN);
        charToColour.put('P', Colour.PURPLE);

        colourToChar = new EnumMap<Colour, Character>(Colour.class);
        colourToChar.put(Colour.RED, 'R');
        colourToChar.put(Colour.GREEN, 'G');
        colourToChar.put(Colour.BLUE, 'B');
        colourToChar.put(Colour.YELLOW, 'Y');
        colourToChar.put(Colour.CYAN, 'C');
        colourToChar.put(Colour.PURPLE, 'P');

        colourToAnsiColour = new EnumMap<Colour, String>(Colour.class);
        colourToAnsiColour.put(Colour.RED, "\u001B[31m");
        colourToAnsiColour.put(Colour.GREEN, "\u001B[32m");
        colourToAnsiColour.put(Colour.BLUE, "\u001B[34m");
        colourToAnsiColour.put(Colour.YELLOW, "\u001B[33m");
        colourToAnsiColour.put(Colour.CYAN, "\u001B[36m");
        colourToAnsiColour.put(Colour.PURPLE, "\u001B[35m");
    }

    @Override
    public void ShowState(Guess[] guesses, int numGuessesSoFar, int numGuessesAllowed, boolean revealCode, Pattern targetPattern){
        PrintCodeSection(revealCode, targetPattern);
        for(int i = 0; i < numGuessesAllowed - numGuessesSoFar; i++){
            PrintEmptyGuess();
        }
        for (int i = numGuessesSoFar - 1; i >= 0; i--){
            PrintGuess(guesses[i]);
        }
    }

    @Override
    public Guess WaitForGuess(){
        System.out.print("Enter guess pattern (RGBYCP) of length " + Pattern.patternLength);
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

    private void PrintCodeSection(boolean revealCode, Pattern targetPattern){
        int resultLength = Pattern.patternLength / 2;
        if (Pattern.patternLength % 2 != 0){
            resultLength++;
        }

        String seperator = "";
        String codeline = "";
        String emptyline = "";
        for (int i = 0; i < resultLength; i++){
            seperator += "=";
            codeline += " ";
            emptyline += " ";
        }
        seperator += "==";
        codeline+= " |";
        emptyline += " |";
        for (int i = 0; i < Pattern.patternLength; i++){
            seperator += "==";
            if (revealCode){
                codeline += " " + GetColourString(targetPattern.PegAt(i));
            }
            else {
                codeline += " ?";
            }
            emptyline += "  ";
        }

        System.out.println(seperator);
        System.out.println(codeline);
        System.out.println(emptyline);
        System.out.println(seperator);
    }

    private void PrintEmptyGuess(){
        int resultLength = Pattern.patternLength / 2;
        if (Pattern.patternLength % 2 != 0){
            resultLength++;
        }

        String seperator = "";
        String codeline = "";
        String emptyline = "";

        for (int i = 0; i < resultLength; i++){
            seperator += "-";
            codeline += " ";
            emptyline += " ";
        }

        seperator += "--";
        codeline += " |";
        emptyline += " |";

        for (int i = 0; i < Pattern.patternLength; i++){
            seperator += "--";
            codeline += "  ";
            emptyline += "  ";
        }

        System.out.println(codeline);
        System.out.println(emptyline);
        System.out.println(seperator);
    }

    private String GetColourString(Colour c){
        return colourToAnsiColour.get(c) + colourToChar.get(c) + resetColour;
    }

    private void PrintGuess(Guess g){
        String resultStringOneLine = "";
        for (int i = 0; i < g.numRightColourRightPlace; i++){
            resultStringOneLine += "●";
        }
        for (int i = 0; i < g.numRightColourWrongPlace; i++){
            resultStringOneLine += "○";
        }
        int totalResultPegs = g.numRightColourRightPlace + g.numRightColourWrongPlace;
        for (int i = 0; i < Pattern.patternLength - totalResultPegs; i++){
            resultStringOneLine += " ";
        }
        if (Pattern.patternLength % 2 != 0){
            resultStringOneLine += " ";
        }
        int resultStringMidpoint = resultStringOneLine.length() / 2;

        int resultLength = Pattern.patternLength / 2;
        if (Pattern.patternLength % 2 != 0){
            resultLength++;
        }

        String seperator = "";
        String codeline = resultStringOneLine.substring(0, resultStringMidpoint);
        String emptyline = resultStringOneLine.substring(resultStringMidpoint);

        for (int i = 0; i < resultLength; i++){
            seperator += "-";
        }

        seperator += "--";
        codeline += " |";
        emptyline += " |";

        for (int i = 0; i < Pattern.patternLength; i++){
            seperator += "--";
            codeline += " " + GetColourString(g.guessPattern.PegAt(i));
            emptyline += "  ";
        }

        System.out.println(codeline);
        System.out.println(emptyline);
        System.out.println(seperator);
    }
}
