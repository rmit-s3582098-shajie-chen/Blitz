package Model.Pieces;

import com.google.java.contract.Requires;

import Model.PieceTypes.LeaderPiece;

/**
 * Role of the goldMerchant is to be brought to the top of the board.
 */
public class GoldMerchant extends LeaderPiece {

	@Requires({ "x > 0", "y > 0" }) 
	public GoldMerchant(int x, int y ) {
		super(x, y);
		type = Type.MERCHANT;
		moveDistance = 2;
		power = 0;
		this.setPosition(x, y);
	}

}
