package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class represents element that stores properties of operator.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class ElementOperator extends Element {

	/**
	 * symbol of operator
	 */
	private String symbol;
	
	/**
	 * Constructor for creating new <code>ElementOperator</code>.
	 * 
	 * @param symbol <code>String</code> that represents symbol of operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Method returns operator's name.
	 * 
	 * @return <code>String</code> that represents operator's name
	 */
	public String getValue() {
		return symbol;
	}
	
}
