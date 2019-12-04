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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if(!o.getClass().equals(this.getClass())) {
            return false;
        }
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
}
