package monkeygame;

/*
 * JavaFX Monkey Game
 * Author: Ahmed Kadry
 * Date: May 16, 2022
 * Description: Basic monkey game using objects
 * This main class contains code to open game
 * This game uses multiple png images from googleimages.com
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Monkey Game");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MonkeyGame.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 1000, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			MonkeyGameController controller = loader.getController();
			primaryStage.setScene(scene);
			controller.getScene(primaryStage);
			controller.gameLoop();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
