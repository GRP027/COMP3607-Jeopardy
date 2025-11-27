package g027.jeopardyproject.models;
import java.util.Objects;

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
    private final String category;
    private final int value;
    private final String questionText;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final char correctAnswer; 
    private boolean isAnswered = false;

    public Question(String category, int value, String questionText,
                    String optionA, String optionB, String optionC,
                    String optionD, char correctAnswer) {
        this.category = category;
        this.value = value;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        if (!"ABCD".contains(String.valueOf(correctAnswer))) {
            throw new IllegalArgumentException("CorrectAnswer must be A, B, C, or D");
        }
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public String getCategory() { return category; }
    public int getValue() { return value; }
    public String getQuestionText() { return questionText; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public char getCorrectAnswer() { return correctAnswer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question q = (Question) o;
        return value == q.value &&
               correctAnswer == q.correctAnswer &&
               Objects.equals(category, q.category) &&
               Objects.equals(questionText, q.questionText) &&
               Objects.equals(optionA, q.optionA) &&
               Objects.equals(optionB, q.optionB) &&
               Objects.equals(optionC, q.optionC) &&
               Objects.equals(optionD, q.optionD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, value, questionText, optionA, optionB, optionC, optionD, correctAnswer);
    }

    public boolean isAnswered() {
        return isAnswered;
    }
    public void setAnswered() {
        this.isAnswered = true;
    }
    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", value=" + value +
                ", questionText='" + questionText + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}