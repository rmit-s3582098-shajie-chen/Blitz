package Model;

public interface IPiece{
	
	/**
	 * All possible piece types.
	 */
	public static enum Type {
		NONE, MERCHANT, GUARD, PIKEMAN, ASSASSIN, WIZARD, KNIGHT
	}

	/**
	 * Gets the type(s) of the piece, usually just one but sometimes the piece has multiple types.
	 */
	public Type[] getType();

	/**
	 * Updates the position of this piece.
	 */
	public void setPosition(int endx, int endy);

	/**
	 * Gets the Y position of the piece.
	 */
	public int getPositionY();
	
	/**
	 * Gets whether the unit is in defensive mode or not.
	 */
	public boolean isDefensive();

	/**
	 * Sets the units defensive state.
	 */
	public void setDefensive(boolean defensive);

	/**
	 * Gets the power level of the unit.
	 */
	public int getPower();

	/**
	 * Gets the move distance of the unit.
	 */
	public int getMoveDistance();

}
