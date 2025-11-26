package loaders;

import java.util.Locale;

public class QuestionLoaderFactory {

    
    public static QuestionLoader createLoader(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        String extension = getFileExtension(filePath).toLowerCase(Locale.ROOT);

        return switch (extension) {
            case "csv" -> new CsvQuestionLoader();
            case "json" -> new JsonQuestionLoader();
            case "xml" -> new XmlQuestionLoader();
            default -> throw new IllegalArgumentException(
                "Unsupported file format: ." + extension + ". Supported: .csv, .json, .xml"
            );
        };
    }

    private static String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filePath.length() - 1) {
            throw new IllegalArgumentException("File has no extension: " + filePath);
        }
        return filePath.substring(lastDotIndex + 1);
    }
}
