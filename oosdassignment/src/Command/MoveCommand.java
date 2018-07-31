package Command;

import Model.Model;

import java.util.Optional;

import Model.IPiece;
import View.GameWindow;
import View.View;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class MoveCommand extends Command {
	
	int xPos;
	int yPos;
	int xSelected;
	int ySelected;
	Model model = Model.getInstance();
	GameWindow gameWindow;

	public MoveCommand() {
		
	}

	public MoveCommand(int xPos2, int yPos2, GameWindow gameWindow2) {
		this.xPos = xPos2;
		this.yPos = yPos2;
		this.gameWindow = gameWindow2;
	}

	@Override
	public void execute() {

		// Get the first click location.
		this.xSelected = gameWindow.getxSelected();
		this.ySelected = gameWindow.getySelected();

		gameWindow.setxSelected(xPos);
		gameWindow.setySelected(yPos);

		// There are 3 different situations.
		// 1. If the user clicked on the same square, cancel it.
		// 2. If the user clicked on a different place, initiate a move.
		// 3. This is the user's first click of the two.
		if (this.model.getPieceType(xPos, yPos) != null) {
			if (xPos == xSelected && yPos == ySelected) {
				// Cancel click.
				gameWindow.setxSelected(-1);
				gameWindow.setySelected(-1);
			} else {
				// If the user previously selected something
				if (xSelected >= 0 && ySelected >= 0) {
					// If previous selection was a piece
					if (model.getPieceType(xSelected, ySelected)[0] != IPiece.Type.NONE) {
						
						// Make sure move is available
						if (Model.getInstance().moveAvailable(xPos, yPos)) {
							int manhattanDistance = Math.abs(xSelected - xPos) + Math.abs(ySelected - yPos);
							if (manhattanDistance <= model.getPiece(xSelected, ySelected).getMoveDistance()) {
								model.makeMove(xSelected, ySelected, xPos, yPos);
								View.getInstance().restartMoveTimer();
								gameWindow.setxSelected(-1);
								gameWindow.setySelected(-1);
							}
						}
					}
				}
			}
		}
		View.getInstance().refreshGameWindow();
	}
	public void unexecute() 
	{
		TextInputDialog dialog = new TextInputDialog("1");
		dialog.setTitle("Undo Moves");
		dialog.setHeaderText("You can undo up to 3 moves at a time.\nNote you are only allowed to do this once.");
		dialog.setContentText("Number of moves to undo:");

		// Blocks until user takes action on dialog.
		Optional<String> result = dialog.showAndWait();

		Model model = Model.getInstance();

		// If user pressed ok.
		if (result.isPresent()) {
			System.out.println("Your move: " + result.get());

			if (isNumeric(result.get())) {
				int undoSteps = Integer.parseInt(result.get());

				if (undoSteps > 3 || undoSteps < 1) {
					showAlert("Error", "Please enter a number between 1 and 3.", "Try again.");
				} else if (!model.isAllowedToUndo()) {
					showAlert("No more undos left.", "You've used your undo already!", "Sorry!");
				} else {
					model.undoTurn(undoSteps * 2);
					View.getInstance().refreshGameWindow();
				}
			} else {
				showAlert("Error.", "Please enter a numeric number!", "Sorry!");
			}
		}

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> System.out.println("Your move: " + name));
		View.getInstance().refreshGameWindow();
	}
	/**
	 * Shows a generic alert box.
	 */
	private void showAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(content);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.showAndWait();
	}

	/**
	 * Checks if the string parameter is numeric only.
	 * @param str Input string to check.
	 * @return True if input parameter has only numeric characters.
	 */
	private boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}
}
