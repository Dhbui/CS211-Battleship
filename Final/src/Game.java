import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game extends Canvas implements Runnable, ActionListener, MouseListener{

	//LOTS OF FIELDS
	private static final int WIDTH = 1400, HEIGHT =700; 
	private Thread thread; 
	private boolean running = false; 
	protected static boolean setup = false; 
	private boolean gameInProgress = false; 
	protected static boolean playerTurn = true; 
	protected boolean clicked = false; 
	//where the ships are
    private HashSet<Integer> shipSpaces1;
    //where you can fire
    private HashSet<Integer> availableMoves1;
    private HashSet<Integer> shipSpaces2;
    private HashSet<Integer> availableMoves2;
	
    
    //Tiles sets
    ArrayList<Rectangle> yourTiles = new ArrayList<>();
    ArrayList<Rectangle> opponentsTiles = new ArrayList<>();
    
    int temp; 
	Graphics g;
	Graphics ged; 
	private Handler handler; 
	private BufferedImage hitMissImage;
	private BufferedImage boatImage; 
	private ImageObserver obs;  
	
	protected MouseListener m;
	int shipCount =17; 
	private int count =0; 
	protected final String[] NAMES = {"Patrol Boat", "Submarine", "Destroyer", "Battleship", "Carrier"};
	protected final int[] SHIPSIZES = {2,3,3,4,5};
	protected HashMap<Integer, String> shipNames;
	
	protected Prompt p; 


	//Game constructor instantiates adds keyListener, creates window, and adds the player
	public Game() {
		handler = new Handler();
		this.addMouseListener(m);
		new Window(WIDTH, HEIGHT, "Battleship", this);
		
		shipSpaces1 = new HashSet<>();
		availableMoves1 = new HashSet<>();
		shipSpaces2 = new HashSet<>();
		availableMoves2 = new HashSet<>();
		//p = new Prompt(); 
		//setting tiles coordinates
		for(int i=100; i<500; i+=40) {
			for(int j=200; j<=560; j+=40) {
				yourTiles.add(new Rectangle(i,j,40,40));
			}
		}
		
		for(int i=750; i<1150; i+=40) {
			for(int j=200; j<=575; j+=40) {
				opponentsTiles.add(new Rectangle(i,j,40,40));
			}
		}
		
		addMouseListener(this);
		
	}
		
	//calls playSound() so the anthem starts when the game is run, starts thread
	public synchronized void start() {
		//threads allow the program to do all the different things at the same time
		thread = new Thread(this); 
		thread.start(); 
		//running is set to true, this will be checked in the game loop
		running =true; 
	}
	//stops running the program, notice how running is set to false, this will be checked in the game loop
	public synchronized void stop() {
		try {
			thread.join();
			running = false; 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//handler calls its tick which calls the tick method off all gameObjects
	private void tick() {
		handler.tick();
	}
	
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return; 
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		//BOARD
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 1400, 700);
		
		g.setColor(Color.CYAN);
		g.fillRect(100,200,1400,700);
		
		g.setColor(Color.BLUE);
		g.fillRect(500, 0, 250, 900);
		g.setColor(Color.CYAN);
		g.fillRect(100, 200, 400,400);
		
		g.setColor(Color.BLUE);
		g.fillRect(1150, 0, 250, 700);
		
		g.fillRect(0, 600, 1400, 100);
		
		g.setColor(Color.GREEN);
		Font f = new Font("Times New Roman", Font.BOLD, 50);
		g.setFont(f);
		g.drawString("Your board", 150, 75);
		g.drawString("Opponent's board", 770, 75);
		g.setColor(Color.YELLOW);
		
		//labels for the x and y axis
		g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
		
		//numbers for the PLAYERS board
		g.drawString("1  2   3   4   5   6   7   8  9  10", 125, 175);
		//numbers for the OPPONENTS board
		g.drawString("1  2   3   4   5   6   7   8  9  10", 775, 175);
		
		
		g.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		
		//Letters for the PLAYERS board
		g.drawString("A", 50, 225);
		g.drawString("B", 50, 265);
		g.drawString("C", 50, 305);
		g.drawString("D", 50, 345);
		g.drawString("E", 50, 385);
		g.drawString("F", 50, 425);
		g.drawString("G", 50, 465);
		g.drawString("H", 50, 505);
		g.drawString("I", 50, 545);
		g.drawString("J",50, 585);
		
		//Letters for the OPPONENTS board
		g.drawString("A", 700, 225);
		g.drawString("B", 700, 265);
		g.drawString("C", 700, 305);
		g.drawString("D", 700, 345);
		g.drawString("E", 700, 385);
		g.drawString("F", 700, 425);
		g.drawString("G", 700, 465);
		g.drawString("H", 700, 505);
		g.drawString("I", 700, 545);
		g.drawString("J", 700, 585);
		

		for(int i=100; i<500; i+=40) {
			g.setColor(Color.BLACK);
			for(int j=200; j<=560; j+=40) {
				g.drawRect(i, j, 40, 40);
			}
		}
		
		for(int i=750; i<1150; i+=40) {
			g.setColor(Color.BLACK);
			for(int j=200; j<=575; j+=40) {
				g.drawRect(i, j, 40, 40);
			}
		}
		
		g.setColor(Color.YELLOW);
		g.drawString("Place your boats", 500,125);
		
		handler.render(g);
		g.dispose();
		bs.show();
	
	}

	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0; 
		double ns = 1000000000 / amountOfTicks; 
		double delta = 0; 
		long timer = System.currentTimeMillis();
		int frames = 0 ; 
		
		while(running) { 
			long now = System.nanoTime();
			delta += (now - lastTime)/ ns; 
			lastTime =now; 
			
			//While the game is running the following lines of code will constantly call the tick(), render(), and collision() methods 
			while(delta >= 1) {
				tick();
				delta--; 
			}
			
			//if the running is true, the images and graphics will render
			if(running)
				render();
			frames++; 
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000; 
				frames = 0; 
			
			}
			

		}
		
		stop(); 
	}
	
	//main method, instance of game
	public static void main(String args[]) {
		new Game();
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0){
		// TODO Auto-generated method stub
		
		
	}
	
	//This task class is used to spawn the birds
	public class Task extends TimerTask{
		
		public void run() {}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle r=null; 
		Point p = e.getPoint(); 
		setup = true; 
		if(checkSetup()) {
			for(int i=0; i<100; i++) {
					if(yourTiles.get(i).getX() <= p.getX() && yourTiles.get(i).getX()+40 >= p.getX() && yourTiles.get(i).getY() <= p.getY() && yourTiles.get(i).getY()+40 >= p.getY() ) {
						r = yourTiles.get(i);
					}
					/*
					else if(opponentsTiles.get(i).getX() <= p.getX() && opponentsTiles.get(i).getX()+40 >= p.getX() && opponentsTiles.get(i).getY() <= p.getY() && opponentsTiles.get(i).getY()+40 >= p.getY()){
						r = opponentsTiles.get(i);
					}*/
			}
			Boat b = new Boat((int)r.getX()+1,(int) r.getY()+1);
			handler.addObject(b);
			shipCount--; 
		}/*
		else if() {
			
		}*/
		
	}

	public void renderBoat(Graphics g, int x, int y) {
		try {
			boatImage = ImageIO.read(new File("boatShip.png"));
			
		}
		catch(IOException ex){
			System.out.println(ex);
		}
		catch(NullPointerException ex) {
			System.out.println(ex);
		}
		g.drawImage(boatImage, x, y, obs);
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkSetup() {
		return shipCount>0;
	}
	
	/*
	public boolean checkGameRunning() {
		return yourShips>0 || opponentsShips>0; 
	}*/
	
}