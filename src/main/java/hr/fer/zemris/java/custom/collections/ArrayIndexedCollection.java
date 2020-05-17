package hr.fer.zemris.java.custom.collections;

/**
 * Class represents collection that uses array to store elements.
 * Duplicate elements are allowed.
 * Storage of <code>null</code> references is not allowed.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 * 
 */
public class ArrayIndexedCollection extends Collection {
	
	/**
	 * represents current number of elements in collection
	 */
	private int size;
	/**
	 * represents current maximum size of collection
	 */
	private int capacity;
	/**
	 * represents array where collection stores objects
	 */
	private Object[] elements;
	/**
	 * default capacity is used when initial capacity of stack is not given
	 */
	private static final int DEFAULT_CAPACITY = 16;
	/**
	 * used for resizing collection capacity
	 */
	private static final int CAPACITY_MULTIPLIER = 2;
	
	/**
	 * Constructor that is used when initial capacity is not given.
	 * It uses default capacity and creates new <code>ArrayIndexedCollection</code>
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor that is used when initial capacity is given.
	 * 
	 * @param initialCapacity initial capacity of collection
	 * @throws <code>IllegalArgumentException</code> if given initial capacity is less that one
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Capacity must not be less then one.");
		initCollection(initialCapacity);
	}

	/**
	 * Constructor that is used for creating new <code>ArrayIndexedCollection</code>
	 * that will contain all elements of given <code>collection</code>
	 * with no given initial capacity.
	 * 
	 * @param collection collection whose elements are stored in newly created collection
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor that is used for creating new <code>ArrayIndexedCollection</code>
	 * that will contain all elements of given <code>collection</code>
	 * with given initial capacity.
	 * Capacity of newly created collection will be the larger of two capacities (given collection's capacity and initial capacity).
	 * 
	 * @param collection       collection whose elements are stored in newly created collection
	 * @param initialCapacity  initial capacity of collection
	 * @throws                 <code>NullPointerException</code> if given collection is <code>null</code>
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if (collection == null)
			throw new NullPointerException();

		int capacity = collection.size() > initialCapacity ? collection.size() : initialCapacity;
		initCollection(capacity);
		addAll(collection);
	}
	
	/**
	 * Private method for initializing collection
	 * 
	 * @param capacity capacity of collection
	 */
	private void initCollection(int capacity) {
		this.capacity = capacity;
		elements = new Object[capacity];
		size = 0;
	}
	
	/**
	 * Checks if collection is empty.
	 * 
	 * @return returns <code>true</code> only if the collection does not contain any elements,
	 *                 otherwise returns <code>false<code>
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns size of collection.
	 * 
	 * @return returns the number of currently stored objects in this collection
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Adds the given object into this collection.
	 * Average complexity of this method is O(1).
	 * 
	 * @param value object that is added to this collection
	 */
	public void add(Object value) {
		insert(value, size);
	}
	
	/**
	 * Inserts the given <code>value</code> at the given <code>position</code> in array.
	 * Average complexity of this method is O(n).
	 * 
	 * @param value    object that is added to this collection
	 * @param position index at which the object is added
	 * @throws         <code>NullPointerException</code> if the given <code>value</code> is <code>null</code>
	 * @throws	       <code>IndexOutOfBoundsException</code> if position is not valid
	 */
	public void insert(Object value, int position) {
		if (value == null)
			throw new NullPointerException();
		if (position < 0 || position > size)
			throw new IndexOutOfBoundsException("You entered: " + position);

		reallocateElements();
		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Searches the collection and returns index of the first occurrence of the given <code>value</code>
	 * or -1 if the <code>value</code> is not found. If the given <code>value</code> is <code>null</code> it returns -1.
	 * 
	 * @param value object that is searched for in this collection
	 * @return      index of the given <code>value<code> or -1
	 * 				if the collection does not contain given <code>value</code>
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;

		for (int index = 0; index < size; index++) {
			if (elements[index].equals(value))
				return index;
		}
		return -1;
	}
	
	/**
	 * Method that is used for reallocating elements with larger capacity only if necessary.
	 * Capacity is multiplied by <code>CAPACITY_MULTIPLIER<code>.
	 */
	private void reallocateElements() {
		if (size >= capacity) {
			capacity *= CAPACITY_MULTIPLIER;
			Object[] newElements = new Object[capacity];

			for (int i = 0; i < size; i++) {
				newElements[i] = elements[i];
			}
			elements = newElements;
		}
	}
	
	/**
	 * Method used for checking if collection contains given element.
	 * 
	 * @param value the object whose presence in the collection is checked
	 * @return <code>true</code> only if the collection contains given <code>value</code>, otherwise <code>false</code>
	 */
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}
	
	/**
	 * Returns the object that is stored in backing array at position <code>index</code>.
	 * Valid indexes are from zero to size (not including).
	 * 
	 * @param  index index at which object is returned
	 * @return object at given index
	 * @throws IndexOutOfBoundsException if given index is not in valid range
	 */
	public Object get(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("You entered: " + index);
		return elements[index];
	}
	
	/**
	 * Removes element at specified <code>index</code> from the collection.
	 * 
	 * @param index index at which element is removed
	 * @throws <code>IndexOutOfBoundsException</code> if <code>index</code> is not valid
	 */
	public void remove(int index) {
		if(index < 0 || index >= size) 
			throw new IndexOutOfBoundsException("You entered: " + index);
		
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
	}
	
	/**
	 * Method removes given <code>Object</code> element from collection
	 * only if collection contains the given element.
	 * 
	 * @param  value the object which is removed from collection
	 * @return <code>true</code> only if the collection contains given <code>value</code>, otherwise <code>false</code>
	 * @throws <code>IllegalArgumentException</code> if given <code>value</code> is <code>null</code>
	 */
	public boolean remove(Object value) {
		if(value == null)
			throw new IllegalArgumentException("Value must not be null");
		
		for (int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection. 
	 * The order in which elements will be sent is undefined in this class.
	 * 
	 * @return array of objects that this collection contains
	 */
	public Object[] toArray() {
		Object[] newElements = new Object[size];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		return newElements;
	}
	
	/**
	 * Method calls <code>processor.process()<code> for each element of this collection.
	 * The order in which elements will be sent is undefined in this class.
	 * 
	 * @param processor instance of <code>Processor</code> class
	 */
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
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
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
}
