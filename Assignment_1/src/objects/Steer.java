package objects;

import processing.core.PVector;

/**
 * defines all of the variables for the steering and movement of any shape
 * allows the shape to shange velocity and orientation
 * @author Hunter Mintz
 *
 */
public class Steer {

	// kinematic variables
	PVector position;
	public PVector velocity;
	public float orientation;
	float rotation;

	// steering variables
	public PVector linear;
	float angular;
	//speed variables
	public float speed;
	public float maxSpeed;
	/**
	 * this will create a new instance of Steer with the given position and orientation but no speed
	 * this will allow the PApplet to draw the shape facing in the direction of orientation
	 * @param position
	 * @param orientation
	 */
	public Steer(PVector position, float orientation) {
		this.position = position;
		this.orientation = orientation;
		this.rotation = 0;
		this.velocity = getVeloFromOrient(orientation);
		this.maxSpeed = 20;
		this.speed = 0;
		this.linear = new PVector(0, 0);
		this.angular = 0;
	}
	/**
	 * this will aplly the standard update to all the relevant variables 
	 * it will add linear acceleration to the velocity
	 * then add velocity to position
	 * then put the orientation of the shape in direction of movement
	 */
	public void update() {
		position.add(PVector.mult(velocity, speed));
		if (linear != null) {
			velocity.add(linear);
			linear.mult(0);
		}
		if (linear == null && angular != 0) {
			orientation += angular;
			velocity = getVeloFromOrient(orientation);
		}

		orientation = getOrientFromVelo(velocity);
		if (PVector.mult(velocity, speed).mag() > maxSpeed) {
			velocity.setMag(1);
		}
		
	}
	/**
	 * this will return a radian orientation by getting the direction of the given velocity vetor
	 * @param velo
	 * @return
	 */
	public static float getOrientFromVelo(PVector velo) {
		return (float) Math.atan2(velo.y, velo.x);
	}
	/**
	 * this will return a unit velocity vector in the direction of the given orientation
	 * @param orient
	 * @return
	 */
	public static PVector getVeloFromOrient(float orient) {
		return PVector.fromAngle(orient);
	}
	/**
	 * returns the position vector
	 * @return positio
	 */
	public PVector getPosition() {
		return position;
	}
	/**
	 * sets the position vector
	 * @param position
	 */
	public void setPosition(PVector position) {
		this.position = position;
	}
	/**
	 * returns the velocity vector
	 * @return velocity
	 */
	public PVector getVelocity() {
		if (velocity == null) {
			return new PVector(0, 0);
		}
		return velocity;
	}
	/**
	 * sets the velocity vector
	 * @param position
	 */
	public void setVelocity(PVector velocity) {
		this.velocity = velocity;
	}
	/**
	 * returns the position vector
	 * @return orientation
	 */
	public float getOrientation() {
		return orientation;
	}
	/**
	 * sets the orientation
	 * @param orientation
	 */
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}
	/**
	 * returns the rotation
	 * @return rotation
	 */
	public float getRotation() {
		return rotation;
	}
	/**
	 * sets the rotation
	 * @param rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	/**
	 * returns the linear acc
	 * @return linear
	 */
	public PVector getLinear() {
		return linear;
	}
	/**
	 * sets linear acc
	 * @param linear
	 */
	public void setLinear(PVector linear) {
		this.linear = linear;
	}
	/**
	 * returns angular acc
	 * @return angular
	 */
	public float getAngular() {
		return angular;
	}
	/**
	 * sets angular acc
	 * @param angular
	 */
	public void setAngular(float angular) {
		this.angular = angular;
	}
	/**
	 * returns the speed
	 * @return speed
	 */
	public float getSpeed() {
		return speed;
	}
	/**
	 * sets the speed
	 * @param speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	/**
	 * returns max speed
	 * @return maxSpeed
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}
	/**
	 * stes max speed
	 * @param maxSpeed
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	/**
	 * clears all accelerations and stops movement, but keep direction
	 */
	public void clear() {
		linear = null;
		speed = 0;
		rotation = 0;
		angular = 0;
		velocity = getVeloFromOrient(orientation);
	}

}
