package src.test.java;

import src.main.java.g027.jeopardyproject.io.CsvQuestionLoader;
import g027.jeopardyproject.model.Category;
import g027.jeopardyproject.model.Question;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
        List<Category> cats = loader.load(tmp.toString());

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
        List<Category> cats = loader.load(tmp.toString());

        assertEquals(2, cats.size());  // Should load valid rows only
    }
}
