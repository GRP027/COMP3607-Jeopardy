package g027.jeopardyproject.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import g027.jeopardyproject.models.Question;

import java.io.File; 
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap; 
import java.util.Objects; 

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class implements the QuestionLoader interface to load questions from a CSV file.
*/
public class CsvQuestionLoader implements QuestionLoader {

    private static final int EXPECTED_COLUMNS = 8;

    @Override
    public Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException {
        
        Objects.requireNonNull(filePath, "File path cannot be null");
        if (filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }

        
        File csvFile = new File(filePath);
        if (!csvFile.exists() || !csvFile.isFile()) {
            throw new IOException("File not found or is a directory: " + filePath);
        }

        Map<String, Map<Integer, Question>> gameBoard = new LinkedHashMap<>(); 

        
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] header = reader.readNext();
            if (header == null) {
                // If a file is not completely empty, but the first row is blank
                // The CsvReader might return null.
                throw new IOException("CSV file is empty or contains no header row");
            }

            // Optional: You could add a check here to validate the header columns if needed.

            String[] nextLine;
            int lineNumber = 1; // Start line number at 1 for the first data row (after header)
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                
                // CRITICAL VALIDATION: Check for empty lines or truncated rows
                if (nextLine.length != EXPECTED_COLUMNS) {
                    // Check if the row is entirely empty (opencsv might return an array of length 1 with an empty string)
                    if (nextLine.length == 1 && nextLine[0].trim().isEmpty()) {
                        continue; // Skip empty lines silently
                    }
                    
                    throw new IOException("Invalid row at line " + lineNumber +
                                ": expected " + EXPECTED_COLUMNS + " columns, got " + nextLine.length);
                }

                String category = nextLine[0].trim();
                int value;
                try {
                    value = Integer.parseInt(nextLine[1].trim());
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid value (must be an integer) at line " + lineNumber + ": " + nextLine[1], e);
                }
                
                // Extract and trim all required fields
                String questionText = nextLine[2].trim();
                String optA = nextLine[3].trim();
                String optB = nextLine[4].trim();
                String optC = nextLine[5].trim();
                String optD = nextLine[6].trim();
                String correctAnsStr = nextLine[7].trim();

                // Validation for correct answer format
                if (correctAnsStr.length() != 1) {
                    throw new IOException("Invalid correct answer format at line " + lineNumber + ": '" + correctAnsStr + "'. Must be a single character.");
                }
                char correctAnswer = Character.toUpperCase(correctAnsStr.charAt(0));
                // Question constructor handles the A, B, C, D validation, but we can do it here for better error message context
                if (!"ABCD".contains(String.valueOf(correctAnswer))) {
                    throw new IOException("Correct answer must be A, B, C, or D at line " + lineNumber);
                }

                // Call the Question constructor (relies on its internal null/value checks)
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
            throw new IOException("CSV parsing error. Check file structure and delimiters.", e);
        }

        if (gameBoard.isEmpty()) {
            throw new IOException("No valid questions found in file: " + filePath);
        }

        return gameBoard;
    }
}