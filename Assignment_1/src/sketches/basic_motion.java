package sketches;

import java.util.ArrayList;

import objects.PointShape;
import processing.core.PApplet;
import processing.core.PVector;
/**
 * this class implements the algorithm for the first problem in assignment 1
 * This class acts as a world that controls the shape. This class will move the shape
 * around the perimeter of the screen while leaving breadcrumbs at every other frame
 * @author Hunter MIntz
 *
 */
public class basic_motion extends PApplet{
	//word size
	static final int worldw = 1280;
	static final int worldh = 720;
	//state to guide which part of screen we are on
	int state = 0;
	//number of crumbs
	int crumbCount;
	//array  list of positions to put crumbs
	ArrayList<PVector> crumbs;
	//length of the shape
	float shapeLen;
	//the shape in the drawing
	public PointShape shape;
	/**
	 * main method sets up sketch and runs it
	 * @param args
	 */
	public static void main(String[] args){
		String[] processingArgs = {"basic_motion"};
		basic_motion world = new basic_motion();
		PApplet.runSketch(processingArgs, world);
		
	}
	/**
	 * debug method to print str
	 * @param str
	 */
	public static void print(String str) {
		System.out.println(str);
	}
	/**
	 * this will run the animation for the shape
	 * using a state machine the shape will move along the perimeter of the screen
	 * when it reaches an edge it will change state, turn , and continue moving in the 
	 * new direction, until it reaches the initial position again
	 */
	public void basic() {
		if (state == 0) {
			shape.steer.setVelocity(new PVector(1, 0));
			shape.steer.setSpeed(5);
			float dist = worldw - (shape.position.x - shape.size / 2);
			if (dist <= shape.size) {
				shape.steer.setVelocity(new PVector(0, -1));
				state = 1;
				return;
			}
			return;
		} 
		if (state == 1) {
			float dist = shape.position.y + shape.size / 2;
			if (dist <= shape.size) {
				shape.steer.setVelocity(new PVector(-1, 0));
				state = 2;
				return;
			}
			return;
		}
		if (state == 2) {
			float dist = shape.position.x + shape.size / 2;
			if (dist <= shape.size) {
				shape.steer.setVelocity(new PVector(0, 1));
				state = 3;
				return;
			}
			return;
		}
		if (state == 3) {
			float dist = worldh - (shape.position.y - shape.size / 2);
			if (dist <= shape.size) {
				shape.steer.setVelocity(new PVector(1, 0));
				state = 4;
				shape.steer.setSpeed(0);
				return;
			}
			return;
		}
		
	}
	/**
	 * this method can draw a new crumb and then draws the current crumbs
	 * @param b if true add new crumb
	 * 			else dont add new crumb
	 */
	private void leaveCrumb(boolean b) {
		if (crumbs == null) {
			crumbs = new ArrayList<>();
		}
		if (b) {
			
			crumbs.add(new PVector(shape.position.x, shape.position.y));
		}
		for (int i = 0; i < crumbs.size(); i++) {
			fill(255);
			square(crumbs.get(i).x, crumbs.get(i).y, shape.size / 7);
		}
		
	}
	/**
	 * setup to run before first draw, set frame rate and background color
	 */
	public void setup() {
		frameRate(60);
		background(120);
	}
	/**
	 * settings to run before first draw, set world size and initialize the shape
	 */
	public void settings(){
		size(worldw, worldh);
		shape = PointShape.Basic(this);
		shapeLen = (float) (shape.size + (1.3 * shape.size));
	}
	/**
	 * do this each frame:
	 * if frame count is even leave new crumb else don't
	 * update the shape velocity for the movement
	 * draw the shape
	 */
	public void draw() {
		background(120);
		if (this.frameCount % 2 == 0) {
			leaveCrumb(true);
		} else {
			leaveCrumb(false);
		}
		basic();
		shape.update();
		
		shape.draw();		
	}

	
	
}
