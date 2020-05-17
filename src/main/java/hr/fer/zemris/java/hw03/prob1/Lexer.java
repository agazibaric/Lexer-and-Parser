package hr.fer.zemris.java.hw03.prob1;

/**
 * Class represents a lexical analyzer for language specified in assignment.
 * Input of lexical analyzer is original text.
 * Output of lexical analyzer is stream of tokens.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class Lexer {
	
	/**
	 * input text arranged in char array
	 */
	private char[] data;
	/**
	 * current token
	 */
	private Token token;
	/**
	 * index of first unprocessed <code>char</code> in </code>data</code>
	 */
	private int currentIndex;
	/**
	 * current lexer state
	 */
	private LexerState state = LexerState.BASIC;
	
	/**
	 * Constructor for creating new <code>Lexer</code>.
	 * 
	 * @param text text input that is processed
	 */
	public Lexer(String text) {
		if (text == null) 
			throw new IllegalArgumentException("Text must not be null");
		
		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Method used for setting next token from input data.
	 * 
	 * @return <code>Token</code> next token
	 */
	public Token nextToken() {
		if (this.state == LexerState.BASIC)
			setCurrentToken();
		else
			setCurrentTokenExtendedMode();
		
		return token;
	}
	
	/**
	 * Method returns current token.
	 * 
	 * @return <code>Token</code> current token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method used for setting new token when it's in BASIC mode
	 */
	private void setCurrentToken() {
		
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("There is no more tokens");
		
		skipWhitespaces();
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		//Word test
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			int beginningOfWord = currentIndex;
			while (true) {
				if(currentIndex >= data.length)
					break;
				
				if (data[currentIndex] == '\\') {
					if (currentIndex >= data.length - 1 || Character.isLetter(data[currentIndex + 1]))
						throw new LexerException("Invalid string input");

					data[currentIndex] = ' '; // replace '/' with ' ' so that we can later delete them from string
					currentIndex++;
				} else if (!Character.isLetter(data[currentIndex])) {
					break;
				}
				currentIndex++;
			}
			String word = new String(data, beginningOfWord, currentIndex - beginningOfWord).replace(" ", "");
			token = new Token(TokenType.WORD, word);
			return;
		}
		
		//Number test
		if (Character.isDigit(data[currentIndex])) {
			int beginningOfNumber = currentIndex;
			while(currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}
			String numberInput = new String(data, beginningOfNumber, currentIndex - beginningOfNumber);
			Long number;
			try {
				number = Long.parseLong(numberInput);
			} catch(NumberFormatException ex) {
				//Number is too big
				throw new LexerException("Entered number is too big. You entered:" + numberInput);
			}
			token = new Token(TokenType.NUMBER, number);
			return;
		}
		
		// Remaining char is symbol. 
		// Everything that is not a word, a number or a whitespace is a symbol
		token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
		currentIndex++;
	}
	
	/**
	 * Method used for setting new token when it's in EXTENDED mode
	 */
	private void setCurrentTokenExtendedMode() {
		
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("There is no more tokens");
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		while (true) {
			int beginningOfWord = currentIndex;
			while (currentIndex < data.length && !isWhitespace(data[currentIndex]) && data[currentIndex] != '#') {
				currentIndex++;
			}

			if (beginningOfWord == currentIndex) {
				
				if (currentIndex >= data.length) {
					token = new Token(TokenType.EOF, null);
					return;
				} else if (data[currentIndex] == '#') {
					token = new Token(TokenType.SYMBOL, Character.valueOf('#'));
					currentIndex++;
					return;
				}
				currentIndex++;
				continue;
			}

			String word = new String(data, beginningOfWord, currentIndex - beginningOfWord);
			token = new Token(TokenType.WORD, word);
			if(currentIndex < data.length && data[currentIndex] != '#')
				currentIndex++;
			return;
		}
	}
	
	/**
	 * Private method for skipping any whitespaces.
	 * That includes: ' ', '\n', '\t', '\r'.
	 */
	private void skipWhitespaces() {
		if (data.length == 0)
			return;
		while(currentIndex < data.length) {
			if (isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			break;	
		}
	}
	
	/**
	 * Method checks if given character is a whitespace.
	 * That includes: ' ', '\n', '\t', '\r'.
	 * @param c character that is checked
	 * @return  <code>true</code> if <code>c</code> is a whitespace, otherwise <code>false</code>
	 */
	private boolean isWhitespace(char c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t';
	}
	
	/**
	 * Method used for changing state of <code>Lexer</code>.
	 * 
	 * @param state new lexer state
	 */
	public void setState(LexerState state) {
		if (state == null) 
			throw new IllegalArgumentException("Lexer state must not be null");
		this.state = state;
	}
	
	/**
	 * Method returns current state of lexer.
	 * 
	 * @return <code>LexerState</code> of lexer
	 */
	public LexerState getState() {
		return state;
	}
	
}
