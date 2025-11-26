
package loaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import jeopardy.model.Question;

import java.io.IOException;
import java.util.*;

public class JsonQuestionLoader implements QuestionLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        List<Map<String, Object>> rawQuestions;
        try {
            rawQuestions = objectMapper.readValue(
                objectMapper.writeValueAsString(
                    objectMapper.readTree(filePath)
                ),
                new TypeReference<>() {}
            );
        } catch (MismatchedInputException e) {
            throw new IOException("File is not a valid JSON array", e);
        }

        Map<String, Map<Integer, Question>> gameBoard = new LinkedHashMap<>();

        for (int i = 0; i < rawQuestions.size(); i++) {
            Map<String, Object> item = rawQuestions.get(i);
            int lineNumber = i + 2; // 1-based + header-like

            validateRequiredFields(item, lineNumber);

            String category = getString(item, "Category", lineNumber);
            int value = getInt(item, "Value", lineNumber);
            String questionText = getString(item, "Question", lineNumber);
            String optA = getString(item, "OptionA", lineNumber);
            String optB = getString(item, "OptionB", lineNumber);
            String optC = getString(item, "OptionC", lineNumber);
            String optD = getString(item, "OptionD", lineNumber);
            char correctAnswer = getChar(item, "CorrectAnswer", lineNumber);

            Question question = new Question(category, value, questionText,
                optA, optB, optC, optD, correctAnswer);

            gameBoard.computeIfAbsent(category, k -> new LinkedHashMap<>());
            if (gameBoard.get(category).containsKey(value)) {
                throw new IOException("Duplicate question for category '" + category +
                    "' and value " + value + " at item " + lineNumber);
            }
            gameBoard.get(category).put(value, question);
        }

        if (gameBoard.isEmpty()) {
            throw new IOException("No valid questions found in file: " + filePath);
        }

        return gameBoard;
    }

    private void validateRequiredFields(Map<String, Object> item, int index) throws IOException {
        String[] required = {"Category", "Value", "Question", "OptionA", "OptionB", "OptionC", "OptionD", "CorrectAnswer"};
        for (String key : required) {
            if (!item.containsKey(key) || item.get(key) == null) {
                throw new IOException("Missing or null field '" + key + "' at item " + index);
            }
        }
    }

    private String getString(Map<String, Object> item, String key, int index) throws IOException {
        Object val = item.get(key);
        if (!(val instanceof String)) {
            throw new IOException("Field '" + key + "' must be a string at item " + index + " (got: " + val + ")");
        }
        return ((String) val).trim();
    }

    private int getInt(Map<String, Object> item, String key, int index) throws IOException {
        Object val = item.get(key);
        try {
            if (val instanceof Integer) {
                return (Integer) val;
            } else if (val instanceof String) {
                return Integer.parseInt((String) val);
            } else if (val instanceof Double || val instanceof Float) {
                double d = ((Number) val).doubleValue();
                if (d == (int) d) return (int) d;
            }
            throw new NumberFormatException("Not an integer");
        } catch (Exception e) {
            throw new IOException("Field '" + key + "' must be an integer at item " + index + " (got: " + val + ")", e);
        }
    }

    private char getChar(Map<String, Object> item, String key, int index) throws IOException {
        String str = getString(item, key, index);
        if (str.length() != 1) {
            throw new IOException("Field '" + key + "' must be a single character (A-D) at item " + index);
        }
        char c = Character.toUpperCase(str.charAt(0));
        if (!"ABCD".contains(String.valueOf(c))) {
            throw new IOException("CorrectAnswer must be A, B, C, or D at item " + index);
        }
        return c;
    }
}
