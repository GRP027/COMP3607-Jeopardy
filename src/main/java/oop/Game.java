package oop;

public class Game {
    private Player[] players;
    private Category[] categories;
    private int currentPlayerIndex;

    public Game(Player[] players, Category[] categories) {
        this.players = players;
        this.categories = categories;
        this.currentPlayerIndex = 0;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    public Category[] getCategories() {
        return categories;
    }
    
}
