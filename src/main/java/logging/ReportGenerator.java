
package logging;

import models.GameObserver;
import models.Player;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ReportGenerator implements GameObserver {
    
    private final String reportFilePath = "summary_game_report.txt";
    private final List<Map<String, String>> gameHistory = new ArrayList<>();
    private final List<Player> finalPlayers;
    private String caseId; 

    public ReportGenerator(List<Player> players) {
        
        this.finalPlayers = players;
    }

    @Override
    public void update(Map<String, String> eventDetails) {
        
        gameHistory.add(eventDetails);

        
        if (caseId == null && eventDetails.containsKey("Case_ID")) {
            this.caseId = eventDetails.get("Case_ID");
        }
        
       
        if ("End Game".equalsIgnoreCase(eventDetails.get("Activity"))) {
            generateReport();
        }
    }

    
    public void generateReport() {
        try (FileWriter writer = new FileWriter(reportFilePath)) {
            
            
            writer.write("JEOPARDY PROGRAMMING GAME REPORT\n");
            writer.write("================================\n\n");
            writer.write("Case ID: " + (caseId != null ? caseId : "N/A") + "\n");
            
            String playerNames = finalPlayers.stream()
                .map(Player::getName)
                .collect(java.util.stream.Collectors.joining(", "));
            writer.write("Players: " + playerNames + "\n\n");

            
            writer.write("Gameplay Summary (Turn-by-Turn Rundown):\n");
            writer.write("----------------------------------------\n");

            int turnNumber = 1;
            for (Map<String, String> event : gameHistory) {
                // Focus on key interaction points: Answer Question events
                if ("Answer Question".equalsIgnoreCase(event.get("Activity"))) {
                    String player = event.getOrDefault("Player_ID", "N/A");
                    String category = event.getOrDefault("Category", "");
                    String value = event.getOrDefault("Question_Value", "0");
                    String answerGiven = event.getOrDefault("Answer_Given", "[N/A]");
                    String result = event.getOrDefault("Result", "Neutral");
                    String scoreAfter = event.getOrDefault("Score_After_Play", "0");
                    
                    String scoreChange = (result.equals("Correct") ? "+" : "-") + value + " pts";
                    
                    writer.write(String.format("Turn %d: %s answered %s for %s points\n", 
                        turnNumber++, player, category, value));
                    
                    // NOTE: You would typically look up the Question Text here if you had stored a reference to the Question object
                    // For this simple Observer, we focus on the result:
                    writer.write(String.format("Answer given: %s — %s (%s)\n", 
                        answerGiven, result, scoreChange));
                    
                    writer.write(String.format("Score after turn: %s = %s\n\n", player, scoreAfter));
                }
            }
            
            // --- 3. Final Scores ---
            writer.write("Final Scores:\n");
            writer.write("-------------\n");
            
            // The finalPlayers list has the most up-to-date score because it's passed by reference
            finalPlayers.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore())) // Sort highest score first
                .forEach(p -> {
                    try {
                        writer.write(String.format("%s: %d\n", p.getName(), p.getScore()));
                    } catch (IOException e) {
                        // Suppress exception or handle logging
                    }
                });

            System.out.println("Summary Game Report successfully generated at: " + reportFilePath);

        } catch (IOException e) {
            System.err.println("Error writing summary report: " + e.getMessage());
        }
    }
}