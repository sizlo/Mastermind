/**
 * Created by tbrier on 13/06/2017.
 */
public class Main {
    public static void main(String[] args) {
        IUserInterface ui = new ConsoleUI();
        Game game = new Game(ui);
        game.Play();
    }
}
