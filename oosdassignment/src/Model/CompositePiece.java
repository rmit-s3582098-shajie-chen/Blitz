package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.java.contract.Requires;

public class CompositePiece implements IPiece, Serializable{

	ArrayList<IPiece> childPieces = new ArrayList<IPiece>();

	/**
	 * Adds the specified piece to this composite.
	 */
	@Requires({ "piece != null" })
	public void add(IPiece piece) {
		childPieces.add(piece);
	}

	/**
	 * Removes the specified piece from this composite.
	 * @param piece Piece to remove.
	 */
	@Requires({ "piece != null" })
	public void remove(IPiece piece) {
		childPieces.remove(piece);
	}

	/* (non-Javadoc)
	 * @see Model.IPiece#getType()
	 * Returns all types of children in an array.
	 */
	@Override
	public Type[] getType() {
		
		ArrayList<Type> types = new ArrayList<Type>();
		
		for(int i = 0; i < childPieces.size(); i++) {
			types.addAll(Arrays.asList(childPieces.get(i).getType()));
		}
		Type[] typeArray = new Type[types.size()];
		types.toArray(typeArray);
		return typeArray;
	}
	@Requires({ "endx > 0", "endy > 0" })
	@Override
	public void setPosition(int endx, int endy) {
		for (IPiece piece : childPieces)
			piece.setPosition(endx, endy);
	}

	@Override
	public int getPositionY() {
		for (IPiece piece : childPieces)
			return piece.getPositionY();
		return 0;
	}

	@Override
	public boolean isDefensive() {
		for (IPiece piece : childPieces)
			return piece.isDefensive();
		return false;
	}

	@Override
	public void setDefensive(boolean defensive) {
		for (IPiece piece : childPieces)
			piece.setDefensive(defensive);
	}

	@Override
	public int getPower() {

		int maxPower = 0;

		for (IPiece piece : childPieces) {
			if (piece.getMoveDistance() > maxPower)
				maxPower = piece.getMoveDistance();
		}

		return maxPower;
	}

	@Override
	public int getMoveDistance() {
		int maxMoveDistance = 0;

		for (IPiece piece : childPieces) {
			if (piece.getMoveDistance() > maxMoveDistance)
				maxMoveDistance = piece.getMoveDistance();
		}

		return maxMoveDistance;
	}

}
