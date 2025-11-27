package g027.jeopardyproject.loaders;
import java.io.IOException;
import java.util.Map;

import g027.jeopardyproject.models.Question;


/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This interface defines the contract for loading questions from a data source.
*/
public interface QuestionLoader {
    Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException;
}