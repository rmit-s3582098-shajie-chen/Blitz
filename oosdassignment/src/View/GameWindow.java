package View;

import java.util.ArrayList;

import Command.AUCommand;
import Command.MoveCommand;
import Command.SaveCommand; 
import Controller.Controller;
import Model.Model;
import Model.IPiece;
import Model.IPiece.Type;
import Model.Ground.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class GameWindow extends Window {

	Timeline timeline = new Timeline();
	
	private int xSelected = -1;
	private int ySelected = -1;
	private long previousMoveTime = 0;
	private StringProperty timeLeft = new SimpleStringProperty();
	private StringProperty moveModeText = new SimpleStringProperty();
	private int timerTotal = 30;
	private GridPane grid;

	public GameWindow() {
		loadImages();
	}

	@Override
	protected Scene generateScene() {
		// Set the grid layout
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(0);
		grid.setVgap(0);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Model model = Model.getInstance();

		// For each BoardSquare in the grid of the board, we place an image, and add a
		// mouse click event handler.
		for (int x = 0; x < model.getWidth(); x++) {
			for (int y = 0; y < model.getHeight(); y++) {

				boolean showSelected = false;

				if (xSelected >= 0 && ySelected >= 0) {
					if (x == xSelected && y == ySelected || model.isValidMove(xSelected, ySelected, x, y)) {
						showSelected = true;
					}
				}

				int xPos = x;
				int yPos = y;
				MoveCommand command = new MoveCommand(xPos, yPos, this);

				// Add ground image.
				Image groundImage = getGroundImage(model.getBoardGround(x, y));
				final ImageView imageGround = new ImageView();
				if (groundImage != null)
					imageGround.setImage(groundImage);
				grid.add(imageGround, x, y);

				// Add piece image(s).
				Type[] types = model.getPieceType(x, y);
				for (int i = 0; i < types.length; i++) {
					Image image = getPieceImage(types[i]);
					final ImageView imagePiece = new ImageView();
					imagePiece.setImage(image);
					imagePiece.addEventHandler(MouseEvent.MOUSE_CLICKED, command);
					grid.add(imagePiece, x, y);
				}

				// Add defensive shield image(s).
				if (model.isDefensive(x, y)) {
					Image defensiveImage = getDefensiveImage();
					final ImageView imageDefensive = new ImageView();
					imageDefensive.setImage(defensiveImage);
					imageDefensive.addEventHandler(MouseEvent.MOUSE_CLICKED, command);
					grid.add(imageDefensive, x, y);
				}

				// Add selected images.
				Image selectedImage = getSelectedImage();
				final ImageView imageSelected = new ImageView();
				imageSelected.setImage(selectedImage);
				if (showSelected)
					grid.add(imageSelected, x, y);

				imageSelected.addEventHandler(MouseEvent.MOUSE_CLICKED, command);
			}
		}

		Button attackBtn = new Button();
		attackBtn.setText("Attackive");
		attackBtn.setAlignment(Pos.CENTER);
		attackBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				model.isDefensiveMode = false;
				moveModeText.set(model.getMoveMode());
			}
		});
		HBox boxRight2 = new HBox(10);
		boxRight2.setAlignment(Pos.CENTER_RIGHT);
		boxRight2.getChildren().add(attackBtn);

		Button defendBtn = new Button();
		defendBtn.setText("Defensive");
		defendBtn.setAlignment(Pos.CENTER);
		defendBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Defensive mode.");
				model.isDefensiveMode = true;
				moveModeText.set(model.getMoveMode());
			}
		});
		boxRight2.getChildren().add(defendBtn);
		grid.add(boxRight2, 0, model.getHeight() + 1, model.getWidth(), 1);
		GridPane.setMargin(boxRight2, new Insets(10, 0, 0, 0));

		// Alternative Universes button.
		Button auBtn = new Button();
		auBtn.setText("Alternative Universes");
		auBtn.setAlignment(Pos.CENTER);
		auBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new AUCommand());
		HBox boxRight = new HBox(10);
		boxRight.setAlignment(Pos.CENTER_RIGHT);
		boxRight.getChildren().add(auBtn);

		// Undo button.
		Button undoBtn = new Button();
		undoBtn.setText("Undo");
		undoBtn.setAlignment(Pos.CENTER);
		undoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new MoveCommand() {
						@Override
						public void handle(MouseEvent arg0) { 
							this.unexecute();
						}
					});
		boxRight.getChildren().add(undoBtn);

		// Save button.
		Button saveBtn = new Button();
		saveBtn.setText("Save");
		saveBtn.setAlignment(Pos.CENTER);
		saveBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new SaveCommand());
		boxRight.getChildren().add(saveBtn);

		// Forfeit to Menu button.
		Button forfeitBtn = new Button();
		forfeitBtn.setText("Forfeit to Menu");
		forfeitBtn.setAlignment(Pos.CENTER);
		forfeitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				xSelected = -1;
				ySelected = -1;
				View.getInstance().showMainMenu();
			}
		});
		boxRight.getChildren().add(forfeitBtn);

		grid.add(boxRight, 0, model.getHeight() + 1, model.getWidth(), 1);
		GridPane.setMargin(boxRight, new Insets(80, 0, 0, 0));

		Label moveMode = new Label();
		grid.add(moveMode, 0, model.getHeight() + 1, model.getWidth(), 1);
		GridPane.setMargin(moveMode, new Insets(105, 0, 0, 0));
		moveModeText.set(model.getMoveMode());
		moveMode.textProperty().bind(moveModeText);

		// Adds the current player information underneath the board, with a top offset
		// of (70).
		Label currentPlayer = new Label("Player: " + Model.getInstance().getCurrentPlayer());
		grid.add(currentPlayer, 0, model.getHeight() + 1, model.getWidth(), 1);
		GridPane.setMargin(currentPlayer, new Insets(70, 0, 0, 0));

		// Adds the number of moves information underneath the board, with a top offset
		// of (35).
		Label numMoves = new Label("Moves: " + Model.getInstance().getNumMoves());
		grid.add(numMoves, 0, model.getHeight() + 1, model.getWidth(), 1);
		GridPane.setMargin(numMoves, new Insets(35, 0, 0, 0));

		// Adds the timer information underneath the board, with a top offset of (0).
		// However the timer label is binded to the timeLeft StringProperty for
		// automatic updating.
		Label timerLabel = new Label("Timer: ");
		grid.add(timerLabel, 0, model.getHeight() + 1, model.getWidth(), 1);
		timerLabel.textProperty().bind(timeLeft);
		GridPane.setMargin(timerLabel, new Insets(0, 0, 0, 0));

		return new Scene(grid);
	}

	/**
	 * Gets the image for a defensive shield.
	 */
	private Image getDefensiveImage() {
		return imageTiles.get(TileType.DEFENSIVE.getValue());
	}

	/**
	 * Gets an image for the corresponding ground type.
	 */
	private Image getGroundImage(Ground boardGround) {

		if (boardGround instanceof GroundGrass) {
			return imageTiles.get(TileType.GRASS.getValue());
		} else if (boardGround instanceof GroundTree) {
			return imageTiles.get(TileType.TREE.getValue());
		} else if (boardGround instanceof GroundShoes) {
			return imageTiles.get(TileType.SHOES.getValue());
		}

		return null;
	}

	/**
	 * Gets the tile for the selected overlay image.
	 */
	public Image getSelectedImage() {
		return imageTiles.get(TileType.SELECTED.getValue());
	}

	/**
	 * Gets an image tile from storage of the specified piece type.
	 */
	private Image getPieceImage(IPiece.Type type) {
		switch (type) {
		case NONE:
			return imageTiles.get(TileType.EMPTY.getValue());
		case ASSASSIN:
			return imageTiles.get(TileType.ASSASSIN.getValue());
		case GUARD:
			return imageTiles.get(TileType.GUARD.getValue());
		case KNIGHT:
			return imageTiles.get(TileType.KNIGHT.getValue());
		case MERCHANT:
			return imageTiles.get(TileType.MERCHANT.getValue());
		case PIKEMAN:
			return imageTiles.get(TileType.PIKEMAN.getValue());
		case WIZARD:
			return imageTiles.get(TileType.WIZARD.getValue());
		default:
			System.err.println("That piece type does not exist?");
			break;
		}

		return null;
	}

	/**
	 * Starts a countdown for a move timer.
	 */
	public void startMoveTimer() {
		restartMoveTimer();

		timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				int timeElapsed = (int) ((System.currentTimeMillis() - previousMoveTime) / 1000);

				if (timeElapsed == timerTotal) {
					System.out.println("Timer Elapsed");
					previousMoveTime = System.currentTimeMillis();
					Controller.getInstance().timerElapsed();
					View.getInstance().refreshGameWindow();
				}

				timeLeft.set("Timer: " + Integer.toString(timerTotal - timeElapsed));
			}
		}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void stopTimer() {
		timeline.pause();
		System.out.println("Timer paused.");
	}
	
	public void restartMoveTimer() {
		previousMoveTime = System.currentTimeMillis();
		timeLeft.set("Timer: " + Integer.toString(timerTotal));
	}

	public int getxSelected() {
		return xSelected;
	}

	public int getySelected() {
		return ySelected;
	}

	public void setxSelected(int xSelected) {
		this.xSelected = xSelected;
	}

	public void setySelected(int ySelected) {
		this.ySelected = ySelected;
	}
}
