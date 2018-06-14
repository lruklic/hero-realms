package creator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TutorialFX extends Application {

	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Title");
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(10);
		pane.setHgap(10);

		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Star Wars", "Star Lord", "Star night");
		comboBox.setPromptText("What is your favorite movie?");
		GridPane.setConstraints(comboBox, 0, 0);

		pane.getChildren().addAll(comboBox);

		Scene scene = new Scene(pane, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
}
