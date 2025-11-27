package g027.jeopardyproject.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import g027.jeopardyproject.models.Question;

/**
 * Modal dialog that displays a question and 4 options (A-D).
 * Returns the selected character via getSelectedOption() or 0 if cancelled.
 */
public class QuestionDialog extends JDialog {
    private char selectedOption = 0;

    public QuestionDialog(Frame owner, Question q) {
        super(owner, "Question - " + q.getCategory(), true);
        setLayout(new BorderLayout());
        JTextArea questionArea = new JTextArea(q.getQuestionText());
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);
        questionArea.setBackground(getBackground());
        questionArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        questionArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(questionArea, BorderLayout.NORTH);

        JPanel options = new JPanel(new GridLayout(2,2,10,10));
        JRadioButton a = new JRadioButton("A: " + q.getOptionA());
        JRadioButton b = new JRadioButton("B: " + q.getOptionB());
        JRadioButton c = new JRadioButton("C: " + q.getOptionC());
        JRadioButton d = new JRadioButton("D: " + q.getOptionD());
        ButtonGroup bg = new ButtonGroup();
        bg.add(a); bg.add(b); bg.add(c); bg.add(d);
        options.add(a); options.add(b); options.add(c); options.add(d);
        options.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(options, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        bottom.add(cancel);
        bottom.add(submit);
        add(bottom, BorderLayout.SOUTH);

        submit.addActionListener(e -> {
            if (a.isSelected()) selectedOption = 'A';
            else if (b.isSelected()) selectedOption = 'B';
            else if (c.isSelected()) selectedOption = 'C';
            else if (d.isSelected()) selectedOption = 'D';
            else {
                JOptionPane.showMessageDialog(this, "Please select an option (A–D).");
                return;
            }
            dispose();
        });

        cancel.addActionListener(e -> {
            selectedOption = 0;
            dispose();
        });

        KeyAdapter ka = new KeyAdapter(){
            @Override public void keyPressed(KeyEvent e){
                int k = e.getKeyCode();
                if (k == KeyEvent.VK_A) { a.setSelected(true); }
                if (k == KeyEvent.VK_B) { b.setSelected(true); }
                if (k == KeyEvent.VK_C) { c.setSelected(true); }
                if (k == KeyEvent.VK_D) { d.setSelected(true); }
                if (k == KeyEvent.VK_ENTER) submit.doClick();
                if (k == KeyEvent.VK_ESCAPE) cancel.doClick();
            }
        };
        addKeyListener(ka);
        options.addKeyListener(ka);
        questionArea.addKeyListener(ka);
        // ensure keys go to dialog
        setFocusable(true);

        setSize(600, 300);
        setLocationRelativeTo(owner);
    }

    public char getSelectedOption() { return selectedOption; }
}
