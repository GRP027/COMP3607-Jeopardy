package g027.jeopardyproject.controller;

import java.util.Map;

import g027.jeopardyproject.loaders.CsvQuestionLoader;
import g027.jeopardyproject.loaders.QuestionLoader;
import g027.jeopardyproject.models.Category;
import g027.jeopardyproject.models.Game;
import g027.jeopardyproject.models.Player;
import g027.jeopardyproject.models.Question;
import g027.jeopardyproject.scoring.ScoringStrategy;
import g027.jeopardyproject.scoring.StandardScoring;

public class GameController {

    private final Game model;
    private ScoringStrategy strategy = new StandardScoring();
    private final QuestionLoader loader = new CsvQuestionLoader();

    public GameController(Game model) {
        this.model = model;
    }

    public void loadQuestions(String path) throws Exception {
        Map<String, Map<Integer, Question>> categories = loader.loadQuestions(path);
        for (Map.Entry<String, Map<Integer, Question>> entry : categories.entrySet()) {
            String categoryName = entry.getKey();
            Map<Integer, Question> questions = entry.getValue();
            model.getCategories().add(new Category(categoryName, questions));
        }
    }

    
    public boolean submitAnswer(Player p, Question q, String userAnswer) {
        model.processAnswer(q, userAnswer);
        if (q.getCorrectAnswer() == userAnswer.charAt(0)) {
            int points = strategy.evaluateAnswer(q, userAnswer);
            p.addScore(points);
            return true;
        } else {
            return false;
        }
    }
    
}

