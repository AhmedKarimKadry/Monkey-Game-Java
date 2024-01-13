package monkeygame;

/*
 *Monkey Game Controller Class
 * Author: Ahmed Kadry
 * Date: May 16, 2022
 * Description: This is the controller class for monkey game
 * This class contains methods to continue to display/refresh background
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MonkeyGameController {

	//Stage for pop up windows
	Stage secondaryStage;
	
	@FXML
	TextField endText;
	
	//Assign fx:ids and declare variables
	@FXML
	Canvas gameCanvas;
	Scene gameScene;
	GraphicsContext gc;

	//declare variables
	double invisibleTime = 0;
	double powerTime = 0;
	boolean on = true;
	boolean rottenOn = true;
	boolean powerOn = true;
	boolean collidedBanana = false;
	boolean collidedCoconut = false;
	boolean collidedRotten = false;
	boolean collidedPower = false;
	
	//method to get scene when starting game in main class
	public void getScene(Stage primaryStage) {
		gameScene = primaryStage.getScene();
	}

	
	//method to loop and refresh screen every frame
	public void gameLoop() {
		
		gc = gameCanvas.getGraphicsContext2D();
		
		//get background image
		Image background = new Image(new File("src/images/background.png").toURI().toString());
		
		//array list to store amount of cocunuts
		ArrayList<Coconut> coconutList = new ArrayList<Coconut>();
		
		//fill array list depending on amount of coconuts
		for (int i = 0; i<Coconut.numCoconuts; i++) {
			coconutList.add(new Coconut(gc, gameCanvas));
		}
		
		//array list to store rotten bananas
		ArrayList<RottenBanana> rottenList = new ArrayList<RottenBanana>();
		
		//array list for power ups
		ArrayList<PowerUp> powerList = new ArrayList<PowerUp>();
		
		//get instance of banana
		Banana banana = new Banana(gc, gameCanvas);
		
		//array list for monkey movement
		ArrayList<String> input = new ArrayList<String>();
		
		//handler for while key is pressed
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!input.contains(code))
                    input.add(code);
            }
        });

        //handler for when key is released
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (input.contains(code))
                    input.remove(code);
            }
        });

        //declare instances of monkey and score
		Monkey monkey = new Monkey(gc, gameCanvas, input);
		Score score = new Score(gc, gameCanvas);
		
		new AnimationTimer() {
			
			// actual game loop that repeats
			@Override
			public void handle(long currentNanoTime) {
				
				
				// clear the whole canvas each frame
				gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
				//draw background image
				gc.drawImage(background, 0, 0);
				
				//call methods from banana and monkey classes
				banana.drawBanana();
				monkey.move();
				
				
				//move every coconut in array list
				for (int i = 0; i < Coconut.numCoconuts; i++) {
				    coconutList.get(i).move();
				}
				
				
				//add coconut every 5 score
				if (Banana.bananasEaten%5 == 0 && Banana.bananasEaten>0&& on == true) {
					Coconut.numCoconuts+=1;
					coconutList.add(new Coconut(gc, gameCanvas));
					on = false;
				}
				
				//add power up every 6 points
				if (Banana.bananasEaten%6==0 && Banana.bananasEaten>0 &&powerOn == true) {
					powerList.add(new PowerUp(gc, gameCanvas));
					powerOn = false;
				}	
				for (int i =0; i<powerList.size(); i++) {
					powerList.get(i).drawPowerUp();
				}
				
				//add rotten banana every 3 points
				if (Banana.bananasEaten%3==0 && Banana.bananasEaten>0 &&rottenOn == true) {
					rottenList.add(new RottenBanana(gc, gameCanvas));
					rottenOn = false;
				}	
				for (int i =0; i<rottenList.size(); i++) {
					rottenList.get(i).drawBanana();
				}
				
				
				
				//check if monkey touched power up
				for (int i =0; i<powerList.size(); i++) {
					PowerUp p = powerList.get(i);
					collidedPower = monkey.collisionPower(p);
					if (collidedPower) {
						//give 10 seconds for power up
						powerTime = 11;
						//remove power up from screen
						powerList.remove(i);
					}
				}
				
				
				//check if monkey ate rotten banana
				for (int i =0; i<rottenList.size(); i++) {
					RottenBanana r = rottenList.get(i);
					collidedRotten = monkey.collisionRotten(r);
					if (collidedRotten) {
						//remove from score if rotten banana is eaten
						Banana.bananasEaten --;
						rottenList.remove(i);

					}
				}
				
				invisibleTime += 0.015625;
				
				//check if monkey touched banana
				collidedBanana = monkey.collisionBanana(banana);
				if (collidedBanana) {
					//add to score
					Banana.bananasEaten ++;
					//draw new banana in random place
					banana.randomBanana();
					//reset boolean for new coconuts
					on = true;
					//reset boolean for rotten banana
					rottenOn = true;
					//reset boolean for powerups
					powerOn = true;
				}
				
				//invisible for first 2 seconds
				if (invisibleTime>2) {
					//check if monkey touched coconut for every coconut on screen
					for (int i = 0; i<Coconut.numCoconuts; i++) {
						Coconut c = coconutList.get(i);
						collidedCoconut = monkey.collisionCoconut(c);
						if (collidedCoconut) {
							//decrease lives
							monkey.lives --;
							//start new life at random location
							monkey.randomXY();
							
							//if lives are out, end game
							if (monkey.lives<=0) {
								try {
									changeSceneEnd();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				
				//decrease time by 1/64 of a second every frame of the 64 fps
				powerTime -=(0.015625);
				//if time left with power up is greater than 0, give monkey speed
				//and display time remaining as a countdown
				if (powerTime>0) {
					monkey.setSpeed(10);
					 String timeString = "POWER UP: " + (int)(powerTime)+"s" ;
					 	gc.setFont(Font.font("ComicSansMS", FontWeight.BOLD, 54));
					    gc.setFill(Color.YELLOW);
					    gc.fillText(timeString, 600, 50);
				}else {
					//if power up timer is over, return to normal speed
					monkey.setSpeed(4);
				}
				//display score and lives
				score.display(monkey);
		            }
		}.start();
		
	}
	
	
	//code to display end screen
    public void changeSceneEnd() throws IOException {
        final BorderPane sceneTwoParent = (BorderPane)FXMLLoader.load(this.getClass().getResource("End.fxml"));
        final Scene sceneTwo = new Scene((Parent)sceneTwoParent, 1000, 700.0);
        final Stage stage = (Stage)gameCanvas.getScene().getWindow();
        stage.setScene(sceneTwo);
        stage.show();
    }
	
    //handler for menu items
    public void buttonClickHandler(ActionEvent evt) {
    	Button buttonClicked = (Button) evt.getTarget(); 
		String buttonLabel = buttonClicked.getText();
		
		if (buttonLabel.equals("QUIT")) {
			Platform.exit();
			System.exit(0);
		}
    }
     
    
    //Handler for menu bar and clicks
	public void menuClickHandler(ActionEvent evt) {
		
		//Find menu clicked
		MenuItem clickedMenu = (MenuItem) evt.getTarget();
		//Get text of menu
		String menuLabel = clickedMenu.getText();
	
		if ("Instructions".equals(menuLabel)) {
			openInstructionsWindow();
		}
		
		//close game if quit is clicked
		if ("Quit".equals(menuLabel)) {
		    Platform.exit();
		    System.exit(0);
		}
	}
	
	//open instructions pop up
	private void openInstructionsWindow() {
		try {
			// load the pop up you created
		Pane howTo = (Pane)FXMLLoader.load(getClass().getResource("Instructions.fxml"));
				
				// create a new scene
		Scene howToScene = new Scene(howTo,250,250);

		// add css to the new scene		
		howToScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		//create new stage to put scene in
				secondaryStage = new Stage();
				secondaryStage.setScene(howToScene);
				secondaryStage.setResizable(false);
				secondaryStage.showAndWait();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
    
	// close the window that is currently open
	//it figures out where the event came from and closes that stage
	public void closeCurrentWindow(final ActionEvent evt) {
	    final Node source = (Node) evt.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	


}
