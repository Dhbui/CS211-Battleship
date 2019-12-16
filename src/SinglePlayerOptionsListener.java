import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener for buttons that should bring the player to the SinglePlayerOptionsPanel JPanel.
 * @author Dylan Bui
 */
public class SinglePlayerOptionsListener implements ActionListener {
    private JFrame myFrame;
    private BattleshipGame myGame;
    public SinglePlayerOptionsListener(JFrame f, BattleshipGame g) {
        myFrame = f;
        myGame = g;
    }

    public void actionPerformed(ActionEvent e) {
        myFrame.getContentPane().removeAll();
        myFrame.add(new SinglePlayerOptionsPanel(myFrame, myGame));
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
    }
}
