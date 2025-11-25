package g027.jeopardyproject;

import g027.jeopardyproject.model.Player;
import g027.jeopardyproject.model.Question;

/*
GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026
--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------

This is the main entry point for the Jeopardy Project application.
*/
public class Main {

    public static void main(String[] args) {

        Player p = new Player("Alice");
        Question q = new Question("What is 2+2?" , 100, "4", "Math");

        System.out.println("Player: " + p.getName() + " Score: " + p.getScore());
        System.out.println("Sample Question: " + q.getText() +
                " (" + q.getValue() + " points)");
    }
}