package g027.jeopardyproject.model;
import java.util.ArrayList;
import java.util.List;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class coordinates between Model, Strategy, Loader, and GUI components to manage game state and player turns.
*/

public class Game {
    private List<Category> categories = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private Player currentPlayer;

    
    public Game() {
        //this.players = players;
        //this.categories = categories;
        this.currentPlayer = players.get(0);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void addCategory(Category c) {
        categories.add(c);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void nextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(nextIndex);
    }

    public List<Category> getCategories() {
        return categories;
    }
    
        public void updateScore(Player p, int delta) {
        p.addScore(delta);
    }
}
