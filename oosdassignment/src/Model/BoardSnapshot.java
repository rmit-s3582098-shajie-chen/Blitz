package Model;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.google.java.contract.Requires;

import Model.Ground.Ground;

/**
 * BoardSnapshot holds a 'snapshot' of a specific board's state.s
 * @author Liam
 */
public class BoardSnapshot implements Serializable {

	/**
	 * Global counter for boardID.
	 */
	public static int boardIDCounter = 0;
	/**
	 * Each board snapshot gets a unique ID, mainly for display of alternative universes.
	 */
	public int boardID = 0;
	
	final Ground[][] boardMatrix;
	final IPiece[][] pieceMatrix;
	@Requires({ "board != null" }) 
	public BoardSnapshot(Board board) {
		// Provide deep copies.
		boardMatrix = SerializationUtils.clone(board.boardMatrix);
		pieceMatrix = SerializationUtils.clone(board.pieceMatrix);
		boardID = boardIDCounter++;
	}

}
