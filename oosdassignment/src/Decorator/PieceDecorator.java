package Decorator;

import java.io.Serializable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import Model.IPiece;

/**
 * Abstract class to define a decorator that wraps IPiece to provide additional functionalities during runtime.
 * @author Liam
 */
public abstract class PieceDecorator implements IPiece, Serializable{

	IPiece decoratedPiece;
	
	public PieceDecorator(IPiece newPiece){
		decoratedPiece = newPiece;
	}

	public Type[] getType() {
		return decoratedPiece.getType();
	}
	@Requires({ "endx > 0", "endy > 0" })
	@Ensures({ "result != null" })
	public void setPosition(int endx, int endy) {
		decoratedPiece.setPosition(endx, endy);
	}

	public int getPositionY() {
		return decoratedPiece.getPositionY();
	}
	
	public boolean isDefensive() {
		return decoratedPiece.isDefensive();
	}

	public void setDefensive(boolean defensive) {
		decoratedPiece.setDefensive(defensive);
	}

	public int getPower() {
		return decoratedPiece.getPower();
	}

	public int getMoveDistance() {
		return decoratedPiece.getMoveDistance();
	}
	
}
