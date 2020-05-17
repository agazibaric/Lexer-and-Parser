package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class represents different types of <code>Token2</code>.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public enum TokenType2 {
	/**
	 * represents end of document
	 */
	EOF,
	/**
	 * represents <code>String</code> input
	 */
	TEXT,
	/**
	 * represents variable
	 */
	VAR,
	/**
	 * represents integer
	 */
	INTEGER,
	/**
	 * represents floating point number
	 */
	DOUBLE,
	/**
	 * represents keywords that are specific for language
	 */
	KEYWORD,
	/**
	 * represents arithmetic operators
	 */
	OPERATOR,
	/**
	 * represents open curly bracket
	 */
	OPEN_BKRACKETS,
	/**
	 * represents closed curly bracket
	 */
	CLOSE_BRACKETS,
	/**
	 * represents different symbols that are not operators
	 */
	
	/**
	 * represents dollar symbol '$'
	 */
	DOLLAR,
	/**
	 * represents AT symbol '@'
	 */
	AT,
	/**
	 * represents equals symbol '='
	 */
	EQUALS
}