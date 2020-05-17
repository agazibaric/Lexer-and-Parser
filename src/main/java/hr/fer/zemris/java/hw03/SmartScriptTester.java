package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Class represents program that is used for testing <code>SmartScriptParser</code>.
 * It processes given text and tests correctness of <code>SmartScriptParser</code>
 * by analyzing given text twice. After every analysis it prints text to the screen.
 * If parsing process is correct there should be no caught exceptions and 
 * outputed text should be the same after every analysis.
 * Text is given through one command line argument that represents path to the text file
 * that contains the text that will be analyzed. 
 * If path is not given <code>SmartScriptTester<code> uses default text.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class SmartScriptTester {

	/**
	 * Accepts one command line argument that represents path to the text file
	 * that contains text that will be analyzed.
	 * If arguments are not given, default text will be analyzed.
	 * 
	 * @param args <code>String</code> that represents path to the text file
	 */
	public static void main(String[] args) {
		
		String text = null;
		int numOfArgs = args.length;
		
		// Check if args contains file path, if not use default text
		if (numOfArgs == 1) {
			text = getTextFromFilePath(args[0]);
		} else if (numOfArgs == 0) {
			text = getDefaultText();
		} else {
			System.out.println("Invalid number of arguments.\n"
							 + "Number of arguments must be eather zero or one.\n"
							 + "You entered: " + numOfArgs + ".\n");
			return;
		}

		String docBody = text;
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		}catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		System.out.println("----------------------------------------------------------------------");
		
		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody2);
		
	}
	
	/**
	 * Method reconstructs original text document that was analyzed.
	 * 
	 * @param node <code>Node</code> that represents syntax tree of original text
	 * @return     <code>String</code> that represents original text.
	 */
	public static String createOriginalDocumentBody(Node node) {
		if(node == null)
			return "";
		
		String documentText = "";
		int numOfNodes = 0;
		try {
			numOfNodes = node.numberOfChildren();
		} catch(NullPointerException ex) {
			// it doesn't contain any child nodes
			return "";
		}
		
		for (int i = 0; i < numOfNodes; i++) {
			Node child = node.getChild(i);
			if(child instanceof ForLoopNode) {
				documentText = documentText.concat(getForLoopText((ForLoopNode)child));
				documentText = documentText.concat(createOriginalDocumentBody(child));
				documentText = documentText.concat("{$END$}");
			} else if(child instanceof EchoNode) {
				documentText = documentText.concat(getEchoText((EchoNode)child));
			}else if(child instanceof TextNode) {
				documentText = documentText.concat(getTextNodeString((TextNode)child));
			}
		}
		return documentText;
	}
	
	/**
	 * Method used for constructing original text of a for loop.
	 * 
	 * @param node <code>ForLoopNode</code> that represents for loop tag
	 * @return     <code>String</code> that represents original text of for loop.
	 */
	private static String getForLoopText(ForLoopNode node) {
		String variable = node.getVariable().asText();
		String start = node.getStartExpression().asText();
		String end = node.getEndExpression().asText();
		String step = node.getStepExpression().asText();
		
		return "{$FOR " + variable + " " + start + " " + end + " " + step + " $}";
	}
	
	/**
	 * Method used for constructing original text of a echo tag.
	 * 
	 * @param node <code>EchoNode</code> that represents echo tag
	 * @return     <code>String</code> that represents original text of echo tag.
	 */
	private static String getEchoText(EchoNode node) {
		
		String text = new String("{$= ");
		Element[] elements = node.getElements();
		int size = 0;
		try {
			size = elements.length;
		} catch(NullPointerException ex) {
			return "";
		}
		
		for(int i = 0; i < size; i++) {
			Element child = elements[i];
			if(child instanceof ElementVariable) {
				ElementVariable var = (ElementVariable) child;
				text = text.concat(var.asText() + " ");
			}else if(child instanceof ElementString) {
				ElementString string = (ElementString) child;
				text = text.concat("\"" + string.asText() + "\" ");
			}else if(child instanceof ElementFunction) {
				ElementFunction function = (ElementFunction) child;
				text = text.concat("@" + function.asText() + " ");
			}else if(child instanceof ElementConstantInteger) {
				ElementConstantInteger number = (ElementConstantInteger) child;
				text = text.concat(number.asText() + " ");
			}else if(child instanceof ElementOperator) {
				ElementOperator operator = (ElementOperator) child;
				text = text.concat(operator.asText() + " ");
			}else if(child instanceof ElementConstantDouble) {
				ElementConstantDouble number = (ElementConstantDouble) child;
				text = text.concat(number.asText() + " ");
			}
		}
		return text.concat("$}");
	}
	
	/**
	 * Method used for constructing original text from <code>TextNode</code>.
	 * 
	 * @param node <code>TextNode</code> that represents text element.
	 * @return     <code>String</code> representation of given <code>node</code>
	 */
	private static String getTextNodeString(TextNode node) {
		return node.getText();
	}
	
	/**
	 * Method used for constructing <code>String</code>
	 * from given text file whose path is given.
	 * 
	 * @param filepath path that points to the text file.
	 * @return		   <code>String</code> representation of text file
	 */
	private static String getTextFromFilePath(String filepath) {
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return docBody;
	}
	
	/**
	 * Private method for getting default text for syntax analysis.
	 * 
	 * @return <code>String</code> that is default text.
	 */
	private static String getDefaultText() {
		return "This is . \"sample\" \\ text.\r\n" + 
				"{$ FOR i_0 -1 \"100\" 1 $}\r\n" + 
				" This is {$= i 22.03 \"Joe \\\"Long\\\" Smith\" $}"
				+ "-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i 2.301AG+3 i * @sin \"0.000\" @decfmt $}\r\n" +
				"{$END$}"; 		
	}
	
}
