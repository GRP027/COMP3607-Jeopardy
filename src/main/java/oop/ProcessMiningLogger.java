package oop;
import java.util.List ;
public class ProcessMiningLogger {
    private Game game;

    public ProcessMiningLogger(Game game) {
        this.game = game;
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public void nextPlayer() {
        game.nextPlayer();
    }

    public List<Category> getCategories() {
        return game.getCategories();
    }
    
}
