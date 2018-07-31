package PersistentDataStructure;

import java.io.Serializable;
import java.util.ArrayList;

import Model.BoardSnapshot;

/**
 * Persistent stack has qualities of a stack, but also works similar to a linked list.
 * Each BoardSnapshot is immutable (see lecture notes)
 * @author Liam
 */
public abstract class PersistentStack<T> implements IStack<T>, Serializable {

	public static ArrayList<BoardSnapshot> outputList = new ArrayList<BoardSnapshot>();
	public static ArrayList<Integer> depthList = new ArrayList<Integer>();
	public static ArrayList<Integer> leafList = new ArrayList<Integer>();
			
	public static int depth = 0;
	public static int leaf = 0;
	
	/**
	 * Points to stack children.
	 */
	public final ArrayList<IStack<T>> nextStacks = new ArrayList<IStack<T>>();

	public IStack<T> Push(T element) {
		nextStacks.add(new LinkNode<T>(this, element));
		return nextStacks.get(nextStacks.size() - 1);
	}
	
	public ArrayList<Integer> getDepthList(){
		return depthList;
	}
	
	public ArrayList<Integer> getLeafList(){
		return leafList;
	}
	
	public ArrayList<IStack<T>> getNextStacks() {
		return nextStacks;
	}
}
