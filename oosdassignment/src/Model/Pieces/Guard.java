package Model.Pieces;

import com.google.java.contract.Requires;

import Model.PieceTypes.SoldierPiece;

public class Guard extends SoldierPiece { 
	@Requires({ "x > 0", "y > 0" }) 
	public Guard(int x, int y ) {
		super(x, y);
		type = Type.GUARD;
		moveDistance = 3;
		power = 1 ;
		this.setPosition(x, y);
	}

}
