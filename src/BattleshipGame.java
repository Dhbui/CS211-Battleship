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
                System.out.println("Player1 Move:");
                int move = promptMove();
                while(!player2.fireAtSpace(move)) {
                    System.out.println("Player1 Move:");
                    move = promptMove();
                }
                player1Turn = false;
                turnCount++;
                System.out.println("Player1's Move:");
                System.out.println(player2);
                System.out.println();
                if(player2.isLastMoveHit())
                    System.out.println("Hit!");
                if(player2.isLastMoveSunk()) {
                    System.out.println("You've sunk a " + player2.getLastSunkShip().getType() + "!\n\n");
                }
            }
            else {
                if(cpuPlaying) {
                    int move = player1.getBestMove();
                    player1.fireAtSpace(move);
                    System.out.println("Computer's Move:");
                }
                else {
                    System.out.println("Player2 Move:");
                    int move = promptMove();
                    while(!player1.fireAtSpace(move)) {
                        System.out.println("Player2 Move:");
                        move = promptMove();
                    }
                    System.out.println("Player2's Move:");

                }
                player1Turn = true;
                turnCount++;
                System.out.println(player1);
                System.out.println();
                if(player1.isLastMoveHit())
                    System.out.println("Hit!");
                if(player1.isLastMoveSunk()) {
                    System.out.println("You've sunk a " + player1.getLastSunkShip().getType() + "!\n\n");
                }
            }
            checkGameOver();
        }
        System.out.println("Game Over!");
        if (player1.getFloatingShips().size() == 0)
            System.out.print("Player2 Wins!");
        else
            System.out.println("Player1 Wins!");
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

    public void checkGameOver() {
        isGameOver =  player1.checkGameOver() || player2.checkGameOver();
    }

    public void resetGame() {
        player1.reset();
        player2.reset();
    }
}
