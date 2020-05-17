package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class that represents ECHO node and its properties.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class EchoNode extends Node {
	
	/**
	 * <code>Element</code> array of ECHO's elements
	 */
	private Element[] elements;

	/**
	 * Constructor for creating new <code>EchoNode</code>.
	 * 
	 * @param elements elements that are stored in <code>EchoNode</code>
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Method returns echonode's elements.
	 * 
	 * @return <code>Element[]</code> that <code>EchoNode</code> contains
	 */
	public Element[] getElements() {
		return elements;
	}

}
