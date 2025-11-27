package g027.jeopardyproject.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import g027.jeopardyproject.models.Category;
import g027.jeopardyproject.models.Question;


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

    @Override
    public List<Category> load(String path) throws Exception {

        Map<String, Category> categories = new LinkedHashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine(); // skip header

        String line;
        while ((line = br.readLine()) != null) {

            // The file format: 8 columns
            // Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer
            String[] parts = line.split(",", 8);

            String categoryName = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            String questionText = parts[2].trim();

            String optionA = parts[3].trim();
            String optionB = parts[4].trim();
            String optionC = parts[5].trim();
            String optionD = parts[6].trim();

            char correct = parts[7].trim().toUpperCase().charAt(0);

            categories.putIfAbsent(categoryName, new Category(categoryName));

            Question q = new Question(
                categoryName,
                value,
                questionText,
                optionA, optionB, optionC, optionD,
                correct
            );

            categories.get(categoryName).addQuestion(q);
        }

        return new ArrayList<>(categories.values());
    }
}
