package oop;

import java.util.List;

public class Category {
    private String title;
    private List<Question> questions;

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