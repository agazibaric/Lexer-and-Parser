package hr.fer.zemris.java.custom.collections;

/**
 * Class represents stack collection 
 * that uses LIFO ("last in, first out") method for organizing and manipulating data.

 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class ObjectStack {
	
	/**
	 * collection that is used as adaptee for storing objects
	 */
	private ArrayIndexedCollection elements;
	
	/**
	 * Constructor for creating new <code>ObjectStack</code>
	 */
	public ObjectStack() {
		super();
		elements = new ArrayIndexedCollection();
	}
	
	/**
	 * Method checks if the stack is empty
	 * 
	 * @return <code>true</code> only if the stack does not contain any elements,
	 *                 otherwise returns <code>false<code>
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * Returns size of stack.
	 * 
	 * @return returns the number of currently stored objects in the stack
	 */
	public int size() {
		return elements.size();
	}
	
	/**
	 * Pushes given <code>value</code> on the stack.
	 * Complexity of this method is O(1).
	 * 
	 * @param value value that is pushed on the stack
	 */
	public void push(Object value) {
		elements.add(value);
	}
	
	/**
	 * Removes last value pushed on stack and returns it.
	 * Complexity of this method is O(1).
	 * 
	 * @return <code>Object</code> element that is last pushed on the stack
	 * @throws <code>EmptyStackException</code> if stack is empty
	 */
	public Object pop() {
		if (isEmpty())
			throw new EmptyStackException();

		int index = size() - 1;
		Object element = elements.get(index);
		elements.remove(index);
		return element;
	}
	
	/**
	 * Returns the last element placed on stack but does not delete it from the stack.
	 * 
	 * @return <code>Object</code> element that is last pushed on the stack
	 * @throws <code>EmptyStackException</code> if stack is empty
	 */
	public Object peek() {
		if (isEmpty())
			throw new EmptyStackException();

		return elements.get(size() - 1);
	}
	
	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		elements.clear();
	}
	
}
