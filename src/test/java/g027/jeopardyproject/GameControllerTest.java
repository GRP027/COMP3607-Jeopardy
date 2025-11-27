package g027.jeopardyproject;

import g027.jeopardyproject.models.*;
import g027.jeopardyproject.scoring.*;
import g027.jeopardyproject.controller.GameController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController controller;
    private Game game;
    private Player player;
    private Question question;

    @BeforeEach
    void setUp() {
        // Basic stubs for Players, Questions, Categories
        player = new Player("TestPlayer");
        question = new Question( "categoryName2", 100 ,"question2", "optionA", "optionB", "optionC", "optionD", 'A');  // correct answer = 'A'

        Category category = new Category("History", Map.of(100, question));

        game = new Game(new ArrayList<>(List.of(player)), new ArrayList<>(List.of(category)));

        controller = new GameController(game);
    }

    // --------------------------------------------------------
    // loadQuestions()
    // --------------------------------------------------------
    @Test
    void testLoadQuestionsAddsCategories() throws Exception {
        // Simple stub path — loadQuestions will try loader, but our stub won't load real data.
        // We expect no crash and categories remain modifiable.
        assertDoesNotThrow(() -> controller.loadQuestions("fake/path.csv"));
    }

    // --------------------------------------------------------
    // submitAnswer()
    // --------------------------------------------------------
    @Test
    void testSubmitAnswerCorrect() {
        boolean result = controller.submitAnswer(player, question, "A");

        assertTrue(result);
        assertTrue(player.getScore() > 0);   // ScoringStrategy adds points
        assertTrue(question.isAnswered());   // Game.processAnswer marks it answered
    }

    @Test
    void testSubmitAnswerIncorrect() {
        boolean result = controller.submitAnswer(player, question, "Z");

        assertFalse(result);
        assertEquals(0, player.getScore());  // No points added
        assertTrue(question.isAnswered());   // Still marked answered by Game.processAnswer
    }
}
