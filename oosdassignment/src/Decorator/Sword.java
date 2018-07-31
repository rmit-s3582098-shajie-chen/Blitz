package Decorator;

import Model.IPiece;

/**
 * Increases power of decorated piece.
 * @author Liam
 */
public class Sword extends PieceDecorator {

	/**
	 * Amount this decorator increases the power.
	 */
	public static int powerAddition = 1; 
	
	public Sword(IPiece newPiece) {
		super(newPiece);
	}
	
	@Override
	public int getPower() {
		return decoratedPiece.getPower() + powerAddition;
	}
}
