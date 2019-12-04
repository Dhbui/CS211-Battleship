import java.util.ArrayList;
import java.util.Scanner;

public class BattleshipGame {
    private Board player1;
    private Board player2;
    private boolean player1Turn;
    private int turnCount;
    private boolean isGameOver;
    private String type;
    private boolean cpuPlaying;
    private boolean player1Win;
    private ArrayList<Integer> movesMade;

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

    public boolean checkGameOver() {
        boolean p1check = player1.checkGameOver();
//        System.out.println("P1check: " + p1check);
        boolean p2check = player2.checkGameOver();
//        System.out.println("P2check: " + p2check);
        isGameOver = p1check || p2check;
        return isGameOver;
    }

    public void resetGame() {
        player1.reset();
        player2.reset();
        movesMade = new ArrayList<>();
        player1Turn = Math.random() >= 0.5;
        turnCount = 0;
        isGameOver = false;

    }

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