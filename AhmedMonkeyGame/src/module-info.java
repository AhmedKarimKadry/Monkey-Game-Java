module AhmedMonkeyGame {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens monkeygame to javafx.graphics, javafx.fxml;
}
