package g027.jeopardyproject.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import g027.jeopardyproject.controller.GameController;
import g027.jeopardyproject.models.Category;
import g027.jeopardyproject.models.Game;
import g027.jeopardyproject.models.Player;
import g027.jeopardyproject.models.Question;

public class MainFrame extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NewJFrame.class.getName());

    public MainFrame(Game model, GameController controller) {
        setTitle("Jeopardy - Iteration 9");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(
                model.getCategories().size(),
                model.getCategories().get(0).getQuestions().size()
        ));

        for (Category c : model.getCategories()) {
            for (Question q : c.getQuestions()) {

                JButton btn = new JButton(c.getName() + " - $" + q.getValue());
                btn.setFont(new Font("Arial", Font.BOLD, 14));

                btn.addActionListener(e -> {
                    askQuestion(q, btn, model, controller);
                });

                board.add(btn);
            }
        }

        add(board);
        setVisible(true);
    }

    private void askQuestion(Question q, JButton btn, Game model, GameController controller) {

        Player p = model.getCurrentPlayer();

        String ans = JOptionPane.showInputDialog(
                this,
                p.getName() + ", your question:\n\n" + q.getquestion(),
                "Answer Question",
                JOptionPane.QUESTION_MESSAGE
        );

        if (ans == null) ans = "";

        boolean correct = controller.submitAnswer(p, q, ans);

        btn.setEnabled(false);

        JOptionPane.showMessageDialog(
                this,
                (correct ? "Correct!" : "Wrong!") +
                        "\n\n" +
                        p.getName() + " now has $" + p.getScore()
        );
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new NewJFrame().setVisible(true));
    }

    
}
