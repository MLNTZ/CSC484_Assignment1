package sketches;

import java.util.Random;
import java.util.Scanner;

import objects.PointShape;
import processing.core.PApplet;
import processing.core.PVector;
/**
 * this class implements 2 different methods of random movement
 * this class acts as a world that controls the shape in it
 * depending on the choice the shape will move around the screen randomly
 * either by randomly turning or by arriving at random targets
 * @author Hunter Mintz
 *
 */
public class wander_steering extends PApplet {
	//world size
	static final int worldw = 1280;
	static final int worldh = 720;
	//length of shape
	float shapeLen;
	//center of movement circle
	PVector circleCenter;
	//max change in angle
	float angleDelta = (float) .3;
	//target to wander to
	PVector wanderTarget;
	//radius to start slow
	float slowRadius = 300;
	//radius to satisfy arrive
	float arriveRadius = 20;
	//angle to wander by
	float wanderAngle;
	//arrived at target?
	boolean arrived = true;
	//the max speed
	float maxSpeed = 5;
	//the max acceleration
	float maxAcceleration = (float) .1;
	//scale to multiply speed by
	float timeToTarget = (float) .05;
	//shape that is moving
	public PointShape shape;
	//random number generator
	public Random r ;
	//weather we use arrive algorithm or not
	static boolean arriveAlgorithm = false;
	/**
	 * will first get the desired algorithm from user:
	 * if user enters 2 then use the arrive algorithm
	 * else use direction algorithm
	 * then set up sketch and tun
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		print("Would you like to use algorithm 1 or 2: ");
		if (in.next().contentEquals("2")) {
			arriveAlgorithm = true;
		}
		in.close();
		String[] processingArgs = { "wander_steering" };
		wander_steering world = new wander_steering();
		PApplet.runSketch(processingArgs, world);

	}
	/**
	 * debug method to print string
	 * @param str
	 */
	public static void print(String str) {
		System.out.println(str);
	}
	/**
	 * this implements both algorithms, if arriveAlgorithm is set to false
	 * then the algorithm will pick a random vector to add to the linear acceleration
	 * of the shape so that it turns
	 * if arriveAlgorithm is true then pick a random target on the screen and then set the linear 
	 * acceleration of the shape to turn velocity towards target until it arrives
	 */
	public void wander() {
		if (!arriveAlgorithm) {
			PVector wander = new PVector(random(2) - 1, random(2) - 1).mult(maxAcceleration);

			shape.steer.linear.add(wander);
			shape.steer.linear.limit(maxAcceleration);
		} else {
			if (arrived == true) {
				getRandTarget();
				arrived = false;
			}
			float targetSpeed = 0;
			PVector targetVelocity;
			PVector direction = PVector.sub(wanderTarget, shape.position);
			float dist = direction.mag();
			if (dist < arriveRadius) {
				wanderTarget = null;
				arrived = true;
			}
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
	}
	/**
	 * this wraps the shape around the screen by setting the coordinates to oposite side if 
	 * they get too large
	 * @param x
	 */
	private void wrap(PointShape x) {
		x.position.x = (x.position.x + worldw) % worldw;
		x.position.y = (x.position.y + worldh) % worldh;
	}
	/**
	 * assigns a new target randomly
	 */
	public void getRandTarget() {
		Random r = new Random();
		int x = r.nextInt(worldw );
		int y = r.nextInt(worldh);
		wanderTarget = new PVector(x, y);
		arrived = false;
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
	public void settings() {
		size(worldw, worldh);
		shape = PointShape.Wander(this);
		shapeLen = (float) (shape.size + (1.3 * shape.size));
	}
	/**
	 * do each frame:
	 * set speed to 1
	 * update linear acc to wander
	 * wrap the shape around the screen
	 * draw the shape
	 */
	public void draw() {
		shape.steer.setSpeed(1);
		background(120);
		wander();
		wrap(shape);
		shape.update();
		shape.draw();
	}

}
