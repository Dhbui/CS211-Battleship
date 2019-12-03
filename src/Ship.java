import java.util.ArrayList;

public class Ship {
    private int length;
    private ArrayList<Integer> spaces;
    private String type;

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
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<Integer> getSpaces() {
        return spaces;
    }

    public void setSpaces(ArrayList<Integer> spaces) {
        this.spaces = spaces;
    }
}
