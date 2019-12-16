import javax.swing.*;
import java.awt.*;

/**
 * The JPanel for options that the user can change in single player mode. Namely, it allows the user to change the
 * difficulty of the CPU. Not currently in use.
 * @author Dylan Bui
 */
public class SinglePlayerOptionsPanel extends JPanel {
    private JFrame myFrame;
    private JSlider CPUDifficulty;
    private JLabel cpudiff;
    private JButton backToGame;

    public SinglePlayerOptionsPanel(JFrame f, BattleshipGame game) {
        myFrame = f;

        setLayout(new GridLayout(3, 1));

        backToGame = new JButton("Back to Game");
        backToGame.addActionListener(new SinglePlayerListener(myFrame));

        cpudiff = new JLabel("CPU Difficulty");
        cpudiff.setHorizontalAlignment(JLabel.CENTER);

        CPUDifficulty = new JSlider(0, 3);
        CPUDifficulty.setPaintLabels(true);
        CPUDifficulty.setPaintTicks(true);
        CPUDifficulty.setMajorTickSpacing(1);
        CPUDifficulty.setValue(game.getPlayer2Difficulty());
        CPUDifficulty.addChangeListener(new PlayerTwoDifficultyListener(game));

        add(backToGame);
        add(cpudiff);
        add(CPUDifficulty);

        System.out.println(game.getPlayer2Difficulty());

    }
}
