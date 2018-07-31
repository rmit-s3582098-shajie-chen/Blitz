package Command;

import java.util.Optional;

import Model.Model;
import javafx.scene.control.TextInputDialog;

public class SaveCommand extends Command {

	@Override
	public void execute() {
		TextInputDialog dialog = new TextInputDialog(GetDocPath.getDocumentsPath() + "\\blitzGame.save");
		dialog.setTitle("Save Game");
		dialog.setHeaderText("Please enter a save file location:");
		dialog.setContentText("Location: ");
		
		// Blocks until user takes action on dialog.
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent()) {
			System.out.println("Saving to " + result.get());
			Model.getInstance().saveGame(result.get());
		}
	}

}
