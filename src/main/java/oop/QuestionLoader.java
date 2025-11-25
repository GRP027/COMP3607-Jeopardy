import java.io.IOException;
import java.util.Map;


public interface QuestionLoader {
    Map<String, Map<Integer, Question>> loadQuestions(String filePath) throws IOException;
}