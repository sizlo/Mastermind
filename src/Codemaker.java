import java.util.Arrays;
import java.util.Random;

/**
 * Created by tbrier on 13/06/2017.
 */
public class Codemaker {
    Pattern targetPattern;

    public void ChooseTarget(){
        Colour[] possibleColours = Colour.values();
        Random generator = new Random();
        Colour[] patternColours = new Colour[Pattern.patternLength];
        for (int i = 0; i < Pattern.patternLength; i++){
            int choice = generator.nextInt(possibleColours.length);
            patternColours[i] = possibleColours[choice];
        }
        targetPattern = new Pattern(patternColours);
    }

    public void CheckGuess(Guess guess){
        Pattern guessPattern = guess.guessPattern;

        // Arrays to track if a peg in the pattern has been accounted for yet
        boolean[] targetAccountedFor = new boolean[Pattern.patternLength];
        boolean[] guessAccountedFor = new boolean[Pattern.patternLength];
        Arrays.fill(targetAccountedFor, false);
        Arrays.fill(guessAccountedFor, false);

        // Work out how many pegs are the right colour in the right place
        for (int i = 0; i < Pattern.patternLength; i++){
            if (guessPattern.PegAt(i) == targetPattern.PegAt(i)){
                targetAccountedFor[i] = true;
                guessAccountedFor[i] = true;
                guess.numRightColourRightPlace++;
            }
        }

        // From what's left in the target pattern work out how many pegs in
        // the guess pattern are still the right colour
        for (int targetIndex = 0; targetIndex < Pattern.patternLength; targetIndex++) {
            if (targetAccountedFor[targetIndex]){
                continue;
            }
            for (int guessIndex = 0; guessIndex < Pattern.patternLength; guessIndex++) {
                if (guessAccountedFor[guessIndex]){
                    continue;
                }
                if (guessPattern.PegAt(guessIndex) == targetPattern.PegAt(targetIndex)){
                    targetAccountedFor[targetIndex] = true;
                    guessAccountedFor[guessIndex] = true;
                    guess.numRightColourWrongPlace++;
                }
            }
        }
    }
}
