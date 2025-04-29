import javax.swing.*;
import java.awt.event.ActionListener;

public class HelpMenu {
    public JMenu createHelpMenu(ActionListener listener) {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem changePassword = new JMenuItem("Change Password");
        changePassword.addActionListener(listener);

        helpMenu.add(changePassword);

        return helpMenu;
    }
}