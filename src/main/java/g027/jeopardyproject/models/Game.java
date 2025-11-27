package g027.jeopardyproject.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;

import g027.jeopardyproject.logging.ProcessMiningLogger;
import g027.jeopardyproject.scoring.ScoringStrategy;
import g027.jeopardyproject.scoring.StandardScoring;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This class coordinates between Model, Strategy, Loader, and GUI components to manage game state and player turns.
*/
public class Game {
    
    
    private final List<Player> players;
    private final List<Category> categories;
    private Player currentPlayer;
private final List<GameObserver> observers = new ArrayList<>();
    private final String caseId = "GAME-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    private int totalQuestions = 0;
    private int answeredQuestionsCount = 0;
    private final ScoringStrategy scoringStrategy; 

    public Game(List<Player> players, List<Category> categories) {
        this.players = players;
        this.categories = categories;
        
        
        if (players == null || players.isEmpty() || players.size() > 4) {
            throw new IllegalArgumentException("Game must support 1 to 4 players.");
        }
        this.currentPlayer = players.get(0);
        
        
        this.scoringStrategy = new StandardScoring(); 

        // Observer Pattern Initialization and Logging Setup
        this.observers.add(new ProcessMiningLogger());
        // Add your ReportGenerator observer here:
        // this.observers.add(new ReportGenerator());
        
        // Calculate total questions for game end condition
        this.totalQuestions = categories.stream()
                .flatMap(c -> c.getQuestions().stream())
                .collect(Collectors.toList())
                .size();

        // Log Game Start event
        notifyObservers("Start Game", null, null, null, "Successful", null, "Total Questions: " + totalQuestions);
    }

    // --- CORE GAMEPLAY METHODS ---

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayerList() {
        return this.players;
    }


    
    public Question selectQuestion(String categoryTitle, int value) {
        Question selectedQuestion = this.categories.stream()
            .flatMap(c -> c.getQuestions().stream())
            .filter(q -> q.getCategory().equalsIgnoreCase(categoryTitle) && q.getValue() == value)
            .findFirst()
            .orElse(null);

        if (selectedQuestion == null || selectedQuestion.isAnswered()) {
            String reason = (selectedQuestion == null) ? "Question not found" : "Question already answered";
            notifyObservers("Select Question", currentPlayer, selectedQuestion, null, "Failed", null, reason);
            return null;
        }

        
        notifyObservers("Select Question", currentPlayer, selectedQuestion, null, "Successful", null, null);
        return selectedQuestion;
    }

    
    public void processAnswer(Question question, String playerAnswer) {
        if (question == null) return;

        
        question.setAnswered();
        answeredQuestionsCount++;

        
        int scoreChange = scoringStrategy.evaluateAnswer(question, playerAnswer);
        
        
        String result = scoreChange > 0 ? "Correct" : (scoreChange < 0 ? "Incorrect" : "Neutral");

        // 3. Update player score
        if (scoreChange > 0) {
            currentPlayer.addScore(scoreChange);
        } else if (scoreChange < 0) {
            currentPlayer.subtractScore(Math.abs(scoreChange));
        }
        
        // 4. Log Answer and Score Update
        notifyObservers("Answer Question", currentPlayer, question, playerAnswer, result, null, null);
        notifyObservers("Score Updated", currentPlayer, question, null, "Successful", String.valueOf(currentPlayer.getScore()), null);
        
        // 5. Check for game end and advance turn
        if (isGameOver()) {
            notifyObservers("End Game", null, null, null, "Successful", null, "All questions answered. Generating Report.");
            // Trigger report generation here (e.g., call a method on the ReportGenerator observer)
        } else {
            nextPlayer();
        }
    }

    /**
     * Advances to the next player in the list.
     */
    public void nextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(nextIndex);
    }

    /**
     * Checks if all questions have been answered.
     */
    public boolean isGameOver() {
        return answeredQuestionsCount >= totalQuestions;
    }

    public List<Category> getCategories() {
        return categories;
    }

    // --- OBSERVER PATTERN IMPLEMENTATION ---

    /**
     * Gathers event data and notifies all registered observers (Process Mining Log, Report Generator).
     * This is the core Subject logic.
     */
    private void notifyObservers(String activity, Player player, Question question, 
                                 String answerGiven, String result, String scoreAfterPlay, 
                                 String customDetails) {
        
        Map<String, String> eventDetails = new LinkedHashMap<>();
        
        // 1. Core Process Mining Columns (must match the required CSV header order)
        eventDetails.put("Case_ID", this.caseId);
        eventDetails.put("Player_ID", player != null ? player.getName() : "System");
        eventDetails.put("Activity", activity);
        eventDetails.put("Timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // 2. Contextual Columns
        eventDetails.put("Category", question != null ? question.getCategory() : "");
        eventDetails.put("Question_Value", question != null ? String.valueOf(question.getValue()) : "");
        eventDetails.put("Answer_Given", answerGiven != null ? answerGiven : "");
        
        // 3. Outcome
        eventDetails.put("Result", result != null ? result : customDetails);
        
        // 4. Final Score State
        eventDetails.put("Score_After_Play", scoreAfterPlay != null ? scoreAfterPlay : 
                                            (player != null ? String.valueOf(player.getScore()) : ""));

        // Notify all registered GameObserver instances
        for (GameObserver observer : observers) {
            observer.update(eventDetails);
        }
    }
}