package g027.jeopardyproject;

import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import g027.jeopardyproject.controller.GameController;
import g027.jeopardyproject.logging.ProcessMiningLogger;
import g027.jeopardyproject.models.Game;
import g027.jeopardyproject.models.Question;
import g027.jeopardyproject.view.MainFrame;
import g027.jeopardyproject.models.Player;
import java.util.List;



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
    //private <List>Players players;



    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            try {
                Game model = new Game(List.of(new Player("Alice"), new Player("Bob")), List.of());  
                //ProcessMiningLogger logger = new ProcessMiningLogger();
                GameController controller = new GameController(model);
                MainFrame frame = new MainFrame(model, controller);
                // attempt to auto-load sample file if present (non-fatal)
                try {
                    controller.loadQuestions("src\\main\\resources\\sample_game_CSV.csv");
                    //frame.rebuildBoard();
                } catch (Exception ignored) {}
                frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Startup error: " + ex.getMessage());
            }
        });
    }
    }   
        
