package g027.jeopardyproject.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import g027.jeopardyproject.models.GameObserver;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class implements a logger for process mining, recording game events to a CSV file.
*/
public class ProcessMiningLogger implements GameObserver {
    private static final String LOG_FILE = "game_event_log.csv";
    private static final String CSV_HEADER = "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ProcessMiningLogger() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writer.append(CSV_HEADER).append("\n");
        } catch (IOException e) {
            System.err.println("Error initializing process mining log: " + e.getMessage());
        }
    }

    @Override
    public void update(Map<String, String> eventDetails) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        
        // Prepare data for the CSV row, using default/empty values if absent
        String[] rowData = {
            eventDetails.getOrDefault("Case_ID", "1"), 
            eventDetails.getOrDefault("Player_ID", ""),
            eventDetails.getOrDefault("Activity", "UNKNOWN"), 
            timestamp, // ISO timestamp [cite: 40]
            eventDetails.getOrDefault("Category", ""),
            eventDetails.getOrDefault("Question_Value", ""),
            eventDetails.getOrDefault("Answer_Given", ""),
            eventDetails.getOrDefault("Result", ""),
            eventDetails.getOrDefault("Score_After_Play", "")
        };

        // Format and append to CSV file
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            String csvRow = String.join(",", rowData) + "\n";
            writer.append(csvRow);
        } catch (IOException e) {
            System.err.println("Error writing to process mining log: " + e.getMessage());
        }
    }
}