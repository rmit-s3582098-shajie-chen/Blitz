package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.lang3.SerializationUtils;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

import Model.Ground.GroundBlocked;
import Model.IPiece.Type;
import Model.Ground.Ground;
import Model.Ground.GroundTree;
import PersistentDataStructure.IStack;

/**
 * (MVC) Manages the core data of the application, also wraps the Game/Board.
 */
@Invariant({ "game != null", "instance != null" })
public class Model {
	private Game game = new Game();
	private static Model instance;
	public final static int DEFAULTSIZE = 17;

	public boolean isDefensiveMode = false;

	@Ensures({ "result != null " })
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	@Requires({ "startx > 0", "starty > 0", "ednx > 0", "endy >0 ", "startx  < width ", "starty < height",
			"endx < width ", "endy < height" })
	public boolean isValidMove(int startx, int starty, int endx, int endy) {
		return game.isValidMove(startx, starty, endx, endy);
	}

	@Requires({ "startx > 0", "starty > 0", "endx > 0", "endy >0 ", "startx  < width ", "starty < height",
			"endx < width ", "endy < height", "(startx != endx) && (starty != endy)" })
	public boolean makeMove(int startx, int starty, int endx, int endy) {
		return game.move(startx, starty, endx, endy, isDefensiveMode);
	}

	public int getNumMoves() {
		return game.getNumMoves();
	}

	public String getCurrentPlayer() {
		return game.getCurrentPlayer();
	}

	public void timerElapsed() {
		game.timerElapsed();
	}

	public int getHeight() {
		return game.getHeight();
	}

	public int getWidth() {
		return game.getWidth();
	}

	@Requires({ "x > 0", "y > 0" })
	@Ensures({ "result != null" })
	public Type[] getPieceType(int x, int y) {
		return game.getPieceType(x, y);
	}

	@Requires({ "x > 0", "y > 0" })
	@Ensures({ "result != null" })
	public Ground getBoardGround(int x, int y) {
		return game.getBoardGround(x, y);
	}

	public void startGame() {
		game.startGame(DEFAULTSIZE);
	}
	@Requires({ "x > 0", "y > 0" })
	@Ensures({ "result != null" })
	public IPiece getPiece(int x, int y) {
		return game.getPiece(x, y);
	}

	/**
	 * Checks specified location to see if its free to place a piece.
	 */
	@Requires({ "x > 0", "y > 0"})
	public boolean moveAvailable(int x, int y) {
		Ground ground = getBoardGround(x, y);

		if (ground instanceof GroundTree)
			return false;
		if (ground instanceof GroundBlocked)
			return false;

		return true;
	}

	/**
	 * Checks at specified location to see if there is a defensive shield.
	 */
	@Requires({ "x > 0", "y > 0"}) 
	public boolean isDefensive(int x, int y) {
		return getPiece(x, y).isDefensive();
	}

	/**
	 * Gets the move mode in text format.
	 */ 
	@Ensures({ "result != null" })
	public String getMoveMode() {
		if (isDefensiveMode)
			return "Defensive Mode.";
		return "Attactive Mode.";
	}
	@Requires({ "undoSteps > 0 && undoStep <= 3"}) 
	public void undoTurn(int undoSteps) {
		game.undoTurn(undoSteps);
	}

	public boolean isAllowedToUndo() {
		return game.isAllowedToUndo();
	}

	/**
	 * Saves the game to a location on disk.
	 * @param string Path to save game at. Make sure permissions allowed.
	 */
	@Requires({ "string != null"}) 
	public void saveGame(String string) {
		byte[] gameBytes = SerializationUtils.serialize(game);
		try (FileOutputStream fos = new FileOutputStream(string)) {
			fos.write(gameBytes);
		} catch (Exception e) {
			System.out.println("Error saving game: " + e.getMessage());
		}
	}

	/**
	 * Reads and loads a game file from disk.
	 * @param string Path to game file.
	 */
	@Requires({ "string != null" }) 
	public void openGame(String string) {
		byte[] gameBytes = null;
		try (FileInputStream fis = new FileInputStream(string)) {
			gameBytes = fis.readAllBytes();
		} catch (Exception e) {
			System.out.println("Error opening game: " + e.getMessage());
		}
		if (gameBytes == null || gameBytes.length == 0) {
			System.out.println("Error opening game. Read nothing from file.");
			return;
		}
		game = SerializationUtils.deserialize(gameBytes);
		isDefensiveMode = false;
	}

	/**
	 * Gets the root node of the persistent data structure.
	 */ 
	@Ensures({ "result != null" })
	public IStack<BoardSnapshot> getRootNode() {
		return game.rootOfStack;
	}

	/**
	 * Loads a previously played boardID.
	 */ 
	@Ensures({ "result != null" })
	public void LoadBoardID(int parseInt) {
		game.loadBoardID(parseInt);
	}
}
