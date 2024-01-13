package monkeygame;

/*
 *Monkey Game Monkey Class
 * Author: Ahmed Kadry
 * Date: May 16, 2022
 * Description: This is the class for the coconut and for it to be randomly placed
 */


import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Monkey {

	//declare variables
	double x = 100;
	double y = 100;
	int speed = 4;
	double dx = 0;
	double dy = 0;
	int lives = 3;
	
	//variables to compare with input from key handler
	String left = "LEFT";
	String right = "RIGHT";
	String up = "UP";
	String down = "DOWN";
	
	String imageName = "images/monkey.png";
	Image image = new Image(new File("src/images/monkey.png").toURI().toString());
	
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	ArrayList<String> input;

	public Monkey(GraphicsContext gc, Canvas gameCanvas, ArrayList<String> input) {
		super();
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.input = input;
	}

	public Monkey(GraphicsContext gc, Canvas gameCanvas, ArrayList<String> input, String imageName) {
		super();
		this.imageName = imageName;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.input = input;
	}

	//get random starting point
	public void randomXY() {
		this.x = (int)(Math.random()*(gameCanvas.getWidth()-this.image.getWidth()));
		this.y = (int)(Math.random()*(gameCanvas.getHeight()-this.image.getHeight()));
	}
	
	public void move() {
		
		//if statements below determine direction monkey will move
		//by checking what input array list that is filled from
		//key handler contains
		
		//for left button
		//if left button is pressed move in left direction
		if (this.input.contains(this.left)) {
			this.dx = -this.speed;
		//if right button is pressed move to right
		} else if (this.input.contains(this.right)) {
			this.dx = this.speed;
		//if neither left or right is pressed dont move in x axis
		}else {
			this.dx = 0;
		}
		
		//for up button
		//if up button is pressed move up
		if (this.input.contains(this.up)) {
			this.dy = -this.speed;
		//if down is pressed move down
		} else if (this.input.contains(this.down)) {
			this.dy = this.speed;
		//if neither up or down is pressed dont move in y axis
		} else {
			this.dy = 0;
		}
		
		//save old position before movement
		double x = this.x;
		double y = this.y;
		
		//move the monkey
		this.x += this.dx;
		this.y += this.dy;
		
		//these if statements check if the monkey are on the edge of the screen
		//and don't allow the monkey to pass the borders
		//reset monkey to old position before if statements were used
		if (this.x<0 || this.x > gameCanvas.getWidth() - this.image.getWidth()) {
			this.x = x;
			this.y = y;
		}
		
		if (this.y<0 || this.y > gameCanvas.getHeight() - this.image.getHeight()) {
			this.x = x;
			this.y = y;
		}
		
		//draw monkey on screen
		this.gc.drawImage(this.image, this.x, this.y);
	}
	
	//All getters and setters-----------
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	//method to get boundary of monkey to use for checking collisions
	//uses rectangle 2D
    public Rectangle2D getBoundary() {
    	return new Rectangle2D(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }
    
    
    //methods to check if monkey collided with banana or coconut using rectangle 2D
    public boolean collisionBanana(Banana banana) {
    	boolean collide = this.getBoundary().intersects(banana.getBoundary());
    	return collide;
    }
    
    //method tp check if monkey touched coconut
    public boolean collisionCoconut(Coconut coconut) {
    	boolean collide = this.getBoundary().intersects(coconut.getBoundary());
    	return collide;
    }
    
    //method to check if monkey collided with rotten banana
    public boolean collisionRotten(RottenBanana rotten) {
    	boolean collide = this.getBoundary().intersects(rotten.getBoundary());
    	return collide;
    }
	
    //method to check if monkey collided with power up
    public boolean collisionPower(PowerUp power) {
    	boolean collide = this.getBoundary().intersects(power.getBoundary());
    	return collide;
    }
    
}
