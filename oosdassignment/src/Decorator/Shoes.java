package Decorator;

import Model.IPiece;

/**
 * Increases move distance of decorated piece.
 * @author Liam
 */
public class Shoes extends PieceDecorator {

	/**
	 * Amount this decorator increases the move distance.
	 */
	public static int moveAddition = 1;  
	
	public Shoes(IPiece newPiece) {
		super(newPiece);
	}
		
	@Override
	public int getMoveDistance() {
		return decoratedPiece.getMoveDistance() + 1;
	}
}
