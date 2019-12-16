import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tiles extends GameObject{

	private int index=0;
	static Color c; 
	
	public Tiles(int x, int y) {
		super(x, y);
		index++;
	}
	

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(this.getX(),this.getY(), 40, 40);
	} 

	
	public String toString() {
		return "Tile located at " + this.getX() + this.getY() ; 
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,40,40);
		
	}
}
//whereever you are instantiating the tiles with a for loop give the coordinates 