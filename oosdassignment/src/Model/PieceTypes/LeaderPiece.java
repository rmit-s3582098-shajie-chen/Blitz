package Model.PieceTypes;

import com.google.java.contract.Requires;

import Model.Piece;

public class LeaderPiece extends Piece {
	@Requires({ "x > 0", "y > 0" }) 
	public LeaderPiece(int x , int y ) {
		super(x, y);
	}
}
