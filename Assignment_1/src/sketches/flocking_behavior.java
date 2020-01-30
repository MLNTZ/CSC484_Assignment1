package sketches;

import java.util.ArrayList;

import objects.PointShape;
import processing.core.PApplet;
import processing.core.PVector;
/**
 * this class implements the Reynolds flocking behavior
 * this class acts as a world that controls all of the objects inside of it
 * the flocking is based on 5 parameter:
 * Separation, Alignment, Cohesion, Wander, and Key
 * all of these parameters are weighed into the linear acceleration of 
 * any given shape to cause them to flock in a pretty way
 * the user can influence the direction of the flock using WASD
 * @author Hunter Mintz
 *
 */
public class flocking_behavior extends PApplet {
	//number of shapes to start with
	static final int STARTING_SHAPES =50;
	//world size
	static final int worldw = 1280;
	static final int worldh = 720;
	//list of all shapes in the flock
	static ArrayList<PointShape> flock;
	//max acceleration
	float maxAccel = (float) .7;
	//list of weather any key is pressed
	public static boolean[] keys = new boolean[128];
 	//weight of separation parameter
	static final float SEP_W = (float) 2.3;
	//weight of alignment parameter
	static final float ALI_W = (float) .8;
	//weight of cohesion parameter
	static final float COH_W = (float) 1.2;
	//weight of wander parameter
	static final float WAN_W = (float) .08;
	//weight of key parameter
	static final float KEY_W = (float) .07;
	//minimum separation goal
	static final float MIN_SEP = 90; 
	//minimum distance to be a local
	static final float NBR_SEP = 60;
	/**
	 * main method sets up sketch and runs it
	 * @param args
	 */
	public static void main(String[] args) {
		String[] processingArgs = { "flocking_behavior" };
		flocking_behavior world = new flocking_behavior();
		PApplet.runSketch(processingArgs, world);
		flock = new ArrayList<>();

	}
	/**
	 * setup to run before first draw, set frame rate and background color and 
	 * initialize list of boids
	 */
	public void setup() {
		frameRate(60);
		background(120);
		for (int i = 0; i < STARTING_SHAPES; i++) {
			flock.add(PointShape.Boid(this, 0));
		}
	}
	/**
	 * settings to run before first draw, set world size 
	 */
	public void settings() {
		size(worldw, worldh);
	}
	/**
	 * for each frame
	 * go through each shape in the flock and apply linear acceleration
	 * then draw each shape
	 */
	public void draw() {
		background(128);

		for (PointShape x : flock) {
			wrap(x);
			flock(x);
			
			x.update();
			x.draw();

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
	 * when mouse is clicked add a new white boid
	 */
	public void mousePressed() {
		flock.add(PointShape.Boid(this, 1));
	}
	/**
	 * when a key is pressed mark it in key array
	 */
	public void keyPressed() {
		if (key >= 128) {
			return;
		}
		keys[key] = true; 
	}
	/**
	 * when key is released unmark it
	 */
	public void keyReleased() {
		if (key >= 128) {
			return;
		}
		keys[key] = false;
	}
	/**
	 * this method sets the linear acc to cause the flock behavior
	 * creates a list of integers that represents indexes in the list of boids for neighbors
	 * start by getting all of the parameters:
	 * wander, avoid, align, cohesion, keyDir
	 * and then add them to the linear acceleration
	 * limit the acceleration
	 * @param x
	 */
	public void flock(PointShape x) {
		int[] locals = findLocals(x);
		PVector wander = new PVector(random(2) - 1, random(2) -1).mult(WAN_W);
		PVector avoid = getAvoid(x, locals).mult(SEP_W);
		PVector allign = getDir(x, locals).mult(ALI_W);
		PVector cohesion = getCoh(x, locals).mult(COH_W);
		PVector keyDir = getKey(x, locals).mult(KEY_W);

		x.steer.linear.add(avoid);
		x.steer.linear.add(allign);
		x.steer.linear.add(wander);
		x.steer.linear.add(cohesion);
		x.steer.linear.add(keyDir);
		x.steer.linear.limit(maxAccel);

	}
	/**
	 * Insert the index of each local boid into the return array
	 * if the distance from x to any boid is smaller then neighbor 
	 * separation distance then it is local
	 * @param x
	 * @return
	 */
	private int[] findLocals(PointShape x) {
		int[] ret = new int[flock.size()];
		int locals = 0;
		for (int i = 0; i < flock.size(); i++) {
			PointShape test = flock.get(i);
			if (test == x) {
				continue;
			}
			if (abs(test.position.x - x.position.x) < NBR_SEP && abs(test.position.y - x.position.y) < NBR_SEP) {
				ret[locals] = i;
				locals++;
			}
		}
		return ret;
	}
	/**
	 * returns the cohesion acceleration by getting the average position of each boid
	 * the cohesion acceleration pushes the velocity toward the average position of the boids
	 * @param x
	 * @param locals
	 * @return cohesion
	 */
	private PVector getCoh(PointShape x, int[] locals) {
		PVector acc = new PVector(0, 0);
		float n = 0;
		for (int i : locals) {
			PointShape cur = flock.get(i);
			float dist = PVector.dist(x.position, cur.position);
			if (dist > 0 && dist < NBR_SEP) {
				acc.add(cur.position);
				n += 1;
			}
		}
		if (n > 0) {
			acc.div(n);
			PVector targ = PVector.sub(acc, x.position);
			return targ.setMag((float) .06);
		}
		return new PVector(0, 0);
	}
	/**
	 * returns the alignment acceleration by getting the average direction of all the boids
	 * Alignment acceleration pushes velocity toward the average direction of the boids
	 * @param x
	 * @param locals
	 * @return alignment
	 */
	private PVector getDir(PointShape x, int[] locals) {
		PVector dir = new PVector(0, 0);
		float n = 0;

		for (int i : locals) {
			PointShape cur = flock.get(i);
			float dist = PVector.dist(x.position, cur.position);
			if (dist > 0 && dist < NBR_SEP) {
				PVector velo = cur.steer.velocity.copy();
				velo.normalize();
				velo.div(dist);
				dir.add(velo);
				n++;
			}
		}
		if (n > 0) {
			
		}

		return dir;
	}
	/**
	 * this returns the separation acceleration, by getting the average velocity away 
	 * from all neighboring boids.
	 * the separation acceleration pushes the velocity away from near boids
	 * @param x
	 * @param locals
	 * @return
	 */
	private PVector getAvoid(PointShape x, int[] locals) {
		PVector acc = new PVector(0, 0);
		float n = 0;
		for (int i : locals) {
			PointShape cur = flock.get(i);
			float dist = PVector.dist(x.position, cur.position);
			if (dist > 0 && dist < MIN_SEP) {
				PVector dir = PVector.sub(x.position, cur.position);
				dir.normalize();
				dir.div(dist);
				acc.add(dir);
				n += 1;
			}
		}
		if (n > 0) {
		
		}


		
		return acc;
	}
	/**
	 * this gets the key acceleration by checking which keys are pressed and getting the 
	 * acceleration in the direction of the keys
	 * key acceleration pushed velocity toward the direction of the key
	 * @param x
	 * @param locals
	 * @return keyAcceleration
	 */
	private PVector getKey(PointShape x, int[] locals) {
		PVector acc = new PVector(0, 0);
		
		if(keys['w']) {
			acc.add(new PVector(0, -1));
		}
		if(keys['d']) {
			acc.add(new PVector(1, 0));
		}
		if(keys['s']) {
			acc.add(new PVector(0, 1));
		}
		if(keys['a']) {
			acc.add(new PVector(-1, 0));
		}
		
		return acc.normalize();
		
	}

}
