package Model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public class Piece implements IPiece, java.io.Serializable {

	protected Type type = Type.NONE;
	protected int moveDistance = 0;
	protected int power = 0;
	protected boolean isDefensive = false;

	private int x = -1;
	private int y = -1;

	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
		type = Type.NONE;
	}

	public int getMoveDistance() {
		if (isDefensive) {
			return (int) Math.ceil(moveDistance / 2f);
		}
		return moveDistance;
	}

	public Piece() {
		type = Type.NONE;
	}

	public Type[] getType() {
		return new Type[] {type};
	}

	@Requires({ "x > 0", "y > 0" })
	@Ensures({ "this.x = x", "this.y = y" })
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getPositionY() {
		return y;
	}

	@Override
	public void setDefensive(boolean defensive) {
		isDefensive = defensive;
	}

	public boolean isDefensive() {
		return isDefensive;
	}

	@Override
	public int getPower() {
		if (isDefensive) {
			return power + 1;
		}
		return power;
	}
}
