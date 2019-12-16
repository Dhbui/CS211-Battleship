/**
 * Enum of possible orientations
 * @author Dylan Bui
 */
public enum Orientation {
    HORIZONTAL, VERTICAL;

    /**
     * Returns the opposing orientation. If currently HORIZONTAL, returns VERTICAl, and vice versa.
     * @return the opposite Orientation.
     */
    public Orientation getOpposite() {
        if(this == HORIZONTAL)
            return VERTICAL;
        else
            return HORIZONTAL;
    }
}
