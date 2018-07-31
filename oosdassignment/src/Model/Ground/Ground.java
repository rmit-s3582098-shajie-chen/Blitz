package Model.Ground;

public abstract class Ground implements Cloneable, java.io.Serializable{
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * Clones the object based on the prototype design pattern.
	 */
	public Ground clone() throws CloneNotSupportedException {
		return (Ground)super.clone();
	}
}
