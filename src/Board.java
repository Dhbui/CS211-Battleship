import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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
    ArrayList<Integer> hits;
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

        updateVerticalConnections();
        updateHorizontalConnections();
        updateTotalConnections();

        hits = new ArrayList<>();
        averageShipLength = 3;
    }

    //Returns false if ship could not be added, true otherwise
    public boolean addShip(int index, int length, Orientation state) {
        return addShip(new Ship(length, index, state));
    }

    public boolean addShip(Ship ship) {
        boolean possibleShip = true;
        for(int i = 0; i < ship.getLength(); i++) {
            if (shipSpaces.contains(ship.getSpaces().get(i))) {
                possibleShip = false;
                break;
            }
        }
        if(!possibleShip)
            return false;
        for(int i = 0; i < ship.getLength(); i++) {
            shipSpaces.add(ship.getSpaces().get(i));
        }
        floatingShips.add(ship);
        return true;
    }

    public boolean fireAtSpace(int index) {
        if(availableMoves.contains(index)) {
            availableMoves.remove(index);
            if(checkShipAtSpace(index)) {
                hits.add(index);
                if(checkIfShipWillSink(index)) {
                    Ship s = shipWithIndex(index);
                    for (int space : s.getSpaces()) {
                        if (hits.contains(space))
                            hits.remove((Integer) space);

                    }
                }
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
        if(hits.size() != 0) {
            int index = hits.get(0);
            if(hits.contains(index - 10)) {
                index = index - 10;

            }
        }
        else {

            updateAverageShipLength();

            ArrayList<Integer> maxConnections = generateMaxConnections();
            int moveToMake = maxConnections.get((int) (Math.random() * (maxConnections.size())));
            return moveToMake;
        }
    }

    private ArrayList<Integer> generateMaxConnections() {
        updateVerticalConnections();
        updateHorizontalConnections();
        updateTotalConnections();

        int max = 0;
        for(int key : totalConnections.keySet()) {
            if(totalConnections.get(key) > max) {
                max = totalConnections.get(key);
            }
        }

        ArrayList<Integer> toReturn = new ArrayList<>();

        for(int key : totalConnections.keySet()) {
            if(totalConnections.get(key) == max) {
                toReturn.add(key);
            }
        }

        return toReturn;
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
                if (availableMoves.contains(space)) {
                    floating = true;
                    break;
                }
            }
            if(floating) {
                newList.add(ship);
            }
        }
        floatingShips = newList;
    }

    private void updateVerticalConnections() {
        verticalConnections = new HashMap<>();
        int lastRow = 9 - (averageShipLength - 1);
        for(int i = 0; i < lastRow * 10 + 9; i++) {
            if(checkVerticalConnection(i, averageShipLength)) {
                for(int j = 0; j < averageShipLength; j++) {
                    if(verticalConnections.containsKey(i + j * 10)) {
                        verticalConnections.put(i + j * 10, verticalConnections.get(i + j * 10) + 1);
                    }
                    else{
                        verticalConnections.put(i + j * 10, 1);
                    }
                }
            }
        }
    }

    private void updateHorizontalConnections() {
        horizontalConnections = new HashMap<>();
        int lastColumn = 9 - (averageShipLength - 1);
        for(int i = 0; i < lastColumn; i++) {
            for(int j = i; j < 100; j+= 10) {
                if(checkHorizontalConnection(j, averageShipLength)) {
                    for(int k = 0; k < averageShipLength; k++) {
                        if (horizontalConnections.containsKey(j + k)) {
                            horizontalConnections.put(j + k, horizontalConnections.get(j + k) + 1);
                        } else {
                            horizontalConnections.put(j + k, 1);
                        }
                    }
                }
            }
        }
    }

    private void updateTotalConnections() {
        for(int i = 0; i < 100; i++) {
            totalConnections.put(i, horizontalConnections.get(i) + verticalConnections.get(i));
        }
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
        hits.clear();
        averageShipLength = 0;
    }

    private void resetAvailableMoves() {
        availableMoves.clear();
        for(int i = 0; i < 100; i++) {
            availableMoves.add(i);
        }
    }

    private boolean checkIfShipWillSink(int index) {
        for(Ship s : floatingShips) {
            if(s.getSpaces().contains(index)) {
                for(int space : s.getSpaces()) {
                    if(space != index) {
                        if(availableMoves.contains(space))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private Ship shipWithIndex(int index) {
        for(Ship s : floatingShips) {
            if(s.getSpaces().contains(index))
                return s;
        }
        return null;
    }

    public boolean checkGameOver() {
        return floatingShips.size() == 0 || availableMoves.size() == 0;
    }
}
