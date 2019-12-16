/**
 * A Handler class to handle the creation and running of the BattleshipGame Object. Terminal ONLY.
 * @author Dylan Bui
 */
public class SinglePlayerHandler {
    public static void main(String[] args) {
        BattleshipGame singlePlayer = new BattleshipGame("Single");
        singlePlayer.setupGame();
        singlePlayer.runGame();
    }
}