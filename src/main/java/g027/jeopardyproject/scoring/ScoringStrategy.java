package g027.jeopardyproject.scoring;

import g027.jeopardyproject.models.Question;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This interface defines the strategy for scoring answers in the game.
*/
public interface ScoringStrategy {


    int evaluateAnswer(Question question, String playerAnswer);
}