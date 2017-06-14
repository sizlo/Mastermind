/**
 * Created by tbrier on 14/06/2017.
 */
public class Game {
    private Codebreaker breaker;
    private Codemaker maker;
    private Guess[] guesses;
    private IUserInterface ui;
    private final int numGuessesAllowed = 12;

    public Game(IUserInterface ui){
        this.ui = ui;

        breaker = new Codebreaker(ui);

        maker = new Codemaker();

        guesses = new Guess[numGuessesAllowed];
    }

    public void Play(){
        maker.ChooseTarget();

        ui.ShowState(guesses, breaker.numGuesses, numGuessesAllowed);

        while (breaker.numGuesses < numGuessesAllowed){
            Guess g = breaker.MakeGuess();
            maker.CheckGuess(g);
            guesses[breaker.numGuesses - 1] = g;
            ui.ShowState(guesses, breaker.numGuesses, numGuessesAllowed);
        }
    }
}
