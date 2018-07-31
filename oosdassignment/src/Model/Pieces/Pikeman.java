package Model.Pieces;

import com.google.java.contract.Requires;

import Model.PieceTypes.RangedPiece;

public class Pikeman extends RangedPiece { 
	@Requires({ "x > 0", "y > 0" }) 
	public Pikeman(int x, int y ) {
		super(x,y);
		type = Type.PIKEMAN;
		moveDistance = 2;
		power = 3;
		this.setPosition(x, y);
	}
}
