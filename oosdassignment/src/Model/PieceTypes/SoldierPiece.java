package Model.PieceTypes;
 
import com.google.java.contract.Requires;

import Model.Piece;

public class SoldierPiece extends Piece {
	@Requires({ "x > 0", "y > 0" }) 
	public SoldierPiece(int x, int y) {
		super(x, y);
	}
}
