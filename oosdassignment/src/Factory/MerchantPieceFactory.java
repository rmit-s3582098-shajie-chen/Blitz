package Factory;

import Model.IPiece;
import Model.Pieces.GoldMerchant;
import Model.Pieces.Guard;
import Model.Pieces.Pikeman;

/**
 * This factory makes only pieces of the merchant family.
 * @author Liam
 */
public class MerchantPieceFactory implements IPieceFactory {

	@Override
	public IPiece createLeaderPiece(int x, int y ) {
		return new GoldMerchant( x,  y );
	}

	@Override
	public IPiece createRangedPiece(int x, int y ) {
		return new Pikeman( x,  y );
	}

	@Override
	public IPiece createSoldierPiece(int x, int y ) {
		return new Guard( x,  y );
	}

}
