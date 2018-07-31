package Model;

import java.util.Random;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import Model.IPiece.Type;
import Model.Ground.*;

public class BoardGenerator {

	public static int edgeSize = 2;
	public static float treeChance = 0.15f;
	public static float shoeChance = 0.02f;
	/**
	 * Denotes how far away from the top and the bottom of the board to generate
	 * obstructions.
	 */
	public static int obstructionOffset = 3;

	@Requires({ "pieceMatrix != null" })
	@Ensures({ "result != null" })
	protected static Ground[][] Generate(IPiece[][] pieceMatrix) {

		int width = pieceMatrix.length;
		int height = pieceMatrix[0].length;

		Ground[][] board = new Ground[pieceMatrix.length][pieceMatrix[0].length];

		Random rand = new Random();

		// Initialize the different boardTypes.
		GroundBlocked groundBlocked = new GroundBlocked();
		GroundGrass groundGrass = new GroundGrass();
		GroundTree groundTree = new GroundTree();
		GroundShoes groundShoes = new GroundShoes();

		try {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {

					// Generate distances to each corner.
					int disTopLeft = Math.abs(0 - x) + Math.abs(0 - y);
					int disTopRight = Math.abs(0 - x) + Math.abs(height - 1 - y);
					int disBotLeft = Math.abs(width - 1 - x) + Math.abs(0 - y);
					int disBotRight = Math.abs(width - 1 - x) + Math.abs(height - 1 - y);

					// Generating the shape of the board.
					if (disTopLeft <= edgeSize || disTopRight <= edgeSize || disBotLeft <= edgeSize
							|| disBotRight <= edgeSize) {
						board[x][y] = groundBlocked.clone();
					} else {
						// Default to grass.
						board[x][y] = groundGrass.clone();

						// Generating the obstructions not on a piece.
						if (pieceMatrix[x][y].getType()[0] == Type.NONE) {
							// Also make sure they're within the offset
							if (Math.abs(x - width) > 3 && x > 3) {
								if (rand.nextFloat() < treeChance) {
									board[x][y] = groundTree.clone();
								} else if (rand.nextFloat() < shoeChance) {
									board[x][y] = groundShoes.clone();
								}
							}
						}
					}
				}
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return board;
	}

}
