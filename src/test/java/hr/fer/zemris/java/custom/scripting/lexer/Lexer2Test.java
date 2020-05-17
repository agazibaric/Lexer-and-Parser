package hr.fer.zemris.java.custom.scripting.lexer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw03.prob1.LexerException;

public class Lexer2Test {

	@Test
	public void testNotNull() {
		Lexer2 lexer = new Lexer2("");
		Assert.assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		new Lexer2(null);
	}
	
	@Test
	public void testEmpty() {
		Lexer2 lexer = new Lexer2("");
		Assert.assertEquals("Empty input must generate only EOF token.", TokenType2.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		Lexer2 lexer = new Lexer2("");
		Token2 token = lexer.nextToken();
		Assert.assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		Assert.assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}
	
	@Test(expected = LexerException.class)
	public void testRadAfterEOF() {
		Lexer2 lexer = new Lexer2("");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testJustText() {
		String textInput = loader("document1.txt");
		Lexer2 lexer = new Lexer2(textInput);

		Token2 correctData[] = {
			new Token2(TokenType2.TEXT, "This is sample text."),
			new Token2(TokenType2.EOF, null)
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTextAndEchoTag() {
		String textInput = loader("document2.txt");
		Lexer2 lexer = new Lexer2(textInput);

		Token2 correctData1[] = {
			new Token2(TokenType2.TEXT, "This is sample text.\r\n"),
			new Token2(TokenType2.OPEN_BKRACKETS, Character.valueOf('{'))
		};
		Token2 correctData2[] = {
				new Token2(TokenType2.DOLLAR, Character.valueOf('$')),
				new Token2(TokenType2.EQUALS, Character.valueOf('=')),
				new Token2(TokenType2.VAR, "i"),
				new Token2(TokenType2.TEXT, "Joe \\\"Long\\\" Smith"),
				new Token2(TokenType2.OPERATOR, Character.valueOf('*')),
				new Token2(TokenType2.AT, Character.valueOf('@')),
				new Token2(TokenType2.VAR, "sin"),
				new Token2(TokenType2.DOLLAR, Character.valueOf('$')),
				new Token2(TokenType2.CLOSE_BRACKETS, Character.valueOf('}')),
				new Token2(TokenType2.EOF, null)
			};
		checkTokenStream(lexer, correctData1);
		lexer.setState(Lexer2State.TAG);
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	public void testTextAndForTag() {
		String textInput = loader("document3.txt");
		Lexer2 lexer = new Lexer2(textInput);

		Token2 correctData1[] = {
			new Token2(TokenType2.TEXT, "This is sample text.\r\n"),
			new Token2(TokenType2.OPEN_BKRACKETS, Character.valueOf('{'))
		};
		Token2 correctData2[] = {
				new Token2(TokenType2.DOLLAR, Character.valueOf('$')),
				new Token2(TokenType2.KEYWORD, "FOR"),
				new Token2(TokenType2.VAR, "i_"),
				new Token2(TokenType2.INTEGER, Long.valueOf(-1)),
				new Token2(TokenType2.INTEGER, Long.valueOf(100)),
				new Token2(TokenType2.INTEGER, Long.valueOf(5)),
				new Token2(TokenType2.DOLLAR, Character.valueOf('$')),
				new Token2(TokenType2.CLOSE_BRACKETS, Character.valueOf('}')),
				new Token2(TokenType2.EOF, null)
			};
		checkTokenStream(lexer, correctData1);
		lexer.setState(Lexer2State.TAG);
		checkTokenStream(lexer, correctData2);
	}
	
	@Test (expected = LexerException.class)
	public void testInvalidSymboInTag() {
		// Document contains invalid symbol (not operator) in echo tag : '?'
		// Lexer must throw exception
		String textInput = loader("document4.txt");
		Lexer2 lexer = new Lexer2(textInput);
		int size = 9;
		for (int i = 0; i < size; i++) {
			lexer.nextToken();
		}
	}
	
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer2 lexer, Token2[] correctData) {
		int counter = 0;
		for (Token2 expected : correctData) {
			Token2 actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			Assert.assertEquals(msg, expected.getType(), actual.getType());
			Assert.assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}

	// Helper method for getting text input from text files in resources
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
	
	
	
	
}
