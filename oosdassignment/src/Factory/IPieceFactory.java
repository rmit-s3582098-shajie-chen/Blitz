package Factory;

import Model.IPiece;

/**
 * Abstract factory interface that defines a framework for other factories. 
 * @author Liam
 */
public interface IPieceFactory {

	/**
	 * Creates a piece of leader type. 
	 */
	public abstract IPiece createLeaderPiece(int x, int y);

	/**
	 * Creates a piece of ranged type.
	 */
	public abstract IPiece createRangedPiece(int x, int y);

	/**
	 * Creates a piece of solider type.
	 */
	public abstract IPiece createSoldierPiece(int x, int y);
}
