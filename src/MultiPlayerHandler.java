/**
 * A Handler class to handle the creation and running of the BattleshipGame Object for 2 players. Terminal ONLY.
 * @author Dylan Bui
 */
public class MultiPlayerHandler {
    public static void main(String[] args) {
        BattleshipGame game = new BattleshipGame("");
        game.setupGame();
        game.runGame();
    }
}
