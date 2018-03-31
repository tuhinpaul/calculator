package calculator;

import java.util.ArrayList;
import java.util.List;

/**
*
* @author Tuhin Paul
*/
class Node {
	/**
	 * The name of operation for this node. Operations are:
	 * {@value calculator.Calculator#OP_LET},
	 * {@value calculator.Calculator#OP_ADD},
	 * {@value calculator.Calculator#OP_SUB},
	 * {@value calculator.Calculator#OP_MUL},
	 * {@value calculator.Calculator#OP_DIV},
	 * {@value calculator.Calculator#OP_VAR} to represent a variable,
	 * {@value calculator.Calculator#OP_INT} to represent an integer,
	 *  */
	private String opName;

	/**
	 * If this node is a variable, the corresponding name. Null otherwise.
	 * */
	private String varName;

	/**
	 * If this node is a numeric literal, the corresponding value. Null otherwise.
	 * */
	private Double value;

	/**
	 * List of children of the current node
	 * */
	private List<Node> children;

	/**
	 * parent of the current node. Null for the root.
	 * */
	private Node parent;

	/**
	 * @return the opName
	 */
	public String getOpName() {
		return opName;
	}

	/**
	 * @param opName the opName to set
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the varName
	 */
	public String getVarName() {
		return varName;
	}

	/**
	 * @param varName the varName to set
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

	/**
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	/**
	 * @return the parent
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}


	/**
	 * The default constructor sets all properties to null.
	 * */
	Node() {
		opName = null;
		varName = null;
		value = null;
		children = null;
		parent = null;
	}

	/**
	 * Number of children
	 * @return no. of children of this node or 0 if the children data structure is unassigned (i.e., null).
	 */
	public int numChildren() {
		if(children == null)
			return 0;

		return children.size();
	}

	/**
	 * Append a child to the list of children. If the children data structure is null (i.e., uninitialized),
	 * initialize the children property first.
	 * @param child the child to append to current children
	 * */
	public void appendChild(Node child) {
		// null check:
		if(this.children == null) {
			this.children = new ArrayList<>();
		}
		
		this.children.add(child);
	}
}

