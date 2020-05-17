package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class that represents general form of node
 * that can store other nodes as its children nodes
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class Node {

	/**
	 * collection of children nodes
	 */
	private ArrayIndexedCollection nodes;

	/**
	 * Default constructor
	 */
	public Node() {
		super();
	}
	
	/**
	 * Method used for adding children nodes.
	 * 
	 * @param child <code>Node</code> that is added as a child
	 */
	public void addChildNode(Node child) {
		if(nodes == null)
			nodes = new ArrayIndexedCollection();
		nodes.add(child);
	}
	
	/**
	 * Method returns number of children that this node has stored.
	 * 
	 * @return number of children nodes
	 */
	public int numberOfChildren() {
		return nodes.size();
	}
	
	/**
	 * Method returns child node at given <code>index</code>.
	 * 
	 * @param index index at which child node is returned
	 * @return      <code>Node</code> child at given <code>index</code>
	 */
	public Node getChild(int index) {
		return (Node) nodes.get(index);
	}

}
