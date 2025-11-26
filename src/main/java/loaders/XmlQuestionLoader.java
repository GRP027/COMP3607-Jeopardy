
package loaders;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jeopardy.model.Question;

import java.io.IOException;
import java.util.*;

// --- DTOs for Jackson XML binding ---
@JacksonXmlRootElement(localName = "questions")
class QuestionsWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "question")
    public List<QuestionXml> questions;
}

class QuestionXml {
    @JacksonXmlProperty(localName = "Category")
    public String Category;

    @JacksonXmlProperty(localName = "Value")
    public String Value; // read as String to handle parsing safely

    @JacksonXmlProperty(localName = "Question")
    public String Question;

    @JacksonXmlProperty(localName = "OptionA")
    public String OptionA;

    @JacksonXmlProperty(localName = "OptionB")
    public String OptionB;

    @JacksonXmlProperty(localName = "OptionC")
    public String OptionC;

    @JacksonXmlProperty(localName = "OptionD")
    public String OptionD;

    @JacksonXmlProperty(localName = "CorrectAnswer")
    public String CorrectAnswer;
}

// --- Loader ---
public class XmlQuestionLoader implements QuestionLoader {

    private static final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        QuestionsWrapper wrapper;
        try {
            wrapper = xmlMapper.readValue(filePath, QuestionsWrapper.class);
        } catch (Exception e) {
            throw new IOException("Invalid XML format", e);
        }

        if (wrapper.questions == null || wrapper.questions.isEmpty()) {
            throw new IOException("No <question> elements found in XML file: " + filePath);
        }

        Map<String, Map<Integer, Question>> gameBoard = new LinkedHashMap<>();

        for (int i = 0; i < wrapper.questions.size(); i++) {
            QuestionXml xml = wrapper.questions.get(i);
            int lineNumber = i + 2;

            String category = validateAndGet(xml.Category, "Category", lineNumber);
            int value = parseValue(xml.Value, "Value", lineNumber);
            String questionText = validateAndGet(xml.Question, "Question", lineNumber);
            String optA = validateAndGet(xml.OptionA, "OptionA", lineNumber);
            String optB = validateAndGet(xml.OptionB, "OptionB", lineNumber);
            String optC = validateAndGet(xml.OptionC, "OptionC", lineNumber);
            String optD = validateAndGet(xml.OptionD, "OptionD", lineNumber);
            char correctAnswer = parseCorrectAnswer(xml.CorrectAnswer, lineNumber);

            Question question = new Question(category, value, questionText,
                optA, optB, optC, optD, correctAnswer);

            gameBoard.computeIfAbsent(category, k -> new LinkedHashMap<>());
            if (gameBoard.get(category).containsKey(value)) {
                throw new IOException("Duplicate question for category '" + category +
                    "' and value " + value + " at question " + lineNumber);
            }
            gameBoard.get(category).put(value, question);
        }

        return gameBoard;
    }

    private String validateAndGet(String value, String fieldName, int index) throws IOException {
        if (value == null || value.trim().isEmpty()) {
            throw new IOException("Missing or empty field '" + fieldName + "' at question " + index);
        }
        return value.trim();
    }

    private int parseValue(String valueStr, String fieldName, int index) throws IOException {
        try {
            return Integer.parseInt(validateAndGet(valueStr, fieldName, index));
        } catch (NumberFormatException e) {
            throw new IOException("Field 'Value' must be an integer at question " + index + " (got: " + valueStr + ")", e);
        }
    }

    private char parseCorrectAnswer(String ansStr, int index) throws IOException {
        ansStr = validateAndGet(ansStr, "CorrectAnswer", index);
        if (ansStr.length() != 1) {
            throw new IOException("CorrectAnswer must be a single character (A-D) at question " + index);
        }
        char c = Character.toUpperCase(ansStr.charAt(0));
        if (!"ABCD".contains(String.valueOf(c))) {
            throw new IOException("CorrectAnswer must be A, B, C, or D at question " + index);
        }
        return c;
    }
}
