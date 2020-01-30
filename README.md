Hunter Mintz (hmintz)
CSC 484 Spring 2020

# Assignment 1

The code for each algorithm is located in the given file in the sketches package. Each algorithm file acts as a world that
controls any objects that are placed in it. The world will update the velocity and linear acceleration of any shape to cause it 
to move at each frame.

## How to run from .zip:
	1) export the Assignment_1 folder out of the .zip file
	2) Open eclipse
	3) Right click in Package Explorer and select "import..."
	4) Select "general" > "existing projects into workspace"
	5) in the "select root directory" field brows to the Assignment_1 folder location and "select folder"
	6) Make sure you select the project "Assignment_1"
	7) Now the project is imported: to run any algorithm, navigate to Assignment_1>src>sketches and run one of the java files
	
## How to run from GitHub:
	1) Clone the repository : https://github.ncsu.edu/hmintz/CSC484_Assignment1.git
	2) Import the eclipse project from the repository
	3) Now the project is imported: to run any algorithm, navigate to Assignment_1>src>sketches and run one of the java files
	
	
	

## Steer Class
### Steer.java
	This file defines the data structure for Steering variable. It holds velocity, orientation, linear, and angular acceleration
	and has methods for getting orientation from a velocity and vice versa. Steer will update the velocity by adding the linear or angular 
	acceleration. The algorithms can modify the linear acceleration causing the shape that holds the Steer to move
	
	
## Shape Class
### CircleShape.java
	This file defines the shape that will be used for the project. The shape holds a class of Steer to control its steering and 
	contains methods for updating the position and drawing the shape. The shape is a circle with a triangle coming out of it, and it 
	will always be oriented in the direction of travel.
	
	
	
## Basic Motion
### basic_motion.java: 
	Run: right click on basic_motion.java and runs as java application
	
	This algorithm causes the shape to move around the perimeter of the screen one time and leave breadcrumbs at every other frame
	the shape will turn when it reaches the edge of the screen and  change direction0 degrees.
	
	
	
	
## Arrive Steering
### arrive_steering.java:
	Run: right click on arrive_steering.java and runs as java application
	
	this algorithm causes the shape to arrive at the location of the latest mouse click. The algorithm does this by calculating the
	vector pointing towards the target and changing the velocity of the shape to point towards the target. It will also slow the shape down 
	as it gets close to the target so that it does not overshoot.
	
	
	
	
## Wander Steering
### wander_steering.jave:
	Run: 
     1). right click on wander_steering.java and runs as java application
		 2). input "1" or "2" into console to chose algorithm
		 
	This algorithm contains 2 different method of random wandering for the shape on the screen.
		Method 1: 
			The shape move by randomly selecting a vector for linear acceleration and adding it to the shape's velocity
			this causes the shape to move in random directions or sometimes in a seemingly straight line.
		Method 2:
			The shape uses an arrive algorithm to target randomly selected locations on the screen. The arrive target changes
			every time the shape gets near the location. this causes the shape to turn slower and change directions less.
			
	This algorithm avoids edge collision by wrapping the shape around the edges of the screen when it crosses an edge.
	
	
	
## Flocking Behaviour
### flocking_behaviour.java:
	Run: 
     1). right click on flocking_behaviour.java and runs as java application
		 2). use WASD to influence flock direction
		 3). click mouse to add boid
		 
	This algorithm causes a group of boids to travel as a flock. The  flocking algorithm is based on 5 parameters:
 	 	 	1). Separation : moving away from nearby boids
 	 	 	2). Alignment : matching nearby boids direction
 	 	 	3). Cohesion : matching nearby boids position
 	 	 	4). Wander : random movement
 	 	 	5). Key : user input to control direction
 	The algorithm blends these behaviors as the linear acceleration of each shape. Each of the parameters becomes a vector
 	that is scaled by the weight of the parameter and added to the linear acceleration before it is scaled to the maxAcceleration.
 	this causes every boid to move in a direction that creates a flock.
 	
 	The user can influence this direction by using WASD to turn the flock.
 	The user can add more boids by clicking on the screen
