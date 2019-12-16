import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ChangeListener for the Difficulty setting in the Options menu of the SinglePlayer game. Sets the difficulty of the
 * player1 cpu in myGame. Not currently in use.
 * @author Dylan Bui
 */
public class PlayerOneDifficultyListener implements ChangeListener {
    private BattleshipGame myGame;
    public PlayerOneDifficultyListener(BattleshipGame g) {
        myGame = g;
    }
    public void stateChanged(ChangeEvent e) {
        myGame.setPlayer1Difficulty(3 - (int)((JSlider) e.getSource()).getValue());
    }
}
