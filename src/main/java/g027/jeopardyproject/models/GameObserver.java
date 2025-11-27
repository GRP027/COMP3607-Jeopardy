package g027.jeopardyproject.models;


import java.util.Map;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This interface defines the observer for game events, allowing implementing classes to receive updates about game state changes.
*/
public interface GameObserver {
    
    void update(Map<String, String> eventDetails);

    void scoreUpdated(Player player);

    void playerTurnChanged(Player current);

    void questionAnswered(Question q);
}