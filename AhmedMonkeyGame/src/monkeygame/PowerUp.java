package monkeygame;

/*
 *Monkey Game Power Up Class
 * Author: Ahmed Kadry
 * Date: May 16, 2022
 * Description: This is the class for the power up and its spawn at random locations 
 */
import java.io.File;

/*
 *Monkey Game Power up Class
 * Author: Ahmed Kadry
 * Date: May 16, 2022
 * Description: This is the class for the power up and for it to be randomly placed
 */

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PowerUp {

	//declare variables
	int numRotten = 0;
	double x;
	double y;
	//get image
	String imageName = "images/powerUp.png";
	Image image = new Image(new File("src/images/powerUp.png").toURI().toString()
			,100,100,false,false);
	
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	//Constructors
	public PowerUp(GraphicsContext gc, Canvas gameCanvas) {
		super();
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomLocation();
	}


	public PowerUp(String imageName, GraphicsContext gc, Canvas gameCanvas) {
		super();
		this.imageName = imageName;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomLocation();
	}

	//method to place rotten banana at a random point in the screen
	public void randomLocation() {
		this.x = (int)(Math.random()*(this.gameCanvas.getWidth()-this.image.getWidth()));
		this.y = (int)(Math.random()*(this.gameCanvas.getHeight()-this.image.getHeight()));
	}
	
	//draw/spawn banana at the random location
	public void drawPowerUp() {
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
