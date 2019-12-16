import java.awt.Graphics;
import java.awt.Rectangle;

//ABSTRACT CLASS WHICH BULLET, BIRDIE, AND PLAYER ARE DERIVED FROM
public abstract class GameObject {
	
	protected int x;
	protected int y; 
	protected int velX, velY; 
	
	public GameObject(int a, int b) {
		x = a;
		y = b; 
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	//GETTERS
	public int getX() {
		return x; 
	}
	
	public int getY() {
		return y; 
	}
	
	//used for checking collision
	public Rectangle getBounds() {
		return new Rectangle(x,y, 25,25);
		
	}
	
	
}
