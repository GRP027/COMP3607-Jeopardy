package g027.jeopardyproject.model;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class represents a question in the game, including its text, value, answer, category, and whether it has been asked.
*/
public class Question {
    private String text;
    private int value;
    private String answer;
    private String category;
    private boolean asked;
    public Question(String text, int value, String answer, String category) {
        this.text = text;
        this.value = value;
        this.answer = answer;
        this.asked = false;
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAsked() {
        return asked;
    }

    public void setAsked(boolean asked) {
        this.asked = asked;
    }
}