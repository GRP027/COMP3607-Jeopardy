package src.test.java;

import g027.jeopardyproject.controller.GameController;
import g027.jeopardyproject.logging.EventLogger;
import g027.jeopardyproject.model.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    @Test
    public void testCorrectAnswerIncreasesScore() throws Exception {
        GameModel model = new GameModel();
        Player p = new Player("P1", "Tester");
        model.addPlayer(p);

        Question q = new Question("General", 50, "Test?", "A", "B", "C", "D", 'C');
        Category c = new Category("General");
        c.addQuestion(q);

        model.setCategories(Collections.singletonList(c));

        Path tempLog = Files.createTempFile("log", ".csv");
        EventLogger logger = new EventLogger(tempLog, "TESTCASE");

        GameController ctrl = new GameController(model, logger);

        boolean ok = ctrl.answerQuestion(p, q, 'C');
        assertTrue(ok);
        assertEquals(50, p.getScore());
        assertTrue(q.isAnswered());
    }
}
