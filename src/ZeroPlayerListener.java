import javax.swing.*;
import java.awt.event.*;

/**
 * ActionListener for the ZeroPlayer Button (not currently in use). Sets the screen to Zero player game.
 * @author Dylan Bui
 */
public class ZeroPlayerListener implements ActionListener {
    JFrame myFrame;
    public ZeroPlayerListener(JFrame f) {
        myFrame = f;
    }
    public void actionPerformed(ActionEvent e) {
        myFrame.getContentPane().removeAll();
        myFrame.add(new ZeroPlayerGamePanel(myFrame));
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
    }
}
