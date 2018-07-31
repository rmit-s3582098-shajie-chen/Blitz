package PersistentDataStructure;

import java.util.ArrayList;

import Model.BoardSnapshot;

public class RootNode<T> extends PersistentStack<T>{

	@Override
	public IStack<T> Pop() {
		throw new IllegalStateException("Can't do this on an empty stack.");
	}

	@Override
	public T Peek() {
		throw new IllegalStateException("Can't do this on an empty stack.");
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
	
	public ArrayList<BoardSnapshot> getStack() {
		outputList.clear();
		depthList.clear();
		leafList.clear();
		depth = 0;
		leaf = 0;
		for(IStack<T> stack : nextStacks) {
			stack.getStack();
		}
		return outputList;
	}

}
