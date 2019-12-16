import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Boat extends GameObject{

	
	protected BufferedImage boatImage;
	protected ImageObserver obs;
	
	public Boat(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		try {
			boatImage = ImageIO.read(new File("boatShip.png"));
			
		}
		catch(IOException ex){
			System.out.println(ex);
		}
		catch(NullPointerException ex) {
			System.out.println(ex);
		}
		g.drawImage(boatImage, this.getX(), this.getY(), obs);
		
	}

}
