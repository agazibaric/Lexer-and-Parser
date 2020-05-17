package hr.fer.zemris.java.custom.collections;

/**
 * This class represents some general collection of objects.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class Collection {
	
	/**
	 * Default constructor
	 */
	protected Collection() {
		super();
	}
	
	/**
	 * Method checks if collection is empty
	 * 
	 * @return returns <code>true</code> only if the collection contains given value, otherwise returns <code>false</code>
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns size of collection
	 * 
	 * @return returns the number of currently stored objects in this collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection
	 * 
	 * @param value object that is being added to this collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if collection contains given value
	 * 
	 * @param value the object whose presence in the collection is checked
	 * @return <code>true</code> only if the collection contains given <code>value</code>, otherwise <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * 
	 * @param value the object which is removed from collection
	 * @return <code>true</code> only if the collection contains given <code>value</code>, otherwise <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection. 
	 * The order in which elements will be sent is undefined in this class.
	 * 
	 * @return array of objects that this collection contains
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls <code>processor.process()<code> for each element of this collection.
	 * The order in which elements will be sent is undefined in this class.
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection.
	 * This other collection remains unchanged.
	 * 
	 * @param other the collection whose elements are added to this collection
	 */
	public void addAll(Collection other) {
		
		class addElementsProcessor extends Processor {	
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new addElementsProcessor());
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		
	}
}
