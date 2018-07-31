package PersistentDataStructure;

import java.util.ArrayList;

import Model.BoardSnapshot;

/**
 * Custom written stack interface for our persistent data structure.
 * @author Liam
 */
public interface IStack<T> {
	/**
	 * Adds the element to a new stack.
	 * @param element Element to add to the stack.
	 * @return New stack with element added.
	 */
	IStack<T> Push(T element);

	/**
	 * Return the previous stack, since element is removed.
	 * 
	 * @return Previous stack.
	 */
	IStack<T> Pop();

	/**
	 * Checks the element out at the top of the stack.
	 * @return Element at the top of the stack.
	 */
	T Peek();

	/**
	 * Checks whether the stack is empty or not.
	 */
	boolean isEmpty();

	/**
	 * Performs a recursive search through the stack, saving as it goes to output the whole stack.
	 */
	ArrayList<BoardSnapshot> getStack();
	
	
	/**
	 * Gets a list of depth numbers corresponding to the stack.
	 */
	ArrayList<Integer> getDepthList();
	
	/**
	 * Gets a list of leaf numbers corresponding to the stack.
	 */
	ArrayList<Integer> getLeafList();
	
	
	/**
	 * Gets child stacks.
	 */
	ArrayList<IStack<T>> getNextStacks();
}
