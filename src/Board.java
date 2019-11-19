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

    HashMap<Integer, Integer> verticalConnections;

    HashMap<Integer, Integer> horizontalConnections;

    HashMap<Integer, Integer> totalConnections;

    public Board() {
        shipSpaces = new HashSet<>();
        availableMoves = new HashSet<>();
        for(int i = 0; i < 100; i++) {
            availableMoves.add(i);
        }
        floatingShips = new ArrayList<>(5);
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
            return true;
        }
        return false;
    }

    public boolean checkShipAtSpace(int index) {
        return shipSpaces.contains(index);
    }

    public int getBestMove() {
        /**ADD CHECK AROUND SHIP SPACES THAT HAVE BEEN FIRED ON*/
        int averageLength = getAverageShipLength();

        HashSet<Integer> bestMoves = (HashSet<Integer>) availableMoves.clone();


        /**REMOVE*/
        return 0;

    }

    private int getAverageShipLength() {
        int averageLength = 0;
        for(Ship ship : floatingShips) {
            averageLength += ship.getLength();
        }
        averageLength = averageLength/shipSpaces.size();
        return averageLength;
    }

    private boolean checkVerticalConnection(int space, int length) {

        /**REMOVE*/
        return true;
    }
}
