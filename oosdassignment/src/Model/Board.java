package Model;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

import Decorator.Shoes;
import Model.Game.Turn;
import Model.IPiece.Type;
import Model.Ground.GroundGrass;
import Model.Ground.GroundShoes;
import Model.Ground.Ground;

@Invariant({ "boardMatrix != null", "pieceMatrix != null" })
public class Board implements Serializable{
	
	/**
	 * The 2d array that holds the board environment (ground).
	 */
	Ground[][] boardMatrix;
	/**
	 * The 2d array that holds the pieces.
	 */
	IPiece[][] pieceMatrix;
	private int height;
	private int width;
	@Requires({ "h > 0", "w > 0" })
	public Board(int h, int w) {
		setHeight(h);
		setWidth(w);
		pieceMatrix = PieceGenerator.Generate(width, height);
		boardMatrix = BoardGenerator.Generate(pieceMatrix);
	}
	
	/**
	 * Creates a new Board using information from the specified snapshot.
	 * @param snapshot Input snapshot.
	 */
	public Board(BoardSnapshot snapshot) {
		setHeight(Model.DEFAULTSIZE);
		setWidth(Model.DEFAULTSIZE);
		pieceMatrix = SerializationUtils.clone(snapshot.pieceMatrix);
		boardMatrix = SerializationUtils.clone(snapshot.boardMatrix);
	}

	/**
	 * Generates a snapshot of the current state of the Board.
	 */
	@Ensures({ "result != null" })
	public BoardSnapshot getBoardSnapshot() {
		return new BoardSnapshot(this);
	}
	@Ensures({ "result != null" })
	public int getHeight() {
		return this.height;
	}

	@Requires("h > 0")
	@Ensures("height == h")
	public void setHeight(int h) {
		this.height = h;
	}

	@Requires("w > 0")
	@Ensures("width == w")
	public void setWidth(int w) {
		this.width = w;
	}
	@Ensures({ "result != null" })
	public int getWidth() {
		return this.width;
	}

	@Requires({ "x > 0", "y > 0", "x < width", "y < height" })
	@Ensures({ "unitCollection.get(y).get(x).getType() !=null" })
	public Type[] getPieceType(int x, int y) {
		return pieceMatrix[y][x].getType();
	}

	@Requires({ "x > 0", "y > 0", "x < width", "y < height" })
	@Ensures({ "unitCollection.get(y).get(x) !=null" })
	public IPiece getPiece(int x, int y) {
		return pieceMatrix[y][x];
	}
	
	@Requires({ "x > 0", "y > 0", "x < width", "y < height" })
	public Ground getBoardGround(int x, int y) {
		return boardMatrix[y][x];
	}

	@Requires({ "startx > 0", "starty > 0", "endx > 0", "endy >0 ", "startx  < width ", "starty < height",
			"endx < width ", "endy < height" })
	public void move(int startx, int starty, int endx, int endy, boolean defensive) {
		IPiece startPiece = pieceMatrix[starty][startx];
		
		// If we landed on a pair of shoes, decorate the piece with them.
		if (boardMatrix[endy][endx] instanceof GroundShoes) {
			// Add shoes decorator to unit.
			startPiece = new Shoes(startPiece);
			boardMatrix[endy][endx] = new GroundGrass();
			// Also remove the shoes from the ground.
		}
		
		IPiece endPiece = pieceMatrix[endy][endx];
		
		// If we're landing on one of our own teams pieces.
		if (Turn.getTeam(endPiece.getType()[0]) == Turn.getTeam(startPiece.getType()[0])) {
			System.out.println("Combining into group.");
			CompositePiece compPiece = new CompositePiece();
			compPiece.add(startPiece);
			compPiece.add(endPiece);
			startPiece = compPiece;
		}
		
		startPiece.setPosition(endx, endy);
		startPiece.setDefensive(defensive);

		// Move the unit to the position.
		pieceMatrix[endy][endx] = startPiece;
		// Clear previous position.
		pieceMatrix[starty][startx] = new Piece();
	}

	/**
	 * Searches for the BoardSquare where the specified unit is.
	 * 
	 * @param unitType
	 *            The type of specified unit.
	 * @return The BoardSquare where the unit is.
	 */
	@Requires({ "unitType != null" })
	public IPiece searchUnit(Type unitType) {
		for(int i = 0; i < pieceMatrix.length; i++) {
			for(int j = 0; j < pieceMatrix[0].length; j++) {
				if (pieceMatrix[i][j].getType()[0].equals(unitType)) {
					return pieceMatrix[i][j];
				}
			}
		}
		return null;
	}
}
