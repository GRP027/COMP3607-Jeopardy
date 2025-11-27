package src.test.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import g027.jeopardyproject.strategy.ExactMatchStrategy;
import g027.jeopardyproject.strategy.AnswerStrategy;

class AnswerStrategyTest {

    private final AnswerStrategy strat = new ExactMatchStrategy();

    @Test
    void exactMatchSameLetter() {
        assertTrue(strat.isCorrect('A', 'A'));
        assertTrue(strat.isCorrect('b', 'B'));
    }

    @Test
    void mismatchedAnswer() {
        assertFalse(strat.isCorrect('C', 'D'));
    }
}
