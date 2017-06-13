/**
 * Created by tbrier on 13/06/2017.
 */
public class Guess {
    public final Pattern guessPattern;
    public int numRightColourWrongPlace;
    public int numRightColourRightPlace;

    public Guess(Pattern p){
        guessPattern = p;
        numRightColourWrongPlace = 0;
        numRightColourRightPlace = 0;
    }
}
