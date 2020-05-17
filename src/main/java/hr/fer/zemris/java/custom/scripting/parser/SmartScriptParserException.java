package hr.fer.zemris.java.custom.scripting.parser;


/**
 * Class represents an exception that <code>SmartScriptParser</code> uses
 * when it encounters invalid token
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {
	
	/**
	 * default serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor that accepts message about situation that occurred during process of parsing
	 * 
	 * @param message message that describes what went wrong during process of parsing
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
