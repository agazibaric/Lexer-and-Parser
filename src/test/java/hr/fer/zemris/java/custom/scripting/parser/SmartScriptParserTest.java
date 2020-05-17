package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;

import static hr.fer.zemris.java.hw03.SmartScriptTester.createOriginalDocumentBody;

public class SmartScriptParserTest {
	
	@Test
	public void testParseTwiceAndCompareOutput() {
		String document1 = loader("document7.txt");
		SmartScriptParser parser1 = new SmartScriptParser(document1);
		DocumentNode node1 = parser1.getDocumentNode();
		
		String document2 = createOriginalDocumentBody(node1);
		SmartScriptParser parser2 = new SmartScriptParser(document2);
		DocumentNode node2 = parser2.getDocumentNode();
		String document3 = createOriginalDocumentBody(node2);
		
		Assert.assertEquals(document2, document3);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testNoEndTag() {
		String document = loader("document5.txt");
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testTooManyEndTags() {
		String document = loader("document6.txt");
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testInvalidForLoop() {
		String document = loader("document8.txt");
		new SmartScriptParser(document);
	}
	
	@Test (expected = SmartScriptParserException.class)
	public void testInvalidSyntax() {
		String document = loader("document9.txt");
		new SmartScriptParser(document);
	}
	
	@Test
	public void testSizeOfDocument() {
		String document = loader("document7.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		
		int expectedSize = 5;
		int actualSize = node.numberOfChildren();
		Assert.assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void testStructureOfDocumentNode() {
		String document = loader("document10.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		
		// At index 1 has to be ForLoopNode
		ForLoopNode forNode = (ForLoopNode) node.getChild(1);
		ElementVariable var = forNode.getVariable();
		ElementConstantInteger start = (ElementConstantInteger) forNode.getStartExpression();
		ElementConstantInteger end = (ElementConstantInteger) forNode.getEndExpression();
		ElementConstantInteger step = (ElementConstantInteger) forNode.getStepExpression();
		
		String varText = var.asText();
		String startText = start.asText();
		String endText = end.asText();
		String stepText = step.asText();
		
		String varExpected = "i";
		String startExpected = "-1";
		String endExpected = "10";
		String stepExpected = "1";
		
		Assert.assertEquals(varExpected, varText);
		Assert.assertEquals(startExpected, startText);
		Assert.assertEquals(endExpected, endText);
		Assert.assertEquals(stepExpected, stepText);
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
