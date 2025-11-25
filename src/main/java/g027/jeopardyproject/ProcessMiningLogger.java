package g027.jeopardyproject;
import java.util.List ;

import g027.jeopardyproject.model.Category;
import g027.jeopardyproject.model.Game;
import g027.jeopardyproject.model.Player;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class implements a singleton logger used to capture gameplay events. It uses the Singleton Pattern (only one instance for entire application).
*/

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
