import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
    //Fireable spaces with a ship on them
    HashSet<Integer> shipSpaces;
    //Fireable spaces
    HashSet<Integer> availableMoves;
    //All floating ships on the board
    ArrayList<Ship> floatingShips;
    //Number of vertically connected spaces of length averageShipLength that each square is part of
    HashMap<Integer, Integer> verticalConnections;
    //Number of horizontally connected spaces of length averageShipLength that each square is part of
    HashMap<Integer, Integer> horizontalConnections;
    //Sum of each space's vertical and horizontal connections
    HashMap<Integer, Integer> totalConnections;
    //List of all moves made
    int[] movesMade;
    //Current move number
    int currMove;
    //Average length of all surviving Ships
    int averageShipLength;
    //Difficulty
    int difficulty;

    public Board() {
        shipSpaces = new HashSet<>();
        availableMoves = new HashSet<>();
        for(int i = 0; i < 100; i++) {
            availableMoves.add(i);
        }
        floatingShips = new ArrayList<>();
        verticalConnections = new HashMap<>();
        horizontalConnections = new HashMap<>();
        totalConnections = new HashMap<>();
        movesMade = new int[100];
        currMove = 0;
        averageShipLength = 3;
    }

    //Returns false if ship could not be added, true otherwise
    public boolean addShip(int index, int length, Orientation state) {
        return addShip(new Ship(length, index, state));
    }

    public boolean addShip(Ship ship) {
        boolean possibleShip = true;
        for(int i = 0; i < ship.getLength(); i++) {
            if(shipSpaces.contains(ship.getSpaces()[i]))
                possibleShip = false;
        }
        if(!possibleShip)
            return false;
        for(int i = 0; i < ship.getLength(); i++) {
            shipSpaces.add(ship.getSpaces()[i]);
        }
        floatingShips.add(ship);
        return true;
    }

    public boolean fireAtSpace(int index) {
        if(availableMoves.contains(index)) {
            availableMoves.remove(index);
            if(checkShipAtSpace(index)) {
                shipSpaces.remove(index);
            }
            movesMade[currMove++] = index;
            return true;
        }
        return false;
    }

    public boolean checkShipAtSpace(int index) {
        return shipSpaces.contains(index);
    }

    public int getBestMove() {
        /**ADD CHECK AROUND SHIP SPACES THAT HAVE BEEN FIRED ON*/
        updateAverageShipLength();


        /**REMOVE*/
        return 0;

    }

    private void updateAverageShipLength() {
        int averageLength = 0;
        updateFloatingShips();
        for(Ship ship : floatingShips) {
            averageLength += ship.getLength();
        }
        averageLength = averageLength/shipSpaces.size();
        averageShipLength = averageLength;
    }

    private void updateFloatingShips() {
        ArrayList<Ship> newList = new ArrayList<>();
        for(Ship ship : floatingShips) {
            boolean floating = false;
            for(int space : ship.getSpaces()) {
                if(availableMoves.contains(space)) {
                    floating = true;
                }
            }
            if(floating) {
                newList.add(ship);
            }
        }
        floatingShips = newList;
    }

    private void updateVerticalConnections() {
        int lastRow = 9 - (averageShipLength - 1);

    }

    private boolean checkVerticalConnection(int space, int length) {

        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(space + i * 10))
                return false;
        }
        return true;
    }

    private boolean checkVerticalConnection2(int start, int end) {
        int length = end/10 - start/10;
        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(start + i * 10))
                return false;
        }
        return true;
    }

    private boolean checkHorizontalConnection(int space, int length) {
        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(space + i))
                return false;
        }
        return true;
    }

    private boolean checkHorizontalConnection2(int start, int end) {
        int length = end - start;
        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(start + i))
                return false;
        }
        return true;
    }

    public void reset() {
        resetAvailableMoves();
        shipSpaces.clear();
        floatingShips.clear();
        verticalConnections.clear();
        horizontalConnections.clear();
        totalConnections.clear();
        movesMade = new int[100];
        currMove = 0;
        averageShipLength = 0;

    }

    private void resetAvailableMoves() {
        availableMoves.clear();
        for(int i = 0; i < 100; i++) {
            availableMoves.add(i);
        }
    }
}
