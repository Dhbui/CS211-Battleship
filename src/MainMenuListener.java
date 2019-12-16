import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener for Main Menu Buttons, takes the user back to the main menu.
 * @author Dylan Bui
 */
public class MainMenuListener implements ActionListener {
    private JFrame myFrame;
    public MainMenuListener(JFrame f) {
        myFrame = f;
    }
    public void actionPerformed(ActionEvent e) {
        myFrame.getContentPane().removeAll();
        myFrame.add(new MainMenuPanel(myFrame));
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
    }
}
