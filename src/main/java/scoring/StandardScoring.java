// src/main/java/scoring/StandardScoring.java

package scoring;

import models.Question;


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
