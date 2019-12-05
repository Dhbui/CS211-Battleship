import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SinglePlayerHandler {
	
	private static BufferedImage winnerImage; 
	private static ImageObserver obs; 
	
	public static void main(String[] args) {
		Graphics g = null; 
		BattleshipGame singlePlayer = new BattleshipGame("Single");
		Window w = new Window(600,600, "Battleship", singlePlayer);
		singlePlayer.setupGame();
		singlePlayer.runGame();
		
		try {
			winnerImage = ImageIO.read(new File("Loser.png"));
		}
		catch(IOException | NullPointerException ex){
			System.out.println(ex);
		} 

		g.drawImage(winnerImage, 0, 0, obs);
	}
}
