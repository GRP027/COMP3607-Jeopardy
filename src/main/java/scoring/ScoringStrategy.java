package scoring;

import models.Question;

public interface ScoringStrategy {


    int evaluateAnswer(Question question, String playerAnswer);
}