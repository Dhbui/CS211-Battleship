import javax.swing.*;
import java.awt.event.*;

/**
 * ActionListener for all buttons that require SinglePlayerGamePanel to be brought up. Clears the JFrame and sets a new
 * SinglePlayerGamePanel.
 * @author Dylan Bui
 */
public class SinglePlayerListener implements ActionListener {
    JFrame myFrame;
    public SinglePlayerListener(JFrame f) {
        myFrame = f;
    }

    public void actionPerformed(ActionEvent e) {
        myFrame.getContentPane().removeAll();
        myFrame.add(new SinglePlayerGamePanel(myFrame));
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
    }
}
