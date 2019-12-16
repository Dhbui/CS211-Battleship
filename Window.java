//import java.awt.Canvas;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.awt.image.ImageObserver;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFrame;
//
////Adds the frame and all that jazz, starts the game
//public class Window extends Canvas {
//
//	public Window(int width, int height, String name, BattleshipGame game) {
//
//		JFrame f = new JFrame(name);
//
//		f.setPreferredSize(new Dimension(width, height));
//		f.setMaximumSize(new Dimension(width, height));
//		f.setMinimumSize(new Dimension(width, height));
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setResizable(false);
//		f.setLocationRelativeTo(null);
//		f.add(game);
//		f.setVisible(true);
//		game.setupGame();
//		game.runGame();
//	}
//}