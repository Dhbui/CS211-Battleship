import java.util.ArrayList;
import java.util.Scanner;

/**
 * An object that can manage and run the game.
 * @author Dylan Bui
 */
public class BattleshipGame {
    /**
     * Board Object for the first player.
     */
    private Board player1;
    /**
     * Board object for the second player, is the Computer when only singleplayer.
     */
    private Board player2;
    /**
     * Boolean that tracks who's turn it is.
     */
    private boolean player1Turn;
    /**
     * Int that tracks the total number of moves made in the game.
     */
    private int turnCount;
    /**
     * Boolean that tracks whether or not the game is over.
     */
    private boolean isGameOver;
    /**
     * String that determines whether or not the game is Singleplayer, Multiplayer, or 2 Computers.
     */
    private String type;
    /**
     * Boolean that tracks whether or not the game is Singleplayer
     */
    private boolean cpuPlaying;
    /**
     * Boolean that tracks who the winner of the game is.
     */
    private boolean player1Win;
    /**
     * ArrayList that keeps track of every single move made in the game.
     */
    private ArrayList<Integer> movesMade;

    /**
     * Only constructor. Initializes fields and takes the type of game as input.
     * @param type the type of game.
     */
    public BattleshipGame(String type) {
        player1 = new Board();
        player2 = new Board();
        player1Turn = Math.random() >= 0.5;
        turnCount = 0;
        isGameOver = false;
        this.type = type;
        cpuPlaying = type.equals("Single");
        movesMade = new ArrayList<>();
    }

    /**
     * Method that runs the game, alternating between each player until the game is over.
     */
    public void runGame() {
        while(!isGameOver) {
            if(player1Turn) {
                System.out.println("Player1 Move:");
                int move = promptMove();
                while(!player2.fireAtSpace(move)) {
                    System.out.println("Player1 Move:");
                    move = promptMove();
                }
                movesMade.add(move);
                player1Turn = false;
                turnCount++;
                System.out.println("Player1's Move:");
                System.out.println(player2);
                if(player2.isLastMoveHit()) {
                    System.out.println("Hit!");
                    if (player2.isLastMoveSunk()) {
                        if(cpuPlaying) {
                            System.out.println("You've sunk the Computer's " + player2.getLastSunkShip().getType() + "!\n");
                        }
                        else {
                            System.out.println("You've sunk Player 2's " + player2.getLastSunkShip().getType() + "!\n");
                        }
                    }
                }
                else {
                    System.out.println("Miss!\n");
                }
            }
            else {
                if(cpuPlaying) {
                    int move = player1.getBestMove();
                    player1.fireAtSpace(move);
                    System.out.println("Computer's Move:");
                    player1Turn = true;
                    turnCount++;
                    System.out.println(player1);
                    if(player1.isLastMoveHit()) {
                        System.out.println("Hit!");
                        if(player1.isLastMoveSunk()) {
                            System.out.println("The Computer sunk your " + player1.getLastSunkShip().getType() + "!\n");
                        }
                        else {
                            System.out.println();
                        }
                    }
                    else {
                        System.out.println("Miss!\n");
                    }
                }
                else {
                    System.out.println("Player2 Move:");
                    int move = promptMove();
                    while(!player1.fireAtSpace(move)) {
                        System.out.println("Player2 Move:");
                        move = promptMove();
                    }
                    movesMade.add(move);
                    System.out.println("Player2's Move:");
                    player1Turn = true;
                    turnCount++;
                    System.out.println(player1);
                    if(player1.isLastMoveHit()) {
                        System.out.println("Hit!");
                        if(player1.isLastMoveSunk()) {
                            System.out.println("You've sunk Player 1's " + player1.getLastSunkShip().getType() + "!\n");
                        }
                        else {
                            System.out.println();
                        }
                    }
                    else {
                        System.out.println("Miss!\n");
                    }

                }


            }
            checkGameOver();
        }
        System.out.println("Game Over!");
        if (player1.getFloatingShips().size() == 0) {
            System.out.println("Player2 Wins!");
            player1Win = false;
        }
        else {
            System.out.println("Player1 Wins!");
            player1Win = true;
        }
        System.out.println("Game took " + (turnCount + 1) + "turns");
    }

    /**
     * Sets up the game from scratch. Takes inputs for ship placement.
     */
    public void setupGame() {
        String[] shipNames = {"Patrol Boat", "Submarine", "Destroyer", "Battleship", "Carrier"};
        for(int i = 0; i < 5; i++) {
            String name = shipNames[i];
            int length = 0;
            Ship s;
            switch(i) {
                case 0:
                    length = 2;
                    break;
                case 1:
                case 2:
                    length = 3;
                    break;
                case 3:
                    length = 4;
                    break;
                case 4:
                    length = 5;
                    break;
            }
            System.out.println(player1.toStringWithShipSpaces());
            s = promptForShip(name, length);
            while(!player1.addShip(s)) {
                System.out.println(player1.toStringWithShipSpaces());
                s = promptForShip(name, length);
            }
            System.out.println(player1.toStringWithShipSpaces());
        }
        if(cpuPlaying) {
            player2.placeShipsRandomly();
        }
        else {
            for(int i = 0; i < 5; i++) {
                String name = shipNames[i];
                int length = 0;
                Ship s;
                switch(i) {
                    case 0:
                        length = 2;
                        break;
                    case 1:
                    case 2:
                        length = 3;
                        break;
                    case 3:
                        length = 4;
                        break;
                    case 4:
                        length = 5;
                        break;
                }
                System.out.println(player2.toStringWithShipSpaces());
                s = promptForShip(name, length);
                while(!player2.addShip(s)) {
                    System.out.println(player2.toStringWithShipSpaces());
                    s = promptForShip(name, length);
                }
                System.out.println(player2.toStringWithShipSpaces());
            }
        }
    }

