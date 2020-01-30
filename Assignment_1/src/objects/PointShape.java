package objects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
/**
 * This is a Shape that will be used for the Assignment 1
 * It will be constructed using static methods for each behavior
 * The hape holds its own position as well as a Steer object that holds
 * all relavent parameters
 * @author Hunter Mintz
 *
 */
public class PointShape {
	// declare position variables
	public PVector position;
	//triangle verticies
	PVector t1, t2, t3;
	//relative size of the shape
	public float size;
	//instance of PApplet that is using this shape
	PApplet sketch;
	//the color of the shape
	float color;
	//hold all variables related to steering
	public Steer steer;
	/**
	 * This will construct a new shape with the parameters given 
	 *
	 * @param sketch the instance of PApplet running
	 * @param x location
	 * @param y location
	 * @param size
	 * @param color in grey
	 */
	public PointShape(PApplet sketch, float x, float y, float size, float color) {
		this.sketch = sketch;
		this.size = size;
		this.position = new PVector(x, y);
		t1 = new PVector(position.x, position.y + size / 2);
		t2 = new PVector(position.x, position.y - size / 2);
		t3 = new PVector((float) (position.x + 1.3 * size), position.y);
		this.color = color;
	}

	/**
	 * returns a shape to be used by the Arrive algorithm in the assignment
	 * 
	 * @param sketch the PApplet that will use the shape
	 * @return the new shape
	 */
	public static PointShape Arrive(PApplet sketch) {
		PointShape ret = new PointShape(sketch, sketch.width / 2, sketch.height / 2, 30, 0);
		ret.steer = new Steer(ret.position, (float) Math.PI);
		return ret;
	}

	/**
	 * returns a shape to be used by the first problem in the assignment
	 * 
	 * @param sketch the PApplet that will use the shape
	 * @return the new shape
	 */
	public static PointShape Basic(PApplet sketch) {
		float s = 30;
		PointShape ret = new PointShape(sketch, s / 2, sketch.height - (s / 2), s,0);
		ret.steer = new Steer(ret.position, 0);

		return ret;
	}
	/**
	 * returns a shape to be used by the wander algorithm in the assignment
	 * 
	 * @param sketch the PApplet that will use the shape
	 * @return the new shape
	 */
	public static PointShape Wander(PApplet sketch) {
		PointShape ret = new PointShape(sketch, sketch.width / 2, sketch.height / 2, 30, 0);
		ret.steer = new Steer(ret.position, (float) 1.5707964);
		ret.steer.maxSpeed = 3;
		return ret;
	}
	/**
	 * returns a shape to be used by the flocking wander algorithm in the assignment
	 * if the shape was created by a click then make it white
	 * @param sketch the PApplet that will use the shape
	 * @param m if m = 0 then the shape color is black
	 * 			else color is white
	 * @return the new shape
	 */
	public static PointShape Boid(PApplet sketch, int m) {
		PointShape ret = null;
		
		
		if (m == 0) {
			ret = new PointShape(sketch, sketch.width / 2, sketch.height / 2, 5, 0);
			
		} else {
			ret = new PointShape(sketch, sketch.mouseX, sketch.mouseY, 5, 255);
		}
		ret.steer = new Steer(ret.position, 2);
		ret.steer.setVelocity(PVector.random2D());
		ret.steer.maxSpeed = 2;
		ret.steer.setSpeed(2);
		return ret;
	}
	/**
	 * this draws the shape on the current PApplet by translating the origin to the center of the shape and then 
	 * rotate the matrix around that point before drawing in the shapes
	 */
	public void draw() {		
		float theta = steer.velocity.heading() ;
		 sketch.fill(color);
		 sketch.stroke(color);
		 sketch.pushMatrix();
		 sketch.translate(position.x, position.y);
		 sketch.rotate(theta);
		 sketch.beginShape(PConstants.TRIANGLES);
		 sketch.vertex(0, size / 2);
		 sketch.vertex(0, -size / 2);
		 sketch.vertex((float) (1.3 * size), 0);
		 sketch.endShape();
		 sketch.ellipse(0, 0, size, size);
		 sketch.popMatrix();
		
	}
	/**
	 * this updates the position of each position vector by adding the current velocity to it
	 * and then updates the 
	 */
	public void update() {
		steer.update();
		if (steer.velocity != null) {
			this.position.add(PVector.mult(steer.velocity, steer.speed));
			this.t1.add(PVector.mult(steer.velocity, steer.speed));
			this.t2.add(PVector.mult(steer.velocity, steer.speed));
			this.t3.add(PVector.mult(steer.velocity, steer.speed));
		}
		
	}


}
