import javax.swing.*;
import java.awt.event.*;

/**
 * ActionListener for the Multiplayer Button (not currently in use). Sets the screen to multiplayer game.
 * @author Dylan Bui
 */
public class MultiPlayerListener implements ActionListener {
    JFrame myFrame;
    public MultiPlayerListener(JFrame f) {
        myFrame = f;
    }

    public void actionPerformed(ActionEvent e) {
        myFrame.getContentPane().removeAll();
        myFrame.add(new MultiPlayerGamePanel(myFrame));
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
    }
}