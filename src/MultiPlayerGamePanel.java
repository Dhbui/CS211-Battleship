import javax.swing.*;

/**
 * The beginnings of a structure for the multiplayer GUI functionality.
 * @author Dylan Bui
 */
public class MultiPlayerGamePanel extends JPanel {
    private JFrame myFrame;
    private JButton[][] player1Board;
    private JButton[][] player2Board;

    public MultiPlayerGamePanel(JFrame f) {
        myFrame = f;
    }
}
