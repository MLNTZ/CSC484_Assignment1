package sketches;


import objects.PointShape;
import processing.core.PApplet;
import processing.core.PVector;
/**
 * this class implements the arrive algorithm for the second problem in Assignments one
 * this class acts as a world that controls the movement of the objects inside of it
 * the class will update the accelerations of the shape to get it to move toward the desired target
 * target will be set to where a mouse click is
 * @author Hunter Mintz
 *
 */
public class arrive_steering extends PApplet{
	//size of world
	static final int worldw = 1280;
	static final int worldh = 720;
	//the shape in the animation
	static PointShape shape;
	//the target location
	static PVector target;
	//radius to start slowing
	static float slowRadius = 300;
	// decimal to scale the speed by
	static float timeToTarget = (float) .05;
	// decimal to scale the acceleration by
	static float maxAcceleration = (float) .7;
	//the max speed of the shape
	static float maxSpeed = 7;
	/**
	 * main method sets up the sketch and then runs it
	 * @param args
	 */
	public static void main(String[] args){
		String[] processingArgs = {"arrive_steering"};
		arrive_steering world = new arrive_steering();
		PApplet.runSketch(processingArgs, world);
		
	}
	/**
	 * helper method to print out the string for debug
	 * @param str
	 */
	public static void print(String str) {
		System.out.println(str);
	}
	/**
	 * Method to control steering toward target
	 * this method will cause the shape to move toward the target by changing the shapes linear acceleration
	 * to point the velocity toward the target. as the shape move towards the target it will start to slow down
	 * so that it will stop at the target
	 */
	public static void arriveSteer() {
		float targetSpeed = 0;
		PVector targetVelocity;
		PVector direction = PVector.sub(target, shape.position);
		float dist = direction.mag();
		direction.div(dist);
		
		if (dist > slowRadius) {
			targetSpeed = maxSpeed;
		} else {
			targetSpeed = maxSpeed * dist / slowRadius;
		}
		targetVelocity = new PVector(direction.x, direction.y);
		targetVelocity.setMag(targetSpeed);
		
		PVector linear = targetVelocity.sub(shape.steer.getVelocity());
		
		linear.mult(timeToTarget);
		
		
		if (linear.mag() > maxAcceleration) {
			linear.setMag(maxAcceleration);
		}
		shape.steer.setAngular(0);
		shape.steer.setSpeed(targetSpeed);
		shape.steer.setLinear(linear);
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
		shape = PointShape.Arrive(this);
	}
	/**
	 * when the mouse is pressed, assign a new target at mouse
	 */
	public void mousePressed() {
		target = new PVector(mouseX, mouseY);
		shape.steer.clear();
		
		
	}
	/**
	 * do this each frame:
	 * if we have target then update linear acc to move toward
	 * update the shape location
	 * draw the shape
	 */
	public void draw() {
		background(120);
		if (target != null) {
			arriveSteer();
		}
		shape.update();
		
		shape.draw();		
	}

}

