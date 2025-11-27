package g027.jeopardyproject.view;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import g027.jeopardyproject.controller.GameController;
import g027.jeopardyproject.models.Player;
import g027.jeopardyproject.models.Question;

public class QuestionDialog extends JDialog {
    public QuestionDialog(Frame owner, Question question, GameController controller, Player currentPlayer) {
        super(owner, "Question - " + question.getCategory(), true);
        setLayout(new BorderLayout());
        JTextArea qArea = new JTextArea(question.getquestion());
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);
        qArea.setEditable(false);
        add(new JScrollPane(qArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JTextField answerField = new JTextField(30);
        bottom.add(new JLabel("Your answer:"));
        bottom.add(answerField);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String ans = answerField.getText();
            boolean correct = controller.submitAnswer(currentPlayer, question, ans);
            JOptionPane.showMessageDialog(this, correct ? "Correct!" : "Incorrect! Answer: " + question.getcorrectAnswer());
            dispose();
        });
        bottom.add(submit);
        add(bottom, BorderLayout.SOUTH);
        setSize(500,300);
    }
}
