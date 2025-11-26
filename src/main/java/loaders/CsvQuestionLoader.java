package loaders;



import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import models.Question;

public class CsvQuestionLoader implements QuestionLoader {

    private static final int EXPECTED_COLUMNS = 8;

    @Override
    public Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        Map<String, Map<Integer, Question>> gameBoard = new LinkedHashMap<>(); // preserve category order

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext();
            if (header == null) {
                throw new IOException("CSV file is empty");
            }

            String[] nextLine;
            int lineNumber = 1; // after header
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                if (nextLine.length != EXPECTED_COLUMNS) {
                    throw new IOException("Invalid row at line " + lineNumber +
                            ": expected " + EXPECTED_COLUMNS + " columns, got " + nextLine.length);
                }

                String category = nextLine[0].trim();
                int value;
                try {
                    value = Integer.parseInt(nextLine[1].trim());
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid value at line " + lineNumber + ": " + nextLine[1], e);
                }

                String questionText = nextLine[2].trim();
                String optA = nextLine[3].trim();
                String optB = nextLine[4].trim();
                String optC = nextLine[5].trim();
                String optD = nextLine[6].trim();
                String correctAnsStr = nextLine[7].trim();

                if (correctAnsStr.length() != 1) {
                    throw new IOException("Invalid correct answer at line " + lineNumber + ": " + correctAnsStr);
                }
                char correctAnswer = Character.toUpperCase(correctAnsStr.charAt(0));
                if (!"ABCD".contains(String.valueOf(correctAnswer))) {
                    throw new IOException("Correct answer must be A, B, C, or D at line " + lineNumber);
                }

                Question question = new Question(category, value, questionText,
                        optA, optB, optC, optD, correctAnswer);

                // Nested map: category → value → question
                gameBoard.computeIfAbsent(category, k -> new LinkedHashMap<>());
                if (gameBoard.get(category).containsKey(value)) {
                    throw new IOException("Duplicate question for category '" + category +
                            "' and value " + value + " at line " + lineNumber);
                }
                gameBoard.get(category).put(value, question);
            }
        } catch (CsvValidationException e) {
            throw new IOException("CSV parsing error", e);
        }

        if (gameBoard.isEmpty()) {
            throw new IOException("No valid questions found in file: " + filePath);
        }

        return gameBoard;
    }
}