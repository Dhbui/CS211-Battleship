import javax.swing.*;
import java.awt.*;

/**
 * JPanel for the Main Menu. Has a MainMenuButtonPanel as well as a welcome message.
 * @author Dylan Bui
 */
public class MainMenuPanel extends JPanel {
    private MainMenuButtonPanel buttons;
    private JLabel title;
    private JFrame myFrame;

    public MainMenuPanel(JFrame f) {
        myFrame = f;

        setLayout(new BorderLayout());

        title = new JLabel("Welcome to Battleship!");
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        buttons = new MainMenuButtonPanel(f);
        add(buttons, BorderLayout.SOUTH);
    }
}
