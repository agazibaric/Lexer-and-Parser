package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class represents element that stores properties of string input.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class ElementString extends Element {

	/**
	 * string value of element
	 */
	private String value;
	
	/**
	 * Constructor for creating new <code>ElementString</code>.
	 * 
	 * @param value <code>String</code> value of element
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return value;
	}
	
	/**
	 * Method returns string value of element.
	 * 
	 * @return <code>String</code> that element contains.
	 */
	public String getValue() {
		return value;
	}
	
}
