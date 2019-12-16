import javax.swing.*;
import java.util.Scanner;

/**
 * A Driver class that starts the program. It opens the first window for the program. Starts the GUI.
 * @author Dylan Bui
 */
public class BattleshipDriver {
    public static void main(String[] args) {

        JFrame frame = new JFrame("CS211 - Battleship!");
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MainMenuPanel(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        BattleshipGame game;
//        boolean playAgain = true;
//        String play = "";
//        while(playAgain) {
//            System.out.println("Welcome to Battleship!");
//            System.out.println("How many people are playing? Zero, One, or Two?");
//            Scanner input = new Scanner(System.in);
//            String players = input.nextLine();
//            if (players.toLowerCase().equals("zero") || players.toLowerCase().equals("0")) {
//                game = new BattleshipGame("");
//                System.out.println("What difficulty do you want the first computer to be? (0, 1, 2, or 3)");
//                game.setPlayer1Difficulty(Integer.parseInt(input.nextLine()));
//                System.out.println("What difficulty do you want the second computer to be? (0, 1, 2, or 3)");
//                game.setPlayer2Difficulty(Integer.parseInt(input.nextLine()));
//                System.out.println("How quickly would you like the computer to make a move (in milliseconds)?");
//                game.setWaitTime(Integer.parseInt(input.nextLine()));
//                game.testTwoCPUS();
//            }
//            else if (players.toLowerCase().equals("one") || players.toLowerCase().equals("1")) {
//                game = new BattleshipGame("Single");
//                System.out.println("What difficulty do you want the computer to be? (0, 1, 2, or 3)");
//                game.setPlayer2Difficulty(Integer.parseInt(input.nextLine()));
//                game.setupGame();
//                game.runGame();
//            }
//            else if(players.toLowerCase().equals("two") || players.toLowerCase().equals("2")) {
//                game = new BattleshipGame("");
//                game.setupGame();
//                game.runGame();
//            }
//            else if(!(players.toLowerCase().equals("q") || players.toLowerCase().equals("quit"))) {
//                System.out.println("Not a valid input. Try again.");
//            }
//            else {
//                playAgain = false;
//            }
//            System.out.println("Do you want to play again?");
//            play = input.nextLine().toLowerCase();
//            if(play.equals("y") || play.equals("yes"))
//                playAgain = true;
//            else
//                playAgain = false;
//        }
    }
}
