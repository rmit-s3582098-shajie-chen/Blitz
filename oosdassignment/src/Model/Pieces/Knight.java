package Model.Pieces;

import com.google.java.contract.Requires;

import Model.PieceTypes.RangedPiece;

/**
 * The knight has excellent move and power levels.
 * @author Liam
 */
public class Knight extends RangedPiece {

	@Requires({ "x > 0", "y > 0" }) 
	public Knight(int x, int y ) {
		super(x, y);
		type = Type.KNIGHT;
		moveDistance = 3;
		power = 3;
		this.setPosition(x, y);
	}

}
