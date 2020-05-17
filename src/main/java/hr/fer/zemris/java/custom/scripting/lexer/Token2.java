package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class represents token in lexical analysis.
 * Token is lexical unit that groups one or more consecutive symbols from input text.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class Token2 {

	/**
	 * type of token
	 */
	private TokenType2 type;
	/**
	 * value that token represents
	 */
	private Object value;
	
	/**
	 * Constructor for creating new <code>Token2</code>.
	 * 
	 * @param type  <code>TokenType2</code> type of token
	 * @param value <code>Object</code> that token stores
	 */
	public Token2(TokenType2 type, Object value) {
		if (type == null)
			throw new IllegalArgumentException("Token type must not be null");
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Method returns value that token contains.
	 * 
	 * @return <code>Object</code> value of token
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Method returns token type.
	 * 
	 * @return <code>TokenType2</code> type of this token.
	 */
	public TokenType2 getType() {
		return type;
	}
	
	
	
}
