package models;


import java.util.Map;

public interface GameObserver {
    
    void update(Map<String, String> eventDetails);
}