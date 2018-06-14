package creator;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FileBrowser {

	public static File openFile(String title, ExtensionFilter extensionFilter) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extensionFilter);
		fileChooser.setTitle(title);
		return fileChooser.showOpenDialog(window);
	}

	public static File saveFile(String title, ExtensionFilter extensionFilter) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extensionFilter);
		fileChooser.setTitle(title);
		return fileChooser.showSaveDialog(window);
	}
}
