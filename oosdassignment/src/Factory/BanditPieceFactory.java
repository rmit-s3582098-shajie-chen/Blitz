package Factory;

import Model.IPiece;
import Model.Pieces.Assassin;
import Model.Pieces.Knight;
import Model.Pieces.Wizard;

/**
 * This factory makes pieces only of the bandit family.
 * @author Liam
 */
public class BanditPieceFactory implements IPieceFactory {

	
	@Override
	public IPiece createLeaderPiece(int x, int y ) {
		return new Knight( x,  y );
	}

	@Override
	public IPiece createRangedPiece(int x, int y ) {
		return new Wizard( x,  y );
	}

	@Override
	public IPiece createSoldierPiece(int x, int y ) {
		return new Assassin( x,  y );
	}

}
