package monkeygame;

/*
 * Monkey Game Score Class
 * Author: Ahmed Kadry
 * Date: May 26, 2022
 * Description: This class has methods to display the score and lives during the game
 */

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score {

	//declare variables
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	//constructor
	public Score(GraphicsContext gc, Canvas gameCanvas) {
		super();
		this.gc = gc;
		this.gameCanvas = gameCanvas;
	}
	
	//display texts
	public void display(Monkey monkey) {
		//display score
		String scoreString = "Score: " +Banana.bananasEaten;
		gc.setFont(Font.font("ComicSansMS", FontWeight.BOLD, 36));
		gc.setFill(Color.BLUE);
	    gc.fillText(scoreString, 20, 50);
	    //display lives
	    String livesString = "Lives: " + monkey.getLives();
	    gc.setFill(Color.BLUE);
	    gc.fillText(livesString, 200, 50);
	}
	
}
