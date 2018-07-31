package Model.PieceTypes;
 
import com.google.java.contract.Requires;

import Model.Piece;

public class RangedPiece extends Piece {
	@Requires({ "x > 0", "y > 0" }) 
	public RangedPiece(int x, int y) {
		super(x, y);
	}
}
