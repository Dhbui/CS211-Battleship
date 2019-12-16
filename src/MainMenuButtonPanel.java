import javax.swing.*;
import java.awt.*;

/**
 * The JPanel for the Main Menu Buttons. Will contain more than Singleplayer button when GUI functionality is working
 * for those aspects of the project.
 * @author Dylan Bui
 */
public class MainMenuButtonPanel extends JPanel {
    private JButton single;
    private JButton multi;
    private JButton zero;
    private JFrame myFrame;

    public MainMenuButtonPanel(JFrame f) {
        myFrame = f;

        setLayout(new GridLayout(1, 3));

        zero = new JButton("Zero Players");
        zero.addActionListener(new ZeroPlayerListener(myFrame));

        single = new JButton("Singleplayer");
        single.addActionListener(new SinglePlayerListener(myFrame));

        multi = new JButton("Multiplayer");
        multi.addActionListener(new MultiPlayerListener(myFrame));

        //add(zero);
        add(single);
        //add(multi);
    }
}
