package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element that stores constant integer value.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class ElementConstantInteger extends Element {

	/**
	 * <code>Long</code> value that element contains
	 */
	private Long value;
	
	/**
	 * Constructor for creating new <code>ElementConstantInteger</code>.
	 * 
	 * @param value <code>Long</code> value that is stored in element
	 */
	public ElementConstantInteger(Long value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
	/**
	 * Method returns value that element contains
	 * 
	 * @return <code>Long</code> value of element
	 */
	public Long getValue() {
		return value;
	}
	
}
