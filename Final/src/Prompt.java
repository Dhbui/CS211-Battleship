import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
public class Prompt extends GameObject{
	
	Font f = new Font("Times New Roman", Font.BOLD, 25);
	
	public Prompt(int x, int y) {
		super(x,y);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.setFont(f);
		g.drawString("Place your boats_: ", 400, 200);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
}
