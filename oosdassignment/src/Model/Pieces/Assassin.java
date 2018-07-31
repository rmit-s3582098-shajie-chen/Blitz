package Model.Pieces;

import com.google.java.contract.Requires;

import Model.PieceTypes.SoldierPiece; 

/**
 * Assassins move quickly, but have lower power making them weak.
 * @author Liam
 */
public class Assassin extends SoldierPiece {

	@Requires({ "x > 0", "y > 0"}) 
	public Assassin(int x, int y ) {
		super(x, y);
		type = Type.ASSASSIN;
		moveDistance = 4;
		power = 1;
		this.setPosition(x, y);
	}
	
}