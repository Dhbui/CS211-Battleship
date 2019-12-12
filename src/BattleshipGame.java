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
     * Time to wait for CPU in Milliseconds
     */
    private int waitTime;
    /**
     * True if there is currently a game in progress.
     */
    private boolean gameInProgress;

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
        waitTime = 550;
        gameInProgress = false;
    }

    /**
     * Method that runs the game, alternating between each player until the game is over.
     */
    public void runGame() {
        while(!isGameOver) {
            if(player1Turn) {
                System.out.println("Opponent's Board:");
                System.out.println(player2);
                System.out.println("Player1 Move:");
                int move = promptMove();
                while(!player2.fireAtSpace(move)) {
                    System.out.println("Invalid move");
                    System.out.println("Opponent's Board:");
                    System.out.println(player2);
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
                    System.out.println("Opponent's Board:");
                    System.out.println(player1);
                    System.out.println("Player2 Move:");
                    int move = promptMove();
                    while(!player1.fireAtSpace(move)) {
                        System.out.println("Invalid move");
                        System.out.println("Opponent's Board:");
                        System.out.println(player2);
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
            s = promptForShip(name, length, player1);
            while(!player1.addShip(s)) {
                System.out.println(player1.toStringWithShipSpaces());
                s = promptForShip(name, length, player1);
            }
        }
        System.out.println(player1.toStringWithShipSpaces());
        if(cpuPlaying) {
            player2.placeShipsRandomly();
        }
        else {
            for(int i = 0; i < 10; i++) {
                System.out.println();
            }
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
                s = promptForShip(name, length, player2);
                while(!player2.addShip(s)) {
                    System.out.println(player2.toStringWithShipSpaces());
                    s = promptForShip(name, length, player2);
                }
            }
            System.out.println(player2.toStringWithShipSpaces());
            for(int i = 0; i < 10; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Prints out an empty board.
     */
    public void printBoardIndexing() {
        String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String toReturn = "\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\n";
        System.out.println(toReturn);
        for(int i = 1; i <= 10; i++) {
            String tabs = rows[i] + "";
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
        int toReturn = parseInput(move);
        return toReturn;
    }

    /**
     * Prompts the user for a space to put a certain ship.
     * @param name the type of the ship that is being created, such as "Destroyer"
     * @param length the length of the ship
     * @return a Ship Object created using name, length, user-selected Orientation, and spaces.
     */
    public Ship promptForShip(String name, int length, Board player) {
        Orientation state = Orientation.HORIZONTAL;
        System.out.println("Where would you like to put your " + name + "?");
        System.out.println("The orientation is currently: " + state + "\nType \"R\" to rotate.");
        Scanner input = new Scanner(System.in);
        String index = input.nextLine();
        while(index.toLowerCase().equals("r")) {
            if(state == Orientation.HORIZONTAL)
                state = Orientation.VERTICAL;
            else
                state = Orientation.HORIZONTAL;
            System.out.println(player.toStringWithShipSpaces());
            System.out.println("Where would you like to put your " + name + "?");
            System.out.println("The orientation is currently: " + state + "\nType \"R\" to rotate.");
            index = input.nextLine();
        }
        int firstIndex = parseInput(index);
        return new Ship(length, firstIndex, state, name);
    }

    /**
     * Parses an input from the user so the program can handle a flat out letter as well as the indexing usually used in
     * Battleship.
     * @param input String to parse
     * @return index from String
     */
    private int parseInput(String input) {
        String lowercase = input.toLowerCase();
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        boolean containsLetter = false;
        char halfIndex = 'a';
        for(char letter : lowercase.toCharArray()) {
            for(char secondLetter : alphabet) {
                if(letter == secondLetter) {
                    containsLetter = true;
                    halfIndex = letter;
                    break;
                }
            }
        }
        if(containsLetter) {
            if(input.length() == 3) {
                int row = (int) (halfIndex - 'a');
                return row * 10 + 9;
            }
            int row = (int) (halfIndex - 'a');
            int indexOfLetter = lowercase.indexOf(halfIndex);
            int indexOfNumber = (indexOfLetter + 1) % 2;
            String number = lowercase.charAt(indexOfNumber) + "";
            int column = Integer.parseInt(number) - 1;
            return row * 10 + column;
        }
        else {
            return Integer.parseInt(input);
        }
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
        if(isGameOver)
            gameInProgress = false;
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
        gameInProgress = false;

    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * A modification of the runGame method that plays two Computers against each other.
     */
    public void testTwoCPUS() {
        player1.placeShipsRandomly();
        player2.placeShipsRandomly();
        while(!isGameOver) {
            try {
                Thread.sleep(waitTime);
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
        if(player1.getFloatingShips().size() == 0) {
            player1Win = false;
        }
        else {
            player1Win = true;
        }
    }

    /**
     * Returns the winner of the game
     * @return true if player1 won
     */
    public boolean getWinner() {
        return player1Win;
    }

    /**
     * Returns the number of turns.
     * @return number of turns
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Sets the difficulty of player1 Board.
     * @param difficulty desired difficulty
     */
    public void setPlayer1Difficulty(int difficulty) {
        player1.setDifficulty(difficulty);
    }

    /**
     * Sets the difficulty of player1 Board.
     * @param difficulty desired difficulty
     */
    public void setPlayer2Difficulty(int difficulty) {
        player2.setDifficulty(difficulty);
    }

    /**
     * Fires at a space in player1's board.
     * @param space space to fire at
     * @return true if fired without problem, false if not player's turn or not in availableMoves for player1's board.
     */
    public boolean player1FireAtSpace(int space) {
        if(!player1Turn && gameInProgress) {
            return player1.fireAtSpace(space);
        }
        return false;
    }

    /**
     * Fires at a space in player2's board.
     * @param space space to fire at
     * @return true if fired without problem, false if not player's turn or not in availableMoves for player2's board.
     */
    public boolean player2FireAtSpace(int space) {
        if(player1Turn && gameInProgress) {
            return player2.fireAtSpace(space);
        }
        return false;
    }

    /**
     * Resets the game, gets it ready for start.
     */
    public void startGame() {
        resetGame();

        gameInProgress = true;
    }

}