import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * @author Dylan Bui
 */
public class Board {
    /**
     * A Set containing all of the spaces containing ships that can still be fired at.
     */
    private HashSet<Integer> shipSpaces;
    /**
     * A Set containing all moves that can still be made.
     */
    private HashSet<Integer> availableMoves;
    /**
     * An ArrayList that holds all of the Ship objects that have not sunk yet.
     */
    private ArrayList<Ship> floatingShips;
    /**
     * An ArrayList that holds all of the ships on the board, regardless of whether they have been sunk.
     */
    private ArrayList<Ship> shipList;
    /**
     * A Map that maps the number of possible vertical ship orientations that could contain a given square.
     */
    private HashMap<Integer, Integer> verticalConnections;
    /**
     * A Map that maps the number of possible horizontal ship orientations that could contain a given square.
     */
    private HashMap<Integer, Integer> horizontalConnections;
    /**
     * A Map that maps the number of total possible ship orientations that could contain a given square.
     */
    private HashMap<Integer, Integer> totalConnections;
    /**
     * An ArrayList containing the indices of squares that have been hit but not searched.
     */
    private ArrayList<Integer> hits;
    /**
     * Average length of all surviving Ships
     */
    private int averageShipLength;
    /**
     * An int representing the difficulty of the computer.
     */
    private int difficulty;
    /**
     * A Set containing all spaces that have been hit.
     */
    private HashSet<Integer> allHits;
    /**
     * A boolean storing true if the last move made on the board was a hit.
     */
    private boolean lastMoveHit;
    /**
     * A boolean storing true if the last move made on the board sunk a ship.
     */
    private boolean lastMoveSunk;
    /**
     * The most recently sunken ship on the board.
     */
    private Ship lastSunkShip;

    /**
     * Default Constructor. It initializes all values.
     */
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

    /**
     * A Method that attempts to add a Ship object to shipList as well as floatingShips.
     * @param index The index of the desired front of the Ship.
     * @param length The length of the Ship.
     * @param state The Orientation of the Ship, horizontal or vertical.
     * @return true if the Ship was successfully added, false otherwise.
     */
    public boolean addShip(int index, int length, Orientation state) {
        return addShip(new Ship(length, index, state));
    }

    /**
     * An overloading on the previous addShip method that also includes the name of the Ship.
     * @param index The index of the desired front of the Ship.
     * @param length The length of the Ship.
     * @param state The Orientation of the Ship, horizontal or vertical.
     * @param name String name of the Ship, such as "Patrol Boat".
     * @return true if the Ship was successfully added.
     */
    public boolean addShip(int index, int length, Orientation state, String name) {
        return addShip(new Ship(length, index, state, name));
    }

