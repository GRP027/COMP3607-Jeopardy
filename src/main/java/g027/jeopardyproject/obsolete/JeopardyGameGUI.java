package g027.jeopardyproject.obsolete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class JeopardyGameGUI extends JFrame {

    // Panels
    private JPanel mainPanel;
    private JPanel categoryPanel;
    private JPanel boardPanel;
    private JPanel playerPanel;

    // Example placeholders for dynamic labels/buttons
    private JLabel[] categoryLabels;
    private JButton[][] questionButtons;
    private JLabel currentPlayerLabel;
    private JLabel scoreLabel;

    // Constants for visual layout
    private static final int NUM_CATEGORIES = 5;
    private static final int NUM_QUESTIONS_PER_CATEGORY = 5;

    public JeopardyGameGUI() {
        setTitle("Jeopardy Game (Frontend Mockup)");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // ---------------------------
        // PLAYER + SCORE PANEL (Top)
        // ---------------------------
        playerPanel = new JPanel(new GridLayout(1, 2));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        currentPlayerLabel = new JLabel("Current Player: <Player Name>", SwingConstants.LEFT);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        scoreLabel = new JLabel("Score: 0", SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));

        playerPanel.add(currentPlayerLabel);
        playerPanel.add(scoreLabel);

        mainPanel.add(playerPanel, BorderLayout.NORTH);

        // ---------------------------
        // CATEGORY HEADER PANEL
        // ---------------------------
        categoryPanel = new JPanel(new GridLayout(1, NUM_CATEGORIES));
        categoryLabels = new JLabel[NUM_CATEGORIES];

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            JLabel label = new JLabel("Category " + (i + 1), SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setOpaque(true);
            label.setBackground(new Color(0, 0, 128));
            label.setForeground(Color.WHITE);
            categoryLabels[i] = label;
            categoryPanel.add(label);

            // BACKEND: replace placeholder text with real category names.
            // Example:
            // label.setText(model.getCategories().get(i).getName());
        }

        mainPanel.add(categoryPanel, BorderLayout.CENTER);

        // ---------------------------
        // QUESTION GRID PANEL
        // ---------------------------
        boardPanel = new JPanel(new GridLayout(NUM_QUESTIONS_PER_CATEGORY, NUM_CATEGORIES));
        questionButtons = new JButton[NUM_QUESTIONS_PER_CATEGORY][NUM_CATEGORIES];

        for (int row = 0; row < NUM_QUESTIONS_PER_CATEGORY; row++) {
            for (int col = 0; col < NUM_CATEGORIES; col++) {
                JButton btn = new JButton("$" + ((row + 1) * 100));
                btn.setFont(new Font("Arial", Font.BOLD, 18));
                btn.setBackground(new Color(0, 51, 204));
                btn.setForeground(Color.WHITE);

                questionButtons[row][col] = btn;
                boardPanel.add(btn);

                /* BACKEND HOOK:
                 * Each button should open a Question Dialog.
                 * Example integration code once backend is ready:
                 *
                 * btn.addActionListener(e -> {
                 *      Question q = model.getCategories().get(col).getQuestions().get(row);
                 *      showQuestionDialog(q);
                 *      btn.setEnabled(false);
                 * });
                 */
            }
        }

        mainPanel.add(boardPanel, BorderLayout.SOUTH);
    }

    // GUI-ONLY FUNCTION FOR SHOWING POPUPS (No backend)
    private void showMockQuestionDialog() {
        JOptionPane.showMessageDialog(this,
                "This is where the question dialog will appear.\n"
                + "(Backend logic will populate text & options.)",
                "Question Preview",
                JOptionPane.INFORMATION_MESSAGE);

        // BACKEND HOOK:
        // Replace this with real multiple-choice question dialog.
    }

    // MAIN METHOD - RUNS GUI ONLY
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JeopardyGameGUI gui = new JeopardyGameGUI();
            gui.setVisible(true);
        });
    }
}
