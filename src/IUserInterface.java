/**
 * Created by tbrier on 14/06/2017.
 */
public interface IUserInterface {
    public void ShowState(Guess[] guesses, int numGuessesSoFar, int numGuessesAllowed);
    public Guess WaitForGuess();
}
