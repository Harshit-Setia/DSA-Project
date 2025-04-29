import javax.swing.*;
import java.awt.event.ActionListener;

public class FileMenu {
    public JMenu createFileMenu(ActionListener listener) {
        JMenu fileMenu = new JMenu("File");

        JMenuItem registerUser = new JMenuItem("Register User");
        JMenuItem loginUser = new JMenuItem("Login User");
        JMenuItem takeQuiz = new JMenuItem("Take Quiz");
        JMenuItem logoutUser = new JMenuItem("Logout User");

        registerUser.addActionListener(listener);
        loginUser.addActionListener(listener);
        takeQuiz.addActionListener(listener);
        logoutUser.addActionListener(listener);

        fileMenu.add(registerUser);
        fileMenu.add(loginUser);
        fileMenu.add(takeQuiz);
        fileMenu.add(logoutUser);

        return fileMenu;
    }
}