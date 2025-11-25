package g027.jeopardyproject.model;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class represents a question in the game, including its multiple choice options, value, the correct answer, category, and whether it has been asked.
*/
public class Question {
    private String question;
    private int value;
    private char correctAnswer;
    private String a;
    private String b;
    private String c;
    private String d;
    private String category;
    private boolean asked;
    
    public Question(String category, int value, String question, String a, String b, String c, String d, char correctAnswer) {
        this.question = question;
        this.value = value;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.correctAnswer = correctAnswer;
        this.asked = false;
        this.category = category;
    }

    public String getquestion() {
        return question;
    }

    public int getValue() {
        return value;
    }

    public String getA() {
        return a;
    }
    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public char getcorrectAnswer() {
        return correctAnswer;
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