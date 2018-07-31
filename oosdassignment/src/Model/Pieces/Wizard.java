package Model.Pieces;

import com.google.java.contract.Requires;

import Model.PieceTypes.RangedPiece;

public class Wizard extends RangedPiece {
	@Requires({ "x > 0", "y > 0" }) 
	public Wizard(int x, int y) {
		super(x, y);
		type = Type.WIZARD;
		moveDistance = 2;
		power = 2;
		this.setPosition(x, y);
	}

}
