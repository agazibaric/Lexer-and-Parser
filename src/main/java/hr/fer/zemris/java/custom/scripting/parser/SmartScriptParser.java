package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer2;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer2State;
import hr.fer.zemris.java.custom.scripting.lexer.Token2;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType2;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Class represents a syntax analyzer for language specified in assignment.
 * Input of the syntax analyzer is stream of tokens obtained from lexical analyzer.
 * Output of the syntax analyzer is syntax tree.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class SmartScriptParser {
	
	/**
	 * original text that is analyzed
	 */
	String document;
	/**
	 * top node of syntax tree
	 */
	private DocumentNode mainNode;
	/**
	 * lexical analyzer used for tokenization of text 
	 */
	private Lexer2 lexer;
	/**
	 * stack used for implementing syntax tree
	 */
	private ObjectStack stack = new ObjectStack();
	
	/**
	 * Constructor used for creating new <code>SmartScriptParser</code>.
	 * 
	 * @param document original text that is analyzed
	 */
	public SmartScriptParser(String document) {
		if(document == null)
			throw new NullPointerException("Document must not be null");
		
		this.document = document;
		lexer = new Lexer2(document);
		mainNode = parse();
	}
	
	/**
	 * Method used for parsing text and constructing syntax tree
	 * 
	 * @return <code>DocumentNode</code> that represents syntax tree
	 */
	private DocumentNode parse() {
		lexer.nextToken();
		DocumentNode documentNode = new DocumentNode();
		stack.push(documentNode);

		while (true) {
			// If it's end of text, we are done with parsing
			if (isTokenOfType(TokenType2.EOF))
				break;
			
			// If lexer is in TEXT mode
			if (lexer.getState() == Lexer2State.TEXT) {
				if (!isTokenOfType(TokenType2.TEXT))
					throw new SmartScriptParserException("Invalid token type. Text was expected.");
				
				try {
					// Try if stack is not empty
					Node lastNode = (Node) stack.peek();
					lastNode.addChildNode(getTextNode());
				} catch(EmptyStackException ex) {
					throw new SmartScriptParserException("Invalid input. Too many END tags");
				}
				lexer.nextToken();
				// If next token is open curly bracket which represents beginning of TAG mode
				// Change lexer mode in TAG mode
				if (isTokenOfType(TokenType2.OPEN_BKRACKETS))
					lexer.setState(Lexer2State.TAG);

			} else {
				// If the lexer is in TAG mode
				if (isTokenOfType(TokenType2.EOF))
					throw new SmartScriptParserException("Invalid TAG structure.");

				// First token must be open curly bracket
				if (!isTokenOfType(TokenType2.OPEN_BKRACKETS))
					throw new SmartScriptParserException("Invalid TAG structure. Open curly brackets was expected.");
				lexer.nextToken();

				// Second token must be dollar symbol
				if (!isTokenOfType(TokenType2.DOLLAR))
					throw new SmartScriptParserException("Invalid TAG structure. '$' was expected.");
				lexer.nextToken();

				// Check what comes after dollar symbol
				if (lexer.getToken().getValue().toString().toUpperCase().equals("FOR")) {
					// FOR TAG
					lexer.nextToken();
					ForLoopNode forNode = getForLoopNode();
					try {
						// Try if stack is not empty
						Node lastNode = (Node) stack.peek();
						lastNode.addChildNode(forNode);
						stack.push(forNode);
					} catch (EmptyStackException ex) {
						throw new SmartScriptParserException("Invalid input. Too many END tags");
					}
				} else if (isTokenOfType(TokenType2.EQUALS)) {
					// ECHO TAG
					lexer.nextToken();
					EchoNode echoNode = getEchoNode();
					try {
						// Try if stack is not empty
						Node lastNode = (Node) stack.peek();
						lastNode.addChildNode(echoNode);
					} catch(EmptyStackException ex) {
						throw new SmartScriptParserException("Invalid input. Too many END tags");
					}
				} else if (lexer.getToken().getValue().toString().toUpperCase().equals("END")) {
					// END TAG
					// After 'END' must come '$'
					if (!(lexer.nextToken().getType() == TokenType2.DOLLAR))
						throw new SmartScriptParserException("Invalid END statment. '$' was expected");
					// After '$' must come '}'
					if (!(lexer.nextToken().getType() == TokenType2.CLOSE_BRACKETS))
						throw new SmartScriptParserException("Invalid END statment. '}' was expected");
					// After 'END' tag pop last node from stack
					try {
						stack.pop();
					} catch (EmptyStackException ex) {
						throw new SmartScriptParserException("Invalid input. Missing END tag");
					}
				} else {
					// Invalid tag input
					throw new SmartScriptParserException("Invalid TAG input");
				}
				// End of TAG, change state to TEXT mode
				lexer.setState(Lexer2State.TEXT);
				lexer.nextToken();
			}
		}
		
		if(stack.size() != 1) {
			throw new SmartScriptParserException("Invalid input! Too many END tags");
		}
		
		return documentNode;
	}
	
	/**
	 * Method used for getting <code>ForLoopNode</code>.
	 * It checks for validity of every token in FOR tag.
	 * 
	 * @return <code>ForLoopNode</code> that represents FOR tag
	 */
	private ForLoopNode getForLoopNode() {
		// First token in FOR tag must be variable
		if(!isTokenOfType(TokenType2.VAR))
			throw new SmartScriptParserException("Invalid FOR loop. Variable was expected");
		ElementVariable variable = new ElementVariable(lexer.getToken().getValue().toString());
		lexer.nextToken();
		ElementConstantInteger startExpression = getForLoopInteger();
		lexer.nextToken();
		ElementConstantInteger endExpression = getForLoopInteger();
		lexer.nextToken();
		ElementConstantInteger stepExpression = getForLoopInteger();
		lexer.nextToken();
		
		// After all characteristic elements of FOR tag must come '$' symbol
		if(!isTokenOfType(TokenType2.DOLLAR))
			throw new SmartScriptParserException("Invalid FOR loop. '$' was expected");
		lexer.nextToken();
		// After '$' symbol must come '}'
		if(!isTokenOfType(TokenType2.CLOSE_BRACKETS))
			throw new SmartScriptParserException("Invalid FOR loop. '}' was expected");
		
		return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
	}
	
	/**
	 * Method used for getting integer elements of FOR tag.
	 * 
	 * @return <code>ElementConstantInteger</code> that represents valid integer expression in FOR tag
	 */
	private ElementConstantInteger getForLoopInteger() {

		Object forElement = lexer.getToken().getValue();
		Long number = null;
		// Valid input is also "1", check for that
		if (forElement instanceof String) {
			String stringOfElement = (String) forElement;
			try {
				number = Long.parseLong(stringOfElement);
				return new ElementConstantInteger(number);
			} catch (NumberFormatException ex) {
				throw new SmartScriptParserException("Invalid FOR loop statment.\nValue is not of type Long");
			}
			// if it's not parsable then it's invalid input
		} else if (!(forElement instanceof Long)) {
			throw new SmartScriptParserException("Invalid FOR loop statment.\nValue is not of type Long");
		}
		number = (Long) forElement;
		return new ElementConstantInteger(number);
	}
	
	/**
	 * Method used for getting <code>EchoNode</code>.
	 * It checks validity of each token in ECHO tag.
	 * 
	 * @return <code>EchoNode</code> that represents ECHO tag
	 */
	private EchoNode getEchoNode() {
		ArrayIndexedCollection elems = new ArrayIndexedCollection();
		
		while(true) {
			Token2 token = lexer.getToken();
			TokenType2 type = token.getType();
			try {
				// Variable token
				if (type == TokenType2.VAR) {
					ElementVariable var = new ElementVariable(token.getValue().toString());
					elems.add(var);
					lexer.nextToken();
					continue;
				}
				// Integer number token
				if (type == TokenType2.INTEGER) {
					ElementConstantInteger number = new ElementConstantInteger((Long) token.getValue());
					elems.add(number);
					lexer.nextToken();
					continue;
				}
				// Double number token
				if (type == TokenType2.DOUBLE) {
					ElementConstantDouble number = new ElementConstantDouble((Double) token.getValue());
					elems.add(number);
					lexer.nextToken();
					continue;
				}
				// Text token ("text")
				if (type == TokenType2.TEXT) {
					ElementString string = new ElementString(token.getValue().toString());
					elems.add(string);
					lexer.nextToken();
					continue;
				}
				// Function token ("@" at the beginning, next token must be valid function name - VAR token)
				if (type == TokenType2.AT) {
					lexer.nextToken();
					if (lexer.getToken().getType() != TokenType2.VAR)
						throw new SmartScriptParserException("Invalid function name");

					ElementFunction function = new ElementFunction(lexer.getToken().getValue().toString());
					elems.add(function);
					lexer.nextToken();
					continue;
				}
				// Operator token (+, -, *, /, ^)
				if (type == TokenType2.OPERATOR) {
					ElementOperator operator = new ElementOperator(token.getValue().toString());
					elems.add(operator);
					lexer.nextToken();
					continue;
				}
				// At the end of TAG must come '$' and then '}'
				if (type == TokenType2.DOLLAR) {
					if (!(lexer.nextToken().getType() == TokenType2.CLOSE_BRACKETS))
						throw new SmartScriptParserException("Invalid ECHO tag. Closed curly bracked was expeceted");
					break;
				}
				// if it's not recognized, it is invalid input in ECHO tag
				throw new SmartScriptParserException("Invalid input in ECHO tag. Input was: " + token.getValue());
			} catch (LexerException ex) {
				throw new SmartScriptParserException(ex.getMessage());
			}
		}
		
		Element[] elements = fillElement(elems);
		return new EchoNode(elements);
	}
	
	/**
	 * Method used for filling <code>Element</code> array
	 * with given collection's elements
	 * 
	 * @param coll <code>ArrayIndexedCollection</code> whose elements are copied
	 * @return <code>Element[]</code> that contains every element from given collection
	 */
	private Element[] fillElement(ArrayIndexedCollection coll) {
		Element[] elements;
		int size = coll.size();
		elements = new Element[size];
		for(int i = 0; i < size; i++) {
			elements[i] = (Element) coll.get(i);
		}
		return elements;
	}
	
	/**
	 * Method used for getting <code>TextNode</code> from token
	 * 
	 * @return <code>TextNode</code> that represents TEXT element
	 */
	private TextNode getTextNode() {
		TextNode textNode = new TextNode(lexer.getToken().getValue().toString());
		return textNode;
	}
	
	/**
	 * Method that checks if a current token is of a given type.
	 * 
	 * @param type token type whose equality is checked
	 * @return     <code>true</code> if current token type is equal to given token type, otherwise <code>false</code>
	 */
	private boolean isTokenOfType(TokenType2 type) {
		return lexer.getToken().getType() == type;
	}
	
	/**
	 * Method that returns node that represents syntax tree of given text.
	 * 
	 * @return <code>DocumentNode</code> that represents syntax tree of given text.
	 */
	public DocumentNode getDocumentNode() {
		return mainNode;
	}
	
	
}
