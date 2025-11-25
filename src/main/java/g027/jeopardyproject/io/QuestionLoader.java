package g027.jeopardyproject.io;

import java.util.List;

import g027.jeopardyproject.model.Category;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This interface defines a how the app will be loading questions from a data source.
*/
public interface QuestionLoader {
    List<Category> load(String path) throws Exception;
}
