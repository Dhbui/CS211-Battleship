import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Board {
    //Fireable spaces with a ship on them
    private HashSet<Integer> shipSpaces;
    //Fireable spaces
    private HashSet<Integer> availableMoves;
    //All floating ships on the board
    private ArrayList<Ship> floatingShips;
    //All ships that started on the board
    private ArrayList<Ship> shipList;
    //Number of vertically connected spaces of length averageShipLength that each square is part of
    private HashMap<Integer, Integer> verticalConnections;
    //Number of horizontally connected spaces of length averageShipLength that each square is part of
    private HashMap<Integer, Integer> horizontalConnections;
    //Sum of each space's vertical and horizontal connections
    private HashMap<Integer, Integer> totalConnections;
    //List of hits that need to be cleared
    private ArrayList<Integer> hits;
    //Average length of all surviving Ships
    private int averageShipLength;
    //Difficulty
    private int difficulty;
    //List of all hits made ever for toString purposes
    private HashSet<Integer> allHits;
    //Whether or not the last move hit
    private boolean lastMoveHit;
    //Whether or not the last move sunk a ship
    private boolean lastMoveSunk;
    //What the last ship sunk was
    private Ship lastSunkShip;

    public Board() {
        shipSpaces = new HashSet<>();
        allHits = new HashSet<>();

        availableMoves = new HashSet<>();
        for(int i = 0; i < 100; i++) {
            availableMoves.add(i);
        }
        floatingShips = new ArrayList<>();
        shipList = new ArrayList<>();

        verticalConnections = new HashMap<>();
        horizontalConnections = new HashMap<>();
        totalConnections = new HashMap<>();

        averageShipLength = 3;

        updateVerticalConnections();
        updateHorizontalConnections();
        updateTotalConnections();

        hits = new ArrayList<>();

        lastMoveHit = false;
        lastMoveSunk = false;
        lastSunkShip = null;
    }

    //Returns false if ship could not be added, true otherwise
    public boolean addShip(int index, int length, Orientation state) {
        return addShip(new Ship(length, index, state));
    }

    public boolean addShip(int index, int length, Orientation state, String name) {
        return addShip(new Ship(length, index, state, name));
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
        shipList.add(ship);
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
                    floatingShips.remove(s);
                    lastMoveSunk = true;
                    lastSunkShip = s;
                }
                else {
                    lastMoveSunk = false;
                }
                shipSpaces.remove(index);
                allHits.add(index);
                lastMoveHit = true;
            }
            else {
                lastMoveHit = false;
            }
            return true;
        }
        return false;
    }

    public boolean checkShipAtSpace(int index) {
        return shipSpaces.contains(index);
    }

    public int getBestMove() {
        if(hits.size() != 0) {
            int currIndex = hits.get(0);
            while(hits.contains(currIndex)) {
                currIndex = currIndex - 10;
            }
            if(availableMoves.contains(currIndex))
                return currIndex;
            currIndex = hits.get(0);
            while(hits.contains(currIndex)) {
                currIndex = currIndex + 10;
            }
            if(availableMoves.contains(currIndex))
                return currIndex;
            currIndex = hits.get(0);
            while(hits.contains(currIndex)) {
                currIndex--;
            }
            if(availableMoves.contains(currIndex)) {
                return currIndex;
            }
            currIndex = hits.get(0);
            while(hits.contains(currIndex)) {
                currIndex++;
            }
            if(availableMoves.contains(currIndex))
                return currIndex;
            return -1;
        }
        else {

            updateAverageShipLength();

            ArrayList<Integer> maxConnections = generateMaxConnections();
            int moveToMake = maxConnections.get((int) (Math.random() * (maxConnections.size())));
            return moveToMake;
        }
    }

    public void placeShipsRandomly() {
        int[] lengths = {2, 3, 3, 4, 5};
        String[] names = {"Patrol Boat", "Submarine", "Destroyer", "Battleship", "Carrier"};
        for(int i = 0; i < 5; i++) {
            int index;
            Orientation o = null;
            do {
                index = (int) (Math.random() * 100);
                int orientation = (int) (Math.random() * 2);
                switch(orientation) {
                    case 0:
                        o = Orientation.HORIZONTAL;
                        break;
                    case 1:
                        o = Orientation.VERTICAL;
                        break;
                }
            }
            while(addShip(lengths[i], index, o, names[i]));
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
        for(int i = 0; i <= lastRow * 10 + 9; i++) {
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
        for(int i = 0; i <= lastColumn; i++) {
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
            if(horizontalConnections.containsKey(i) && verticalConnections.containsKey(i)) {
                totalConnections.put(i, horizontalConnections.get(i) + verticalConnections.get(i));
            }
            else if(horizontalConnections.containsKey(i) && !verticalConnections.containsKey(i)) {
                totalConnections.put(i, horizontalConnections.get(i));
            }
            else if(!horizontalConnections.containsKey(i) && verticalConnections.containsKey(i)) {
                totalConnections.put(i, verticalConnections.get(i));
            }
            else {
                totalConnections.put(i, 0);
            }
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

    public boolean isLastMoveHit() {
        return lastMoveHit;
    }

    public boolean isLastMoveSunk() {
        return lastMoveSunk;
    }

    public Ship getLastSunkShip() {
        return lastSunkShip;
    }

    public void reset() {
        resetAvailableMoves();
        shipSpaces.clear();
        floatingShips.clear();
        verticalConnections.clear();
        horizontalConnections.clear();
        totalConnections.clear();
        hits.clear();
        averageShipLength = 3;
        shipList.clear();
        allHits.clear();
        lastMoveSunk = false;
        lastMoveHit = false;
        lastSunkShip = null;
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

    public boolean removeShip(Ship s) {
        if(shipList.contains(s)) {
            shipList.remove(s);
            floatingShips.remove(s);
            return true;
        }
        return false;
    }

    public String toString() {
        String toReturn = "\tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\n";
        for(int i = 0; i < 10; i++) {
            toReturn += i + "\t";
            for(int j = 0; j < 10; j++) {
                if(availableMoves.contains(i * 10 + j)) {
                    toReturn += "\t";
                }
                else {
                    if(allHits.contains(i * 10 + j)) {
                        toReturn += "X\t";
                    }
                    else {
                        toReturn += "O\t";
                    }
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }

    public String toStringWithShipSpaces() {
        String toReturn = "\tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\n";
        for(int i = 0; i < 10; i++) {
            toReturn += i + "\t";
            for(int j = 0; j < 10; j++) {
                if(availableMoves.contains(i * 10 + j)) {
                    if(shipSpaces.contains(i * 10 + j)) {
                        toReturn += ".\t";
                    }
                    else {
                        toReturn += "\t";
                    }
                }
                else {
                    if(allHits.contains(i * 10 + j)) {
                        toReturn += "X\t";
                    }
                    else {
                        toReturn += "O\t";
                    }
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }
}
