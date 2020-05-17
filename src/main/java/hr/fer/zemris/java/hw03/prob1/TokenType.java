package hr.fer.zemris.java.hw03.prob1;

/**
 * Class represents different types of <code>Token</code>.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public enum TokenType {
	/**
	 * represents end of document
	 */
	EOF,
	/**
	 * represents one word
	 */
	WORD, 
	/**
	 * represents integer number
	 */
	NUMBER,
	/**
	 * represents other symbols that are not contained in words and numbers
	 */
	SYMBOL
}
