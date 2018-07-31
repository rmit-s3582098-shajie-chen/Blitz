package Command;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import Controller.Controller;
import Model.Model;
import View.View;
import javafx.stage.FileChooser;

public class OpenCommand extends Command {
	private Desktop desktop = Desktop.getDesktop();

	@Override
	public void execute() {

		// Show the file chooser
		final FileChooser fileChooser = new FileChooser();
		File result = fileChooser.showOpenDialog(null);
		if (result != null) {
			openFile(result);
			System.out.println("the file location" + result.getAbsolutePath());
		}

		// If the user clicked ok.
		if (result != null) {
			System.out.println("Opening " + result.getAbsolutePath());
			Controller.getInstance().startGame();
			Model.getInstance().openGame(result.getAbsolutePath());
			View.getInstance().refreshGameWindow();
		} else {
			System.out.println("close ");
		}
	}

	private void openFile(File file) {
		try {
			this.desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
