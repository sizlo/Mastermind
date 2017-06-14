/**
 * Created by tbrier on 14/06/2017.
 */
public class Codebreaker {
    public int numGuesses;
    private IUserInterface ui;

    public Codebreaker(IUserInterface ui){
        this.ui = ui;
    }

    public Guess MakeGuess(){
        Guess g = ui.WaitForGuess();
        numGuesses++;
        return g;
    }
}
