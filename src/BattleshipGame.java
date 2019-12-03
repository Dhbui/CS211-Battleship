public class BattleshipGame {
    private Board player1;
    private Board player2;
    private boolean player1Turn;
    private int turnCount;
    private boolean isGameOver;
    private String type;
    private boolean cpuPlaying;

    public BattleshipGame(String type) {
        player1 = new Board();
        player2 = new Board();
        player1Turn = Math.random() >= 0.5;
        turnCount = 0;
        isGameOver = false;
        this.type = type;
        cpuPlaying = type.equals("Single");
    }

    public void runGame() {
        while(!isGameOver) {
            if(player1Turn) {
                promptMove();

            }
        }
    }

    public void printBoardIndexing() {
        System.out.println("\tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ");
        for(int i = 1; i <= 10; i++) {
            String tabs = i + "";
            for(int j = 0; j < 10; j++) {
                tabs += "\t";
            }
            System.out.println(tabs);
        }
    }

    public void promptMove() {

    }
}
