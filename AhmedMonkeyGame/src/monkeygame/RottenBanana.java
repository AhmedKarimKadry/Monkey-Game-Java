package monkeygame;


/*
 *Monkey Game Rotten Banana Class
 * Author: Ahmed Kadry
 * Date: May 16, 2022
 * Description: This is the class for the rotten banana and for it to be randomly placed
 */

import java.io.File;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RottenBanana {

	//declare varibales
	int numRotten = 0;
	double x;
	double y;
	//get image
	String imageName = "images/rottenBanana.png";
	Image image = new Image(new File("src/images/rottenBanana.png").toURI().toString()
			,80,80,false,false);
	
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	//constructors
	public RottenBanana(GraphicsContext gc, Canvas gameCanvas) {
		super();
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomBanana();
	}


	public RottenBanana(String imageName, GraphicsContext gc, Canvas gameCanvas) {
		super();
		this.imageName = imageName;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomBanana();
	}

	//place banana at random point on the screen
	public void randomBanana() {
		this.x = (int)(Math.random()*(this.gameCanvas.getWidth()-this.image.getWidth()));
		this.y = (int)(Math.random()*(this.gameCanvas.getHeight()-this.image.getHeight()));
	}
	
	//draw banana at random point
	public void drawBanana() {
		gc.drawImage(this.image, this.x, this.y);
	}
	
	//All getters and setters-----------------

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


	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
	//boundary method to check for collisions
    public Rectangle2D getBoundary() {
    	return new Rectangle2D(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }
		
}
