package g027.jeopardyproject;

import g027.jeopardyproject.loaders.CsvQuestionLoader;
import g027.jeopardyproject.models.*;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CsvQuestionLoaderTest {

    static final String TEST_CSV =
        "Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\n" +
        "Math,100,What is 2+2?,1,2,3,4,D\n" +
        "Science,200,What is H2O?,H₂,Water,HO,OH,B\n";

    @Test
    public void testLoadValidCsv() throws Exception {
        Path tmp = Files.createTempFile("test-questions", ".csv");
        try (FileWriter fw = new FileWriter(tmp.toFile())) {
            fw.write(TEST_CSV);
        }

        CsvQuestionLoader loader = new CsvQuestionLoader();
        Map<String, Map<Integer, Question>> cats = loader.loadQuestions(tmp.toString());

        assertEquals(2, cats.size());
    }

    @Test
    public void testLoadMalformedCsv_skipsBadRows() throws Exception {
        String bad = TEST_CSV + "BadLineWithoutEnoughColumns\n";

        Path tmp = Files.createTempFile("bad-questions", ".csv");
        try (FileWriter fw = new FileWriter(tmp.toFile())) {
            fw.write(bad);
        }

        CsvQuestionLoader loader = new CsvQuestionLoader();
        Map<String, Map<Integer, Question>> cats = loader.loadQuestions(tmp.toString());

        assertEquals(2, cats.size());  // Should load valid rows only
    }
}
