/**
 * Created by tbrier on 13/06/2017.
 */
public class Pattern {
    private final Colour[] patternColours;
    public static final int patternLength = 4;

    public Pattern(Colour[] p){
        patternColours = p.clone();
    }

    public Colour PegAt(int index){
        return patternColours[index];
    }
}
