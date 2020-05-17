package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class represents for loop node and its properties
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public class ForLoopNode extends Node {
	
	/**
	 * variable in for loop
	 */
	private ElementVariable variable;
	/**
	 * start value
	 */
	private Element startExpression;
	/**
	 * end value
	 */
	private Element endExpression;
	/**
	 * step value that is variable increasing for
	 */
	private Element stepExpression;
	
	/**
	 * Constructor for creating new <code>ForLoopNode</code>.
	 * 
	 * @param variable 	       <code>ElementVariable</code> variable in for loop
	 * @param startExpression  <code>Element</code> start value
	 * @param endExpression	   <code>Element</code> end value
	 * @param stepExpression   <code>Element</code> step value
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Returns forloop's variable
	 * @return <code>ElementVariable</code> that represents forloop's variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns forloop's start value.
	 * @return <code>Element</code> that represents forloop's start value
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns forloop's end value.
	 * @return <code>Element</code> that represents forloop's end value
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns forloop's step value.
	 * @return <code>Element</code> that represents forloop's step value
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