    /**
     * Prints out an empty board.
     */
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

    /**
     * Uses the terminal to prompt the user for a move.
     * @return the index of the desired move.
     */
    public int promptMove() {
        System.out.println("What's your move?");
        Scanner input = new Scanner(System.in);
        String move = input.nextLine();
        return Integer.parseInt(move);
    }

    /**
     * Prompts the user for a space to put a certain ship.
     * @param name the type of the ship that is being created, such as "Destroyer"
     * @param length the length of the ship
     * @return a Ship Object created using name, length, user-selected Orientation, and spaces.
     */
    public Ship promptForShip(String name, int length) {
        System.out.println("Where would you like to put your " + name + "?");
        Scanner input = new Scanner(System.in);
        Orientation state = Orientation.HORIZONTAL;
        String index = input.nextLine();
        while(index.toLowerCase().equals("r")) {
            System.out.println("Where would you like to put your " + name + "?");
            if(state == Orientation.HORIZONTAL)
                state = Orientation.VERTICAL;
            else
                state = Orientation.HORIZONTAL;
            index = input.nextLine();
        }
        int firstIndex = Integer.parseInt(index);
        return new Ship(length, firstIndex, state, name);
    }

    /**
     * Checks whether the game is over.
     * @return true if the game is over.
     */
    public boolean checkGameOver() {
        boolean p1check = player1.checkGameOver();
//        System.out.println("P1check: " + p1check);
        boolean p2check = player2.checkGameOver();
//        System.out.println("P2check: " + p2check);
        isGameOver = p1check || p2check;
        return isGameOver;
    }

    /**
     * Resets all fields to initial values.
     */
    public void resetGame() {
        player1.reset();
        player2.reset();
        movesMade = new ArrayList<>();
        player1Turn = Math.random() >= 0.5;
        turnCount = 0;
        isGameOver = false;

    }

    /**
     * A modification of the runGame method that plays two Computers against each other.
     */
    public void testTwoCPUS() {
        player1.placeShipsRandomly();
        player2.placeShipsRandomly();
        while(!isGameOver) {
            try {
                Thread.sleep(550);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (player1Turn) {
                int move = player2.getBestMove();
                player2.fireAtSpace(move);
                movesMade.add(move);
                System.out.println("Computer 1's Move:");
                player1Turn = false;
                turnCount++;
                System.out.println(player2.toStringWithShipSpaces());
                if (player2.isLastMoveHit()) {
                    System.out.println("Hit!");
                    if (player2.isLastMoveSunk()) {
                        System.out.println("Computer 1 sunk Computer 2's " + player2.getLastSunkShip().getType() + "!\n\n");
                    }
                    else {
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Miss!\n");
                }
            }
            else {
                int move = player1.getBestMove();
                player1.fireAtSpace(move);
                movesMade.add(move);
                System.out.println("Computer 2's Move:");
                player1Turn = true;
                turnCount++;
                System.out.println(player1.toStringWithShipSpaces());
                if (player1.isLastMoveHit()) {
                    System.out.println("Hit!");
                    if (player1.isLastMoveSunk()) {
                        System.out.println("Computer 2 sunk Computer 1's " + player1.getLastSunkShip().getType() + "!\n");
                    }
                    else {
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Miss!\n");
                }
            }
            checkGameOver();
        }
        System.out.println("Game Over!");
        if (player1.getFloatingShips().size() == 0) {
            System.out.println("Computer 2 Wins!");
            player1Win = false;
        }
        else {
            System.out.println("Computer 1 Wins!");
            player1Win = true;
        }
        System.out.println("Game took " + (turnCount + 1) + " turns");
        System.out.println("Move List: \n" + movesMade);
    }

    /**
     * A modification of the testTwoCPUS method that does not print the game.
     */
    public void testTwoCPUSNoPrinting() {
        player1.placeShipsRandomly();
        player2.placeShipsRandomly();
        while(!isGameOver) {
            if (player1Turn) {
                int move = player2.getBestMove();
                player2.fireAtSpace(move);
                movesMade.add(move);
                player1Turn = false;
                turnCount++;
            }
            else {
                int move = player1.getBestMove();
                player1.fireAtSpace(move);
                movesMade.add(move);
                player1Turn = true;
                turnCount++;
            }
            checkGameOver();
        }
        if (player1.getFloatingShips().size() == 0) {
            player1Win = false;
        }
        else {
            player1Win = true;
        }
    }

    public boolean getWinner() {
        return player1Win;
    }
}