package loaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import models.Question;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap; // Required for LinkedHashMap usage

public class JsonQuestionLoader implements QuestionLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        File jsonFile = new File(filePath);
        if (!jsonFile.exists() || !jsonFile.isFile()) {
            throw new IOException("File not found or is a directory: " + filePath);
        }

        List<Map<String, Object>> rawQuestions;
        try {
            // CRITICAL FIX: Read the JSON file directly from the disk using File object.
            rawQuestions = objectMapper.readValue(
                jsonFile,
                new TypeReference<List<Map<String, Object>>>() {}
            );
        } catch (MismatchedInputException e) {
            throw new IOException("File content is not a valid JSON array of objects", e);
        }

        // Using LinkedHashMap to preserve category insertion order
        Map<String, Map<Integer, Question>> gameBoard = new LinkedHashMap<>();

        for (int i = 0; i < rawQuestions.size(); i++) {
            Map<String, Object> item = rawQuestions.get(i);
            // i + 2 is used to provide a 1-based index plus an offset, assuming a header
            int lineNumber = i + 2; 

            validateRequiredFields(item, lineNumber);

            String category = getString(item, "Category", lineNumber);
            int value = getInt(item, "Value", lineNumber);
            String questionText = getString(item, "Question", lineNumber);
            String optA = getString(item, "OptionA", lineNumber);
            String optB = getString(item, "OptionB", lineNumber);
            String optC = getString(item, "OptionC", lineNumber);
            String optD = getString(item, "OptionD", lineNumber);
            char correctAnswer = getChar(item, "CorrectAnswer", lineNumber);

            // Calls the constructor with all the validation logic
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

    // Helper methods are unchanged as they were already robust

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
            } 
            // Removed float/double truncation for strict integer requirement
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
