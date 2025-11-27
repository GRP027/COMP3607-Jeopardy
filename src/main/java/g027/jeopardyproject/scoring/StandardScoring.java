// src/main/java/scoring/StandardScoring.java

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

This class implements the standard scoring strategy for evaluating player answers.
*/
public class StandardScoring implements ScoringStrategy {

    @Override
    public int evaluateAnswer(Question question, String playerAnswer) {
        if (question == null || playerAnswer == null || playerAnswer.trim().isEmpty()) {
            return 0; 
        }

        
        String standardizedAnswer = playerAnswer.trim().toUpperCase();
        
        
        char correctAnswerChar = question.getCorrectAnswer(); 
        
        
        if (standardizedAnswer.length() == 1 && standardizedAnswer.charAt(0) == correctAnswerChar) {
            
            return question.getValue();
        } else {
            
            return -question.getValue();
        }
    }
}
