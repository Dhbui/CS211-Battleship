
import java.awt.Graphics;
import java.awt.Rectangle;

//ABSTRACT CLASS WHICH BOATS ARE DERIVED FROM
public abstract class GameObject {
	
	protected int x;
	protected int y; 
	protected int velX, velY; 
	
	public GameObject(int a, int b) {
		x = a;
		y = b; 
	}
	public GameObject() {
		
	}
	//public abstract void tick();
	public abstract void render(Graphics g, int x, int y);
	public abstract void render();
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
