import java.util.ArrayList;

/**
 * A class made to represent a single ship in Battleship.
 * @author Dylan Bui
 */
public class Ship {
    /**
     * Length of the ship.
     */
    private int length;
    /**
     * An ArrayList of each of the spaces that the ship is on.
     */
    private ArrayList<Integer> spaces;
    /**
     * The type of ship, such as "Carrier"
     */
    private String type;
    /**
     * The orientation of the ship, vertical or horizontal along the board.
     */
    private Orientation orientation;

    /**
     * Basic constructor for the Ship object.
     * @param length length of the Ship
     * @param firstIndex the first space that the ship occupies
     * @param state the Orientation of the ship
     */
    public Ship(int length, int firstIndex, Orientation state) {
        this.length = length;
        spaces = new ArrayList<>(length);
        if(state == Orientation.HORIZONTAL) {
            for(int i = 0; i < length; i++) {
                spaces.add(i + firstIndex);
            }
        }
        else {
            for(int i = 0; i < length; i++) {
                spaces.add(firstIndex + i * 10);
            }
        }
        orientation = state;
        type = "No type";
    }

    /**
     * An improved constructor, this is the same as the previous except it also includes the type of the ship.
     * @param length length of the ship
     * @param firstIndex the space of the front of the ship
     * @param state the Orientation of the ship
     * @param name the type of ship, such as "Submarine"
     */
    public Ship(int length, int firstIndex, Orientation state, String name) {
        this.length = length;
        spaces = new ArrayList<>(length);
        if(state == Orientation.HORIZONTAL) {
            for(int i = 0; i < length; i++) {
                spaces.add(i + firstIndex);
            }
        }
        else {
            for(int i = 0; i < length; i++) {
                spaces.add(firstIndex + i * 10);
            }
        }
        type = name;
        orientation = state;
    }

    /**
     * Returns the length of the ship.
     * @return the ship's length
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the ship to parameter length
     * @param length desired length of the ship
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the ArrayList of spaces the ship occupies.
     * @return An ArrayList containing the ship's spaces
     */
    public ArrayList<Integer> getSpaces() {
        return spaces;
    }

    /**
     * Sets the spaces of the ship to a new ArrayList.
     * @param spaces the new desired spaces
     */
    public void setSpaces(ArrayList<Integer> spaces) {
        this.spaces = spaces;
    }

    /**
     * Returns the ship type
     * @return A String of the ship type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the ship to a certain type
     * @param type String of the new ship type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Updates equals method for comparison of Ship objects against each other. Ships are the same if they have the same
     * length, spaces, and type.
     * @param o the object to compare to
     * @return true if o is the same as this Ship
     */
    public boolean equals(Object o) {
        if(!(o instanceof Ship)) {
            return false;
        }
        if(o == this)
            return true;
        Ship s = (Ship) o;
        if(s.getLength() != length)
            return false;
        if(s.getType().equals(type))
            return false;
        if(s.getSpaces().size() != spaces.size())
            return false;
        for(int space : s.getSpaces()) {
            if(!spaces.contains(space))
                return false;
        }
        return true;
    }

    /**
     * Sets the Orientation of the ship
     * @param orientation Desired Orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns the Orientation of the Ship
     * @return the Orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Returns the type of the ship
     * @return the ship's name
     */
    public String toString() {
        return type;
    }
}
