package g027.jeopardyproject.view;

import g027.jeopardyproject.controller.GameController;
import g027.jeopardyproject.models.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Full GUI: Load, Start, Board, Player sidebar, Score updates.
 */
public class MainFrame extends JFrame implements GameObserver {

    private final Game model;
    private final GameController controller;

    private final JPanel boardPanel = new JPanel();
    private final JPanel playerPanel = new JPanel();
    private final JLabel currentPlayerLabel = new JLabel("Current: -");
    private final Map<String, JLabel> playerScoreLabels = new HashMap<>();
    private final Map<Question, JButton> questionButtons = new HashMap<>();

    public MainFrame(Game model, GameController controller) {
        super("Jeopardy");
        this.model = model;
        this.controller = controller;
        //model.notifyObservers(this); // register as observer
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100,700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
        initTopPanel();
        initBoardPanel();
        initPlayerPanel();
    }

    private void initTopPanel(){
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,10));
        JButton load = new JButton("Load Questions");
        JButton start = new JButton("Start Game");
        top.add(load);
        top.add(start);
        top.add(currentPlayerLabel);
        add(top, BorderLayout.NORTH);

        load.addActionListener(e -> {
            JFileChooser fc = new JFileChooser(new File("."));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.loadQuestions(fc.getSelectedFile().getAbsolutePath());
                    rebuildBoard();
                    JOptionPane.showMessageDialog(this, "Questions loaded.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Load error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        start.addActionListener(e -> {
            String n = JOptionPane.showInputDialog(this, "Number of players (1-4):", "2");
            if (n == null) return;
            int count;
            try { count = Integer.parseInt(n); } catch (NumberFormatException ex){ JOptionPane.showMessageDialog(this, "Invalid"); return; }
            count = Math.max(1, Math.min(4, count));
            model.getCurrentPlayer(); // Reset players if any - note: model.getPlayers is unmodifiable; alternative: create new model in real app
            // For simplicity here, we directly add to model via reflection isn't allowed; instead we'll recreate model/controller in real app.
            // Simpler: prompt for names and add players directly using the model methods
            for (int i=1;i<=count;i++){
                String name = JOptionPane.showInputDialog(this, "Player " + i + " name:", "Player"+i);
                if (name == null || name.isBlank()) name = "Player"+i;
                model.getCurrentPlayer().add(new Player(name));

            }
            rebuildPlayerPanel();
            updateCurrentPlayerLabel();
        });
    }

    private void initBoardPanel(){
        boardPanel.setLayout(new GridLayout(1,1));
        boardPanel.setBorder(BorderFactory.createTitledBorder("Board"));
        add(new JScrollPane(boardPanel), BorderLayout.CENTER);
    }

    private void rebuildBoard(){
        boardPanel.removeAll();
        questionButtons.clear();
        java.util.List<Category> cats = model.getCategories();
        if (cats.isEmpty()){
            boardPanel.setLayout(new BorderLayout());
            boardPanel.add(new JLabel("No questions loaded. Use Load Questions."), BorderLayout.CENTER);
            revalidate(); repaint();
            return;
        }
        int cols = cats.size();
        int rows = cats.get(0).getQuestions().size()+1; // header + rows
        JPanel grid = new JPanel(new GridLayout(rows, cols, 5,5));

        // headers
        for (Category c : cats) {
            JLabel header = new JLabel(c.getName(), SwingConstants.CENTER);
            header.setOpaque(true);
            header.setBackground(new Color(10,90,160));
            header.setForeground(Color.WHITE);
            header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
            grid.add(header);
        }

        // rows
        int qcount = cats.get(0).getQuestions().size();
        for (int r=0;r<qcount;r++){
            for (int c=0;c<cats.size();c++){
                Question q = cats.get(c).getQuestions().get(r);
                JButton btn = new JButton("$" + q.getValue());
                btn.setFont(btn.getFont().deriveFont(Font.BOLD, 16f));
                btn.setBackground(new Color(30,144,255));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                if (q.isAnswered()) { btn.setEnabled(false); btn.setBackground(Color.GRAY); }
                btn.addActionListener(e -> {
                    // open question dialog
                    QuestionDialog dlg = new QuestionDialog(this, q);
                    dlg.setVisible(true);
                    char sel = dlg.getSelectedOption();
                    if (sel == 0) return; // canceled
                    Player curr = model.getCurrentPlayer();
                    boolean correct = controller.submitAnswer(curr, q, String.valueOf(sel));
                    if (correct) {
                        JOptionPane.showMessageDialog(this, "Correct! +" + q.getValue());
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect! Answer: " + q.getCorrectAnswer()() + "  -" + q.getValue());
                    }
                    btn.setEnabled(false);
                    btn.setBackground(Color.GRAY);
                    rebuildPlayerPanel();
                    updateCurrentPlayerLabel();
                });
                questionButtons.put(q, btn);
                grid.add(btn);
            }
        }

        boardPanel.setLayout(new BorderLayout());
        boardPanel.add(grid, BorderLayout.CENTER);
        revalidate(); repaint();
    }

    private void initPlayerPanel(){
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Players"));
        add(playerPanel, BorderLayout.EAST);
    }

    private void rebuildPlayerPanel(){
        playerPanel.removeAll();
        playerScoreLabels.clear();
        for (Player p : model.getPlayers()){
            JLabel l = new JLabel(p.getName() + " : " + p.getScore());
            l.setFont(l.getFont().deriveFont(14f));
            playerPanel.add(l);
            playerScoreLabels.put(p.getId(), l);
        }
        revalidate(); repaint();
    }

    private void updateCurrentPlayerLabel(){
        Player curr = model.getCurrentPlayer();
        if (curr != null) currentPlayerLabel.setText("Current: " + curr.getName());
        else currentPlayerLabel.setText("Current: -");
    }

    // ScoreObserver methods
    @Override
    public void scoreUpdated(Player player) {
        JLabel lbl = playerScoreLabels.get(player.getId());
        if (lbl != null) lbl.setText(player.getName() + " : " + player.getScore());
    }

    @Override
    public void playerTurnChanged(Player current) {
        updateCurrentPlayerLabel();
    }

    @Override
    public void questionAnswered(Question q) {
        JButton b = questionButtons.get(q);
        if (b != null) { b.setEnabled(false); b.setBackground(Color.GRAY); }
    }
}
