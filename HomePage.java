import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class HomePage implements ActionListener {
    static String prn = null;
    JFrame frame;
    JLabel welcom = new JLabel("Welcome to the Home Page");
    JMenuBar menuBar = new JMenuBar();

    HomePage() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
    }

    public void createWindow() {
        frame = new JFrame("Home Page");
        frame.setBounds(50, 10, 1300, 1000);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
    }

    public void setLocationAndSize() {
        menuBar.setBounds(2, 2, 1300, 30);

        // Use the modularized menu classes
        FileMenu fileMenu = new FileMenu();
        HelpMenu helpMenu = new HelpMenu();
        FeedbackMenu feedbackMenu = new FeedbackMenu();

        menuBar.add(fileMenu.createFileMenu(this));
        menuBar.add(helpMenu.createHelpMenu(this));
        menuBar.add(feedbackMenu.createFeedbackMenu(this));

        welcom.setBounds(250, 80, 800, 50);
        welcom.setFont(new Font("Arial", Font.BOLD, 30));
    }

    public void addComponentsToFrame() {
        frame.add(menuBar);
        frame.add(welcom);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Register User")) {
            frame.dispose();
            new RegisterUserPage();
        } else if (e.getActionCommand().equals("Login User")) {
            frame.dispose();
            new LoginPage();
        } else if (e.getActionCommand().equals("Take Quiz")) {
            frame.dispose();
            new QuizPage(prn);
        } else if (e.getActionCommand().equals("Logout User")) {
            frame.dispose();
            new LoginPage();
        } else if (e.getActionCommand().equals("Change Password")) {
            frame.dispose();
            new ChangePasswordPage(prn);
        } else if (e.getActionCommand().equals("Feedback")) {
            frame.dispose();
            new FeedbackPage(prn);
        }
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
