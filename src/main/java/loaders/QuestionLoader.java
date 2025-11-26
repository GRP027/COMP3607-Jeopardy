package loaders;
import java.io.IOException;
import java.util.Map;
import models.Question;



public interface QuestionLoader {
    Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException;
}