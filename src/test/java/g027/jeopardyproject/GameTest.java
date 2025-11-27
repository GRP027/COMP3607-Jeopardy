package g027.jeopardyproject;


import g027.jeopardyproject.models.*;
import g027.jeopardyproject.scoring.ScoringStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 ONLY — no Mockito.
 * Uses simple stub classes for Player, Category, Question, and ScoringStrategy.
 */
class GameTest {

    private Player p1, p2;
    private Category category;
    private Question q100, q200;
    private Game game;

    @BeforeEach
    void setup() {
        // Players
        p1 = new Player("P1");
        p2 = new Player("P2");

        // Questions
        q100 =new Question( "categoryName1", 300 ,"question1", "optionA", "optionB", "optionC", "optionD", 'A');
        q200 = new Question( "categoryName2", 100 ,"question2", "optionA", "optionB", "optionC", "optionD", 'B');

        // Category
        category = new Category("title", List.of(q100, q200));

        // Game (uses built-in StandardScoring but we override it via reflection)
        game = new Game(List.of(p1, p2), List.of(category));

       
    }

    // --------------------
    // getCurrentPlayer()
    // --------------------
    @Test
    void testGetCurrentPlayer() {
        assertEquals(p1, game.getCurrentPlayer());
    }

    // --------------------
    // selectQuestion()
    // --------------------
    @Test
    void testSelectQuestionSuccess() {
        Question q = game.selectQuestion("History", 100);
        assertEquals(q100, q);
    }

    @Test
    void testSelectQuestionNotFound() {
        Question q = game.selectQuestion("History", 999);
        assertNull(q);
    }

    @Test
    void testSelectQuestionAlreadyAnswered() {
        q100.setAnswered();
        Question q = game.selectQuestion("History", 100);
        assertNull(q);
    }

    // --------------------
    // processAnswer()
    // --------------------
    @Test
    void testProcessAnswerCorrect() {
        game.processAnswer(q100, "correct");
        assertTrue(q100.isAnswered());
        assertEquals(100, p1.getScore());
    }

    @Test
    void testProcessAnswerIncorrect() {
        game.processAnswer(q100, "wrong");
        assertTrue(q100.isAnswered());
        assertEquals(-100, p1.getScore());
    }

    @Test
    void testProcessAnswerNeutral() {
        game.processAnswer(q100, "other");
        assertTrue(q100.isAnswered());
        assertEquals(0, p1.getScore());
    }

    // --------------------
    // nextPlayer()
    // --------------------
    @Test
    void testNextPlayerRotation() {
        assertEquals(p1, game.getCurrentPlayer());

        game.nextPlayer();
        assertEquals(p2, game.getCurrentPlayer());

        game.nextPlayer();
        assertEquals(p1, game.getCurrentPlayer());
    }

    // --------------------
    // isGameOver()
    // --------------------
    @Test
    void testIsGameOver() throws Exception {
        // Simulate all questions answered
        setPrivateField(game, "answeredQuestionsCount", 2);
        setPrivateField(game, "totalQuestions", 2);

        assertTrue(game.isGameOver());
    }

    // --------------------
    // getCategories()
    // --------------------
    @Test
    void testGetCategories() {
        assertEquals(1, game.getCategories().size());
        assertEquals(category, game.getCategories().get(0));
    }

    // -------------------------------------------------
    // Reflection helper (for scoringStrategy replacement)
    // -------------------------------------------------
    private void setPrivateField(Object obj, String field, Object value) {
        try {
            var f = Game.class.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
