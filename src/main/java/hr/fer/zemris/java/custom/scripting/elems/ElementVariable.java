package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class represents element that stores properties of variable.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class ElementVariable extends Element {

	/**
	 * name of variable
	 */
	private String name;
	
	/**
	 * Constructor for creating new <code>ElementVariable</code>.
	 * 
	 * @param name <code>String</code> name of variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Method returns variable name.
	 * 
	 * @return <code>String</code> that represents variable name
	 */
	public String getName() {
		return name;
	}
	
}
