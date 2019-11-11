import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    HashSet<Integer> shipSpaces;
    HashSet<Integer> availableMoves;
    ArrayList<Ship> floatingShips;

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
}
