package g027.jeopardyproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import g027.jeopardyproject.scoring.*;
import java.util.*;
import g027.jeopardyproject.models.*;
import org.junit.jupiter.api.BeforeEach;

public class StandardScoringTest {

    private final ScoringStrategy strat = new StandardScoring();
    private final Question question = new Question( "categoryName2", 100 ,"question2", "optionA", "optionB", "optionC", "optionD", 'A');;

    @Test
    void exactMatchSameLetter() {
        assertTrue(strat.evaluateAnswer(question, "A")==0);
        assertTrue(strat.evaluateAnswer(question, "a")==0);
    }

    @Test
    void mismatchedAnswer() {
        assertFalse(strat.evaluateAnswer(question, "D")==0);
    }
}
