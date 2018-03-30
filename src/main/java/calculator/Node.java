package calculator;

import java.util.ArrayList;
import java.util.List;

/**
*
* @author Tuhin Paul
*/
class Node {
	private String opName;
	private String varName;
	private Double value;
	private List<Node> children;
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

	
	Node() {
		opName = null;
		varName = null;
		value = null;
		children = null;
		parent = null;
	}

	public int numChildren() {
		if(children == null)
			return 0;

		return children.size();
	}
	
	public void appendChild(Node child) {
		// null check:
		if(this.children == null) {
			this.children = new ArrayList<>();
		}
		
		this.children.add(child);
	}
}

