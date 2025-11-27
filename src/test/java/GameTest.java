package src.test.java;

import g027.jeopardyproject.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game model;

    @BeforeEach
    public void setUp() {
        model = new Game();
    }

    @Test
    public void testAddPlayerAndRetrieve() {
        Player p = new Player("P1", "Alice");
        model.addPlayer(p);

        assertEquals(1, model.getPlayers().size());
        assertSame(p, model.getPlayers().get(0));
    }

    @Test
    public void testTurnRotation() {
        Player a = new Player("P1", "Alice");
        Player b = new Player("P2", "Bob");

        model.addPlayer(a);
        model.addPlayer(b);

        assertSame(a, model.getCurrentPlayer());
        model.advancePlayer();
        assertSame(b, model.getCurrentPlayer());
        model.advancePlayer();
        assertSame(a, model.getCurrentPlayer());
    }
}
