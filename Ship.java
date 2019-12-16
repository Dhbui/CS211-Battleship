//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.awt.image.ImageObserver;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javax.imageio.ImageIO;
//public class Ship extends GameObject{
//	private int length;
//    private ArrayList<Integer> spaces;
//    private String type;
//    private Orientation orientation;
//    private BufferedImage boatImage;
//    private ImageObserver obs;
//    private Graphics g;
//    private int x,y;
//    protected Handler handler;
//
//    public Ship(int length, int firstIndex, Orientation state) {
//        this.length = length;
//        spaces = new ArrayList<>(length);
//        if(state == Orientation.HORIZONTAL) {
//            for(int i = 0; i < length; i++) {
//                spaces.add(i + firstIndex);
//                //handler.addObject(this);
//                //render(g, firstIndex, firstIndex+length);
//            }
//        }
//        else {
//            for(int i = 0; i < length; i++) {
//                spaces.add(firstIndex + i * 10);
//              //  handler.addObject(this);
//                //render(g, firstIndex, firstIndex+length);
//            }
//        }
//        orientation = state;
//    }
//
//    public Ship(int length, int firstIndex, Orientation state, String name) {
//        this.length = length;
//        spaces = new ArrayList<>(length);
//        if(state == Orientation.HORIZONTAL) {
//            for(int i = 0; i < length; i++) {
//                spaces.add(i + firstIndex);
//            }
//        }
//        else {
//            for(int i = 0; i < length; i++) {
//                spaces.add(firstIndex + i * 10);
//            }
//        }
//        type = name;
//        orientation = state;
//    }
//
//    public Ship(int x, int y) {
//    	this.x = x;
//    	this.y = y;
//    }
//
//    public int getLength() {
//        return length;
//    }
//
//    public void setLength(int length) {
//        this.length = length;
//    }
//
//    public ArrayList<Integer> getSpaces() {
//        return spaces;
//    }
//
//    public void setSpaces(ArrayList<Integer> spaces) {
//        this.spaces = spaces;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public boolean equals(Object o) {
//        if(!(o instanceof Ship)) {
//            return false;
//        }
//        if(o == this)
//            return true;
//        Ship s = (Ship) o;
//        if(s.getLength() != length)
//            return false;
//        if(s.getType().equals(type))
//            return false;
//        if(s.getSpaces().size() != spaces.size())
//            return false;
//        for(int space : s.getSpaces()) {
//            if(!spaces.contains(space))
//                return false;
//        }
//        return true;
//    }
//
//    public void setOrientation(Orientation orientation) {
//        this.orientation = orientation;
//    }
//
//    public Orientation getOrientation() {
//        return orientation;
//    }
//
//    public void render(Graphics g, int x, int y) {
//
//    	g.setColor(Color.red);
//    	g.fillRect(0, 0, 100, 100);
//    	/*
//    	try {
//			boatImage = ImageIO.read(new File("boat.png"));
//		}
//		catch(IOException ex){
//			System.out.println(ex);
//		}
//		g.drawImage(boatImage, x, y, obs);*/
//    }
//
//	@Override
//	public void render() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void render(Graphics g) {
//		// TODO Auto-generated method stub
//
//	}
//
//
//}
