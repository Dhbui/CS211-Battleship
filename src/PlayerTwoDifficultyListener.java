import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ChangeListener for the Difficulty setting in the Options menu of the SinglePlayer game. Sets the difficulty of the
 * player2 cpu in myGame. Not currently in use.
 * @author Dylan Bui
 */
public class PlayerTwoDifficultyListener implements ChangeListener {
    private BattleshipGame myGame;
    public PlayerTwoDifficultyListener(BattleshipGame g) {
        myGame = g;
    }
    public void stateChanged(ChangeEvent e) {
        myGame.setPlayer2Difficulty((int)((JSlider) e.getSource()).getValue());
        System.out.println(myGame.getPlayer2Difficulty());
    }
}
