public class SinglePlayerHandler {
    public static void main(String[] args) {
        BattleshipGame singlePlayer = new BattleshipGame("Single");
        singlePlayer.setupGame();
        singlePlayer.runGame();
    }
}
