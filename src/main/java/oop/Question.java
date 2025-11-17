package oop;

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