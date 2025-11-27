package g027.jeopardyproject.controller;

import java.util.List;

import g027.jeopardyproject.io.CsvQuestionLoader;
import g027.jeopardyproject.io.QuestionLoader;
import g027.jeopardyproject.model.Category;
import g027.jeopardyproject.model.Game;
import g027.jeopardyproject.model.Player;
import g027.jeopardyproject.model.Question;
import g027.jeopardyproject.strategy.AnswerStrategy;
import g027.jeopardyproject.strategy.ExactMatchStrategy;

public class GameController {

    private final Game model;
    private AnswerStrategy strategy = new ExactMatchStrategy();
    private final QuestionLoader loader = new CsvQuestionLoader();

    public GameController(Game model) {
        this.model = model;
    }

    public void loadQuestions(String path) throws Exception {
        List<Category> categories = loader.load(path);
        for (Category c : categories) model.addCategory(c);
    }
//muiltiple choice function to be added later
    public boolean submitAnswer(Player p, Question q, String userAnswer) {
        boolean correct = strategy.isCorrect(String.valueOf(q.getcorrectAnswer()), userAnswer);

        int delta = correct ? q.getValue() : -q.getValue();
        model.updateScore(p, delta);
        //model.nextTurn();

        return correct;
    }
    
}

