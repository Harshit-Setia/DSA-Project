import javax.swing.*;
import java.awt.event.ActionListener;

public class FeedbackMenu {
    public JMenu createFeedbackMenu(ActionListener listener) {
        JMenu feedbackMenu = new JMenu("Feedback");

        JMenuItem feedback = new JMenuItem("Feedback");
        feedback.addActionListener(listener);

        feedbackMenu.add(feedback);

        return feedbackMenu;
    }
}