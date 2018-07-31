package Model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import Factory.BanditPieceFactory;
import Factory.MerchantPieceFactory;
import Factory.IPieceFactory;

/**
 * Generate the layout of pieces according to it's public variables.
 * 
 * @author Liam
 */
public class PieceGenerator {

	/**
	 * Location of the ranged pieces relative to the leader.
	 */
	public int rangedLoc = 1;

	/**
	 * Location of the soldier pieces relative to the leader.
	 */
	public int soldierLoc = 2;

	/**
	 * Creates a board, generating pieces and the background.
	 * 
	 * @param width
	 *            Desired width of the board (in cells).
	 * @param height
	 *            Desired height of the board (in cells).
	 * @return The generated board.
	 */
	@Requires({ "width > 0", "height > 0" }) 
	@Ensures({ "result != null" })
	public static IPiece[][] Generate(int width, int height) {
		int halfWidth = (int) Math.floor(width / 2);
		IPiece[][] pieces = new IPiece[height][width];

		// Initialize the piece matrix.
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				pieces[w][h] = new Piece(w, h);
			}
		}

		IPieceFactory pieceFactory;

		for (int i = 0; i < 2; i++) {
			pieceFactory = (i == 0) ? new MerchantPieceFactory() : new BanditPieceFactory();

			// Set the correct row heights for each team's pieces.
			int leaderPos = (i == 0) ? height - 1 : 0;
			int rangedSoldierPos = (i == 0) ? height - 2 : 1;

			for (int h = 0; h < height; h++) {
				for (int w = 0; w < width; w++) {

					/**
					 * For each team generates a layout like this: 
					 * SOLDIER RANGED ------ RANGED SOLDIER 
					 * ------- ------ LEADER ------ -------
					 */

					if (h == leaderPos && w == halfWidth) {
						// Always generates a leader in the middle.
						pieces[h][w] = pieceFactory.createLeaderPiece(w, h);
					} else if (h == rangedSoldierPos) {
						if (w == halfWidth - 1 || w == halfWidth + 1) {
							// Always generates ranged piece closest to leader.
							pieces[h][w] = pieceFactory.createRangedPiece(w, h);
						} else if (w == halfWidth - 2 || w == halfWidth + 2) {
							// Always generates soldier piece farthest from leader.
							pieces[h][w] = pieceFactory.createSoldierPiece(w, h);
						}
					}
				}
			}
		}

		return pieces;
	}

}
