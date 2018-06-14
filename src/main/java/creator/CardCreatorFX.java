package creator;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CardCreatorFX extends Application {

	private Stage stage;
	private String imagePath;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Card creator");
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(10);
		pane.setHgap(10);

		TextField cardTitle = new TextField();
		cardTitle.setPromptText("Enter card title");
		GridPane.setConstraints(cardTitle, 1, 0);
		cardTitle.setMaxWidth(200);

		TextField cardCost = new TextField();
		cardCost.setPromptText("Cost");
		GridPane.setConstraints(cardCost, 2, 0);
		cardCost.setMaxWidth(50);

		TextField cardStats = new TextField();
		cardStats.setPromptText("Stats");
		GridPane.setConstraints(cardStats, 2, 3);
		cardStats.setMaxWidth(50);

		ImageView imageView = new ImageView();
		imageView.setFitHeight(200);
		imageView.setFitWidth(200);
		GridPane.setConstraints(imageView, 1, 2);

		Button browseButton = new Button("Select image");
		browseButton.setOnAction(e -> {
			File file = FileBrowser.openFile("Image browser", new ExtensionFilter("png files (*.png)", "*.png"));
			if (isImage(file)) {
				try {
					imagePath = file.toURI().toURL().toExternalForm();
					imageView.setImage(new Image(imagePath));
					imageView.setFitHeight(200);
					imageView.setFitWidth(200);
				} catch (Exception e1) {

				}
			} else {
				AlertBox.display("Not an image", "Invalid file format");
			}
		});
		GridPane.setConstraints(browseButton, 1, 1);

		TextArea cardDescription = new TextArea();
		cardDescription.setPromptText("Add card description here");
		cardDescription.setMaxHeight(200);
		cardDescription.setMaxWidth(200);
		cardDescription.setWrapText(true);
		GridPane.setConstraints(cardDescription, 1, 3);

		Button saveButton = new Button("Save");
		saveButton.setOnAction(
				e -> saveCard(cardTitle.getText(), cardDescription.getText(), cardCost.getText(), cardStats.getText()));
		GridPane.setConstraints(saveButton, 3, 3);

		pane.getChildren().addAll(browseButton, imageView, cardTitle, cardDescription, saveButton, cardCost, cardStats);
		Scene scene = new Scene(pane, 350, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	private void saveCard(String title, String description, String cost, String stats) {
		Canvas canvas = new Canvas(200, 350);
		GraphicsContext context = canvas.getGraphicsContext2D();
		Image image = new Image(imagePath, 200, 200, false, true);
		context.setFont(new Font("Courier New", 20));
		context.fillText(title, 0, 14);
		context.drawImage(image, 0, 15);
		context.setFont(new Font("Courier New", 9));
		context.fillText(description, 0, 230);
		context.setFont(new Font("Courier New", 20));
		context.setFill(Color.GOLD);
		context.fillText(cost, 188, 14);
		context.setFill(Color.GREEN);
		context.fillText(stats, 140, 349);
		File file = FileBrowser.saveFile("Image browser", new ExtensionFilter("png files (*.png)", "*.png"));
		if (file != null) {
			try {
				WritableImage writableImage = new WritableImage(200, 350);
				canvas.snapshot(null, writableImage);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
				ImageIO.write(renderedImage, "png", file);
			} catch (IOException ex) {

			}
		}
	}

	private boolean isImage(File file) {
		try {
			return ImageIO.read(file) != null;
		} catch (IOException e) {
			return false;
		}
	}
}
