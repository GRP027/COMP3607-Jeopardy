package models;


import java.util.List;


public class Category {
    private final String title;
    private final List<Question> questions;

    public Category(String title, List<Question> questions) {
        this.title = title;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

}