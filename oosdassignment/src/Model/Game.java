package Model;

import java.io.Serializable;
import java.util.ArrayList;
import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

import Model.Ground.Ground;
import PersistentDataStructure.IStack;
import PersistentDataStructure.RootNode;
import Model.IPiece.Type;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Invariant({ "gameBoard != null", "currentTurn != null", "numMoves >= 0" })
public class Game implements Serializable {
	private Board gameBoard;
	private int numMoves = 0;
	private Turn currentTurn;

	/**
	 * Points to the root node of the custom stack (tree).
	 */
	IStack<BoardSnapshot> rootOfStack = new RootNode<BoardSnapshot>();
	/**
	 * Points to the current node holding the most recent stack.
	 * Wouldn't point to the end of the stack if we've rewound using alternative universes.
	 */
	IStack<BoardSnapshot> persistentStack;
		
	/**
	 * Each teams gets to use undo only once in a game.
	 */
	private boolean canMerchantUndo = true;
	private boolean canBanditUndo = true;

	public enum Turn {
		MerchantTeam("Merchant Team"), BanditTeam("Bandit Team");
		private final String text;

		Turn(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

		/**
		 * Finds which team the specified piece belongs to.
		 * 
		 * @param type
		 *            Type of piece.
		 * @return Team (Merchant, or Bandit)
		 */
		public static Turn getTeam(Type type) {
			if (type == IPiece.Type.MERCHANT || type == IPiece.Type.GUARD || type == IPiece.Type.PIKEMAN) {
				return Turn.MerchantTeam;
			} else if (type == IPiece.Type.KNIGHT || type == IPiece.Type.WIZARD || type == IPiece.Type.ASSASSIN) {
				return Turn.BanditTeam;
			}
			return null;
		}
	};

	public Game() {
		currentTurn = Turn.MerchantTeam;
	}

	/**
	 * Switches the current player without making a move. Used when the move timer
	 * runs out.
	 */
	public void timerElapsed() {

		// Make sure to record empty move.
		persistentStack = persistentStack.Push(gameBoard.getBoardSnapshot());
		numMoves++;

		changeTurns();
	}

	/**
	 * Test to see whether the specified move can take place.
	 * @return True, if the move is a valid one.
	 */
	@Requires({ "startx > 0", "starty > 0", "ednx > 0", "endy >0 ", "startx  < width ", "starty < height",
			"endx < width ", "endy < height" })
	public boolean isValidMove(int startX, int startY, int endX, int endY) {
		IPiece piece = gameBoard.getPiece(startX, startY);

		if (piece.getType()[0] != IPiece.Type.NONE) {

			// Make sure we've got the right team.
			if (Turn.getTeam(piece.getType()[0]) == currentTurn) {

				if (Model.getInstance().moveAvailable(endX, endY)) {
					int manhattanDistance = Math.abs(startX - endX) + Math.abs(startY - endY);
					if (manhattanDistance <= piece.getMoveDistance()) {
						// Make sure we're not capturing our own.
						IPiece endPiece = gameBoard.getPiece(endX, endY);

						// If we're landing on a piece.
						if (endPiece != null) {
							if (Turn.getTeam(endPiece.getType()[0]) != currentTurn) {
								// Pieces are on different teams.
								// Make sure the piece is able to capture.
								if (piece.getPower() >= endPiece.getPower())
									return true;
							} else {
								// Pieces are on the same team.
								// Not allowed to combine the merchant.
								if (piece.getType()[0] == Type.MERCHANT || endPiece.getType()[0] == Type.MERCHANT) {
									return false;
								}
								return true;
							}
						} else {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Moves the unit at (startx, starty) to (endx, endy)
	 * @return True, if the move was successful.
	 */
	@Requires({ "startx > 0", "starty > 0", "endx > 0", "endy >0 ", "startx  < width ", "starty < height",
			"endx < width ", "endy < height", "(startx != endx) && (starty != endy)", "endx < width ",
			"endy < height" })
	public boolean move(int startx, int starty, int endx, int endy, boolean defensive) {

		if (isValidMove(startx, starty, endx, endy)) {

			// Move is appropriate, initiate move now.
			System.out.println("Move " + startx + " " + starty + " to " + endx + " " + endy);

			gameBoard.move(startx, starty, endx, endy, defensive);
			numMoves++;

			// Check if the game's ended.
			hasTheGameEnded();

			// Save the game history in a stack.
			BoardSnapshot boardSnapshot = gameBoard.getBoardSnapshot();
			persistentStack = persistentStack.Push(boardSnapshot);

			changeTurns();
			return true;
		}
		return false;
	}

	private void changeTurns() {
		if (currentTurn == Turn.MerchantTeam) {
			currentTurn = Turn.BanditTeam;
		} else {
			currentTurn = Turn.MerchantTeam;
		}
	}

	/**
	 * Checks the board status to see whether the game has ended. Looks at whether
	 * the merchant still exists, and if it does, checks if it has reached the top
	 * of the board.
	 * @return True, if the game has ended.
	 */
	@Requires({ "unitType != null" })
	public boolean hasTheGameEnded() {

		IPiece unit = gameBoard.searchUnit(Type.MERCHANT);

		// If unit doesn't exist (has died)
		if (unit == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Feel free to play again :)");
			alert.setTitle("Game Over!");
			alert.setHeaderText("Bandits Won!");
			alert.showAndWait();
			return true;
		}

		// If unit exists, and is at the top of the board.
		if (unit.getPositionY() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Feel free to play again :)");
			alert.setTitle("Game Over!");
			alert.setHeaderText("Merchants Won!");
			alert.showAndWait();
			return true;
		}

		// Neither win condition satisfied yet...
		return false;
	}

	public String getCurrentPlayer() {
		return currentTurn.toString();
	}

	public int getNumMoves() {
		return numMoves;
	}

	public int getHeight() {
		return gameBoard.getHeight();
	}

	public int getWidth() {
		return gameBoard.getWidth();
	}

	@Requires({ "x > 0", "y > 0", "x < gameBoard.getWidth()", "y < gameBoard.getHeight()" })
	public Type[] getPieceType(int x, int y) {
		return gameBoard.getPieceType(x, y);
	}

	@Requires({ "x > 0", "y > 0", "x < width", "y < height" })
	public Ground getBoardGround(int x, int y) {
		return gameBoard.getBoardGround(x, y);
	}

	@Ensures({ "gameBoard != null" })
	public void startGame(int size) {
		gameBoard = new Board(size, size);

		// Save the initial snapshot in history.
		BoardSnapshot boardSnapshot = gameBoard.getBoardSnapshot();
		rootOfStack = new RootNode<BoardSnapshot>();
		persistentStack = rootOfStack.Push(boardSnapshot);
		numMoves = 0;
		canMerchantUndo = true;
		canBanditUndo = true;
	}
	@Requires({ "x > 0", "y > 0","x < gameBoard.getWidth()", "y < gameBoard.getHeight()"})
	@Ensures({ "result != null" })
	public IPiece getPiece(int x, int y) {
		if (gameBoard.getPiece(x, y) instanceof IPiece) {
			return gameBoard.getPiece(x, y);
		}
		return null;
	} 
 	public boolean isAllowedToUndo() {
		if (currentTurn == Turn.MerchantTeam) {
			return canMerchantUndo;
		}
		return canBanditUndo;
	}

	/**
	 * Loads a state of game a certain number of steps prior.
	 */
	@Requires({ "undoSteps > 0 && undoSteps <= 3"})
	public void undoTurn(int undoSteps) {

		if (currentTurn == Turn.MerchantTeam) {
			canMerchantUndo = false;
		} else {
			canBanditUndo = false;
		}

		for (int i = 0; i <= undoSteps; i++) {
			
			if (persistentStack.isEmpty()) {
				gameBoard = new Board(persistentStack.getNextStacks().get(0).Peek());
				break;
			}
			
			if (i == undoSteps)
				gameBoard = new Board(persistentStack.Peek());
			else
				persistentStack = persistentStack.Pop();

		}
	}

	/**
	 * Loads a specified boardID from the persistent data structure. 
	 */
	public void loadBoardID(int parseInt) {
		System.out.println("Load BoardID " + parseInt);
		IStack<BoardSnapshot> stack = rootOfStack.getNextStacks().get(0);
		if (!findMatch(stack, parseInt)) {
			System.out.println("Could not find boardID " + parseInt + " in persistent stack.");
		}
	}
	
	/**
	 * Recursive function that searches through a stack for a boardID.
	 * @param stack Stack to search in.
	 * @param id BoardID to search for.
	 * @return If a match has been found.
	 */
	private boolean findMatch(IStack<BoardSnapshot> stack, int id) {

		BoardSnapshot peek = stack.Peek();
		if (peek.boardID == id) {
			gameBoard = new Board(peek);
			persistentStack = stack;
			return true;
		}
		
		ArrayList<IStack<BoardSnapshot>> nextStacks = stack.getNextStacks();

		for(IStack<BoardSnapshot> stack1 : nextStacks) {
			if(findMatch(stack1, id)) {
				return true;
			}
		}
		
		return false;
	}

}
