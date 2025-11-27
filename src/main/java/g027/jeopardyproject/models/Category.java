package g027.jeopardyproject.models;

import java.util.List;
import java.util.Map;

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
    private final String title;
    private final List<Question> questions;

    public Category(String title, List<Question> questions) {
        this.title = title;
        this.questions = questions;
    }

    public Category(String categoryName, Map<Integer,Question> questions2) {
        this.title = categoryName;
        this.questions = questions2.values().stream().toList(); 
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

}