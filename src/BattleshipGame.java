import java.util.Scanner;

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
                int move = promptMove();
                player2.fireAtSpace(move);
                checkGameOver();
                player1Turn = false;
            }
            else {
                if(cpuPlaying) {
                    int move = player1.getBestMove();
                    player1.fireAtSpace(move);
                    player1Turn = true;
                }
                else {
                    int move = promptMove();
                    player1.fireAtSpace(move);
                    checkGameOver();
                    player1Turn = true;
                }
            }
        }
    }

    public void setupGame() {

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

    public int promptMove() {
        System.out.println("What's your move?");
        Scanner input = new Scanner(System.in);
        String move = input.nextLine();
        return Integer.parseInt(move);
    }

    public void checkGameOver() {
        isGameOver =  player1.checkGameOver() || player2.checkGameOver();
    }
}
