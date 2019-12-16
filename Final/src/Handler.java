import java.awt.Graphics;
import java.util.LinkedList;

//A LIST OF OBJECT ELEMENTS USED IN GAME CLASS
//adds, renders, ticks, and removes objects in the Game class
public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();

	//goes through linkedList and calls tick for every object
	public void tick() {			
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);			
				
			tempObject.tick();
		}

	}
	
	//goes through linkedList and renders each object
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);			
				
			tempObject.render(g);
		}
	}
	
	//adds object to linkedList
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	//removes object from linkedList
	public void removeObject(GameObject object) 	{
		this.object.remove(object); 
	}
	
}