package g027.jeopardyproject.strategy;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This interface defines a strategy for checking if a given answer is correct.
*/
public interface AnswerStrategy {
    boolean isCorrect(String expected, String given);
}
