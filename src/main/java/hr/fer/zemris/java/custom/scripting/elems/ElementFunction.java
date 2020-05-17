package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class represents element that stores properties of function.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class ElementFunction extends Element {

	/**
	 * name of function
	 */
	private String name;
	
	/**
	 * Constructor for creating new <code>ElementFunction</code>.
	 * 
	 * @param name <code>String</code> name of function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Method returns function name.
	 * 
	 * @return <code>String</code> that represents function name
	 */
	public String getValue() {
		return name;
	}
	
}
