package PersistentDataStructure;

import java.util.ArrayList;

import Model.BoardSnapshot;

public class LinkNode<T> extends PersistentStack<T> {

	private final IStack<T> previous;
	private final T element;

	public LinkNode(IStack<T> previousStack, T element) {
		this.previous = previousStack;
		this.element = element;
	}

	@Override
	public IStack<T> Pop() {
		return previous;
	}

	@Override
	public T Peek() {
		return element;
	}

	@Override
	public boolean isEmpty() {
		// Non-empty stack is never empty
		return false;
	}
	
	@Override
	public ArrayList<BoardSnapshot> getStack() {
		
		outputList.add((BoardSnapshot) element);
		depthList.add(depth);
		leafList.add(leaf);
		
		depth++;		
		
		for(IStack<T> stack : nextStacks) {
			stack.getStack();
			depth--;
			leaf++;
		}
		
		return null;
	}

}
