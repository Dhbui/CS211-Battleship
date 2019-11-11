public class Ship {
    private int length;
    private int[] spaces;

    public Ship(int length, int firstIndex, Orientation state) {
        this.length = length;
        spaces = new int[length];
        if(state == Orientation.HORIZONTAL) {
            for(int i = 0; i < length; i++) {
                spaces[i] = i + firstIndex;
            }
        }
        else {
            for(int i = 0; i < length; i++) {
                spaces[i] = firstIndex + i * 10;
            }
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getSpaces() {
        return spaces;
    }

    public void setSpaces(int[] spaces) {
        this.spaces = spaces;
    }
}
