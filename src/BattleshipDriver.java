import java.util.Scanner;

public class BattleshipDriver {
    public static void main(String[] args) {
        BattleshipGame game;
        boolean playAgain = true;
        String play = "";
        while(playAgain) {
            System.out.println("Welcome to Battleship!");
            System.out.println("How many people are playing? Zero, One, or Two?");
            Scanner input = new Scanner(System.in);
            String players = input.nextLine();
            if (players.toLowerCase().equals("zero") || players.toLowerCase().equals("0")) {
                game = new BattleshipGame("");
                game.testTwoCPUS();
            }
            else if (players.toLowerCase().equals("one") || players.toLowerCase().equals("1")) {
                game = new BattleshipGame("Single");
                game.setupGame();
                game.runGame();
            }
            else if(players.toLowerCase().equals("two") || players.toLowerCase().equals("2")) {
                game = new BattleshipGame("");
                game.setupGame();
                game.runGame();
            }
            else if(!(players.toLowerCase().equals("q") || players.toLowerCase().equals("quit"))) {
                System.out.println("Not a valid input. Try again.");
            }
            else {
                playAgain = false;
            }
            System.out.println("Do you want to play again?");
            play = input.nextLine().toLowerCase();
            if(play.equals("y") || play.equals("yes"))
                playAgain = true;
            else
                playAgain = false;
        }
    }
}