    /**
     * An overloading on the previous addShip methods that accepts a ship.
     * @param ship The Ship to be added.
     * @return true if the Ship was successfully added.
     */
    public boolean addShip(Ship ship) {
        boolean possibleShip = true;
        for(int i = 0; i < ship.getLength(); i++) {
            if (shipSpaces.contains(ship.getSpaces().get(i))) {
                possibleShip = false;
                break;
            }
            if(ship.getSpaces().get(i) >= 100) {
                possibleShip = false;
                break;
            }
        }
        if(ship.getOrientation() == Orientation.HORIZONTAL) {
            int row = ship.getSpaces().get(0) / 10;
            for(int space : ship.getSpaces()) {
                if(space / 10 != row) {
                    possibleShip = false;
                    break;
                }
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

    /**
     * Fires at a space on the board, if the space is in the availableMoves. Also updates whether or not the move was a
     * hit, as well as whether the move sank any ships.
     * @param index the index to fire at.
     * @return true if the space could be fired at.
     */
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

    /**
     * Checks a space to see if a ship is at that space and the space has not been hit.
     * @param index the space to check.
     * @return true if the space has a ship and has not been hit yet.
     */
    public boolean checkShipAtSpace(int index) {
        return shipSpaces.contains(index);
    }

    /**
     * For the computer to use when playing the game. If there are hits that have been made previously that have not
     * sunken ships, the algorithm prioritizes those spaces until the ship has been sunken. Otherwise, choose the space
     * with the highest number of connections.
     * @return the index of the "best" move.
     */
    public int getBestMove() {
        if(hits.size() != 0) {
            int currIndex = hits.get(0);
            while(hits.contains(currIndex) && currIndex >= 0) {
                currIndex = currIndex - 10;
            }
            if(availableMoves.contains(currIndex))
                return currIndex;
            currIndex = hits.get(0);
            while(hits.contains(currIndex) && currIndex <= 99) {
                currIndex = currIndex + 10;
            }
            if(availableMoves.contains(currIndex))
                return currIndex;
            currIndex = hits.get(0);
            while(hits.contains(currIndex) && currIndex / 10 == hits.get(0) / 10) {
                currIndex--;
            }
            if(availableMoves.contains(currIndex)) {
                return currIndex;
            }
            currIndex = hits.get(0);
            while(hits.contains(currIndex) && currIndex / 10 == hits.get(0) / 10) {
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

    /**
     * Randomly places all 5 Ships onto the board.
     */
    public void placeShipsRandomly() {
        int[] lengths = {2, 3, 3, 4, 5};
        String[] names = {"Patrol Boat", "Submarine", "Destroyer", "Battleship", "Carrier"};
        for(int i = 0; i < 5; i++) {
            int index;
            Orientation o = null;
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
            while(!addShip(index, lengths[i], o, names[i])) {
                index = (int) (Math.random() * 100);
                orientation = (int) (Math.random() * 2);
                switch(orientation) {
                    case 0:
                        o = Orientation.HORIZONTAL;
                        break;
                    case 1:
                        o = Orientation.VERTICAL;
                        break;
                }
            }
        }

    }

    /**
     * Creates an ArrayList of the indices with the most connections. Ignores every other space in a checkerboard-like
     * pattern, to keep parity.
     * @return an ArrayList of the indices with the highest number of connections.
     */
    private ArrayList<Integer> generateMaxConnections() {
        updateVerticalConnections();
        updateHorizontalConnections();
        updateTotalConnections();

        for(int i = 0; i < 100; i+=2) {
            totalConnections.remove(i + ((i/10)%2), totalConnections.get(i + ((i/10)%2)));
        }

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

    /**
     * Updates the averageShipLength field.
     */
    private void updateAverageShipLength() {
        int averageLength = 0;
        updateFloatingShips();
        for(Ship ship : floatingShips) {
            averageLength += ship.getLength();
        }
        averageLength = averageLength/shipSpaces.size();
        averageShipLength = averageLength;
    }

    /**
     * Updates the list of floating Ships to see if any Ships on the list have been sunk.
     */
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

    /**
     * Updates the number of vertical connections.
     */
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

    /**
     * Updates the number of horizontal connections.
     */
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

    /**
     * Sums the number of vertical and horizontal connections to update total connections.
     */
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

    /**
     * Checks to see if there is a vertical strip of length spaces starting at space of possible moves.
     * @param space space to start at
     * @param length the length of the strip
     * @return true if all spaces can still be fired on.
     */
    private boolean checkVerticalConnection(int space, int length) {

        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(space + i * 10))
                return false;
        }
        return true;
    }

    /**
     * A variation of the checkVerticalConnections method that takes a start and end point.
     * @param start index to start at.
     * @param end index to end at.
     * @return true if all spaces can still be fired on.
     */
    private boolean checkVerticalConnection2(int start, int end) {
        int length = end/10 - start/10;
        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(start + i * 10))
                return false;
        }
        return true;
    }

    /**
     * Checks to see if there is a horizontal strip of length spaces starting at space of possible moves.
     * @param space space to start at
     * @param length the length of the strip
     * @return true if all spaces can still be fired on.
     */
    private boolean checkHorizontalConnection(int space, int length) {
        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(space + i))
                return false;
            if(space / 10 != (space + i) / 10)
                return false;
        }
        return true;
    }

    /**
     * A variation of the checkHorizontalConnection method that takes a start and end point.
     * @param start index to start at.
     * @param end index to end at.
     * @return true if all spaces can still be fired on.
     */
    private boolean checkHorizontalConnection2(int start, int end) {
        int length = end - start;
        for(int i = 0; i < length; i++) {
            if(!availableMoves.contains(start + i))
                return false;
        }
        return true;
    }

    /**
     * Returns the value of lastMoveHit.
     * @return lastMoveHit.
     */
    public boolean isLastMoveHit() {
        return lastMoveHit;
    }

    /**
     * Returns the value of lastMoveSunk.
     * @return lastMoveSunk.
     */
    public boolean isLastMoveSunk() {
        return lastMoveSunk;
    }

    /**
     * Returns value of lastSunkShip.
     * @return lastSunkShip
     */
    public Ship getLastSunkShip() {
        return lastSunkShip;
    }

    /**
     * Resets the Board.
     */
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

    /**
     * Fills availableMoves with all 100 spaces on the board.
     */
    private void resetAvailableMoves() {
        availableMoves.clear();
        for(int i = 0; i < 100; i++) {
            availableMoves.add(i);
        }
    }

    /**
     * Checks if a shot at a space will sink a ship.
     * @param index the space to check
     * @return true if the shot will sink a ship.
     */
    private boolean checkIfShipWillSink(int index) {
        for(Ship s : floatingShips) {
            if(s.getSpaces().contains(index)) {
                for(int space : s.getSpaces()) {
                    if(space != index) {
                        if(availableMoves.contains(space)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns the ship containing a given index.
     * @param index the index to search for.
     * @return the Ship if there is a ship containing the index, else null.
     */
    private Ship shipWithIndex(int index) {
        for(Ship s : floatingShips) {
            if(s.getSpaces().contains(index))
                return s;
        }
        return null;
    }

    /**
     * Checks whether the game has ended.
     * @return true if the game has ended.
     */
    public boolean checkGameOver() {
//        System.out.println("floatingShips size: " + floatingShips.size() + "\navailableMoves size: " + availableMoves.size());
        return floatingShips.size() == 0 || availableMoves.size() == 0;
    }

    /**
     * Removes a Ship from shipList and floatingShips.
     * @param s the Ship to remove.
     * @return true if the Ship was successfully removed.
     */
    public boolean removeShip(Ship s) {
        if(shipList.contains(s)) {
            shipList.remove(s);
            floatingShips.remove(s);
            return true;
        }
        return false;
    }

    /**
     * Returns a String representation of the entire board, including bordering indexing.
     * @return the String representation
     */
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

    /**
     * Modification of toString that shows where shipSpaces are.
     * @return the String representation
     */
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

    /**
     * Returns the ArrayList of the floatingShips.
     * @return floatingShips.
     */
    public ArrayList<Ship> getFloatingShips() {
        return floatingShips;
    }

    /**
     * Returns an int[] array representing the current state of the game board.
     * 0 is an empty unfired space.
     * 1 is a missed shot.
     * 2 is a unfired ship space.
     * 3 is a hit shot.
     * @return an int[] array representing the board.
     */
    public int[] getIntArrayRepresentation() {
        int[] toReturn = new int[100];
        for(int i = 0; i < 100; i++) {
            if(availableMoves.contains(i)) {
                for(Ship s : shipList) {
                    if(s.getSpaces().contains(i)) {
                        toReturn[i] = 2;
                        break;
                    }
                }
                if(toReturn[i] != 2)
                    toReturn[i] = 0;
            }
            else {
                if(allHits.contains(i))
                    toReturn[i] = 3;
                else {
                    toReturn[i] = 1;
                }
            }
        }
        return toReturn;
    }
}
