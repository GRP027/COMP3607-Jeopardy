package g027.jeopardyproject;

import g027.jeopardyproject.controller.GameController;
import g027.jeopardyproject.models.Game;
import g027.jeopardyproject.view.MainFrame;


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

    public static void main(String[] args) throws Exception {
        // Initialize the game model
        Game game = new Game();

        // Initialize the game controller
        GameController controller = new GameController(game);

                // Initialize the main application frame (view)
        MainFrame mainFrame = new MainFrame(game,controller);
        mainFrame.setVisible(true);
    }   
        
}