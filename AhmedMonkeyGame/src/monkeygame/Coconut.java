package monkeygame;

import java.io.File;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/*
 *Monkey Game Coconut Class
 * Author: Ahmed Kadry
 * Date: May 16, 2022
 * Description: This is the class for mthe coconut and its movement 
 */

public class Coconut {    
    
    // declare fields (i.e. variables)
    static int numCoconuts = 1;
    int speed = 3;
    double x = 200;
    double y = 200;
    double dx = speed;
    double dy = - speed;
    String imageName = "images/coconut.png";
    Image image = new Image(new File("src/images/coconut.png").toURI().toString());
    GraphicsContext gc;
    @FXML
    Canvas gameCanvas;
    
    //possible options for speed of coconut
    int[] speedList = {-4,-3,-2,-1,1,2,3,4};
    
    // constructors -----------------------------------------------
    public Coconut(GraphicsContext gc, Canvas canvas) {
        super();
        this.gc = gc;
        this.gameCanvas = canvas;
        randomStart();
    }

    public Coconut(String imageName, GraphicsContext gc, Canvas canvas) {
        super();
        this.imageName = imageName;
        this.gc = gc;
        this.gameCanvas = canvas;
        randomStart();
    }
    
    
    //start with random speed and location
    public void randomStart() {
    	int rnd = (int)(Math.random()*speedList.length);
    	this.dx = speedList[rnd];
    	rnd = (int)(Math.random()*speedList.length);
    	this.dy = speedList[rnd];
    	this.x = (int)(Math.random()*(this.gameCanvas.getWidth()-this.image.getWidth()));
    	this.y = (int)(Math.random()*(this.gameCanvas.getHeight()-this.image.getHeight()));
    }

    // all the getters and setters ----------------------------------
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    // contains all code to move and display image-----------------
    public void move() {
        this.x += this.dx;
        this.y += this.dy;
        
        if (this.x <= 0 ||this.x >= this.gameCanvas.getWidth() - this.image.getWidth()) {
            this.dx = -this.dx;
        }
        if (this.y <= 0||this.y >= this.gameCanvas.getHeight() - this.image.getHeight()) {
            this.dy = -this.dy;
        }
        //draw image
        this.gc.drawImage(this.image, this.x, this.y);
    
    }
    
    //rectangle2d method to get boundary to be able to check for collisions
    public Rectangle2D getBoundary() {
    	return new Rectangle2D(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }
}

