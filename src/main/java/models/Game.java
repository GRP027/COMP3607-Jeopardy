package models;

import java.util.List;

public class Game {
    private final List<Player> players;
    private final List<Category> categories;
    private Player currentPlayer;

    public Game(List<Player> players, List<Category> categories) {
        this.players = players;
        this.categories = categories;
        this.currentPlayer = players.get(0);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(nextIndex);
    }

    public List<Category> getCategories() {
        return categories;
    }
    
}
