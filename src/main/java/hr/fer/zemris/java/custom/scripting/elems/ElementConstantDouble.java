package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element that stores constant <code>double</code> value
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class ElementConstantDouble extends Element {

	/**
	 * <code>double</code> value that element contains
	 */
	private double value;
	
	/**
	 * Constructor for creating new <code>ElementConstantDouble</code>.
	 * 
	 * @param value <code>double</code> value that is stored in element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
	/**
	 * Method returns value that element contains
	 * 
	 * @return <code>double</code> value of element
	 */
	public double getValue() {
		return value;
	}
	
}
