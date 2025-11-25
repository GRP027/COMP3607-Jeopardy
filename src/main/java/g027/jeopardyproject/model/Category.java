package g027.jeopardyproject.model;
import java.util.ArrayList;
import java.util.List;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class represents a category in the game, containing a title and a list of questions.
*/
public class Category {
    private String title;
    private List<Question> questions= new ArrayList<>();

    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getName() { 
        return title;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

}