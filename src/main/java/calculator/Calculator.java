package calculator;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tuhin Paul
 */
public class Calculator {

	/* Case-sensitive operation names in expression: change here if you want to change them in the expression */
	public static final String OP_LET  = "let";
	public static final String OP_ADD  = "add";
	public static final String OP_SUB  = "sub";
	public static final String OP_MUL  = "mult";
	public static final String OP_DIV  = "div";
	public static final String OP_VAR  = "var";
	public static final String OP_INT  = "int";
	
	/* Exception error messages */
	public static final String WRONG_VARIABLE_POSITION = "Wrong variable position";
	public static final String UNKNOWN_LITERAL_TYPE = "Unkown literal type";
	public static final String UNDEFINED_VARIABLE = "Undefined variable";
	public static final String WRONG_NUM_OF_OPERANDS = "Wrong number of operands";
	public static final String FIRST_LET_OPERAND_SHOULD_BE_VARIABLE = "Leftmost operand of the let construct should be a variable";
	public static final String UNREACHABLE_CODE_REACHED = "This code should not have been reached.";
	public static final String BAD_EXPRESSION = "Bad expression (empty/malformed).";


	/**
	 * Exception showing bad input in expression provided to Calculator.
	 * @author Tuhin Paul
	 */
	@SuppressWarnings("serial")
	public class BadInputException extends Exception {

		public BadInputException(String message) {
			super(message);
		}
	}

	/**
	 * Tokenize a string. Empty strings are not tokens.
	 * @param inStr the input string to tokenize.
	 * @return the tokens using delimiters: whitespace, comma, and parentheses.
	 */
	public String[] tokenize(String inStr) {
		String[] tokens = new String[0];
		
		if (inStr == null) {
			// TODO: consider invalid input?
			return tokens;
		}
		
		if(inStr.trim().length() == 0) {
			// TODO: consider invalid input?
			return tokens;
		}
		
		tokens = inStr.split("[\\s,\\(\\)]+");

		return tokens;
	}

	/**
	 * Evaluate the expression. The problem specification shows integer output. Therefore, converting the evaluated result to integer.
	 *
	 * @param expr the expression to be evaluated.
	 * @return the evaluated value of the argument.
	 */
	public int evaluate(String expr) throws BadInputException {
		// tokenize input expression:
		String[] tokens = this.tokenize(expr);

		// token tree object:
		TokenTree tree = new TokenTree(tokens);

		// evaluate the expression by traversing the token tree:
		double result = tree.evaluate();
		
		// TODO: cleanup tree?
		
		return (int)result;
	}

	
	
	class TokenTree {
		private Queue<String> tokens;
		private Node root;
		// variable values;
		private Hashtable<String, Double> varMap;
		
		TokenTree(String[] tokens) {
			// TODO: check validity of tokens
			
			this.tokens = new LinkedList<String>();
			// assign tokens:
			this.tokens.addAll(Arrays.asList(tokens));
			
			// initialize root:
			root = new Node();
		}

		private boolean doesMatch(String regex, String literal) {
			// compile the regex
			Pattern pattern = Pattern.compile(regex);

			// applying the regex on the input string:
			Matcher matcher = pattern.matcher(literal);

			return matcher.find();
		}
		
		private boolean isLiteral(String literal) {
			String regex = "^[a-zA-Z]+$";
			return doesMatch(regex, literal);
		}
		
		private boolean isNumeric(String literal) {

			/* avoiding Regex check because the specification says that the value should be an int in [Integer.MIN_VALUE, Integer.MAX_VALUE] */
			try {
				int intVal = Integer.parseInt(literal);
				return true;
			}
			catch (NumberFormatException ex) {
				return false;
			}

			/*
			// TODO: handle octal/hex numbers
			String regex = "^[-+]?\\d+(\\.\\d*)?$"; // TODO: does not match .1
			return doesMatch(regex, literal);
			*/
		}

		private void makeTree(Node n) throws BadInputException {
			String topToken = this.tokens.peek();

			// next token:
			if (topToken == null) {
				// TODO: what if no more tokens?
				// It can happen if: 1) empty expression is provided or 2) malformed expression is provided:
				throw new BadInputException(Calculator.BAD_EXPRESSION); // TODO: add other info to the exception
			}

			switch(topToken) {
				// let operation starts:
				case OP_LET:
				// add operation starts:
				case OP_ADD:
				// sub operation starts:
				case OP_SUB:
				// mult operation starts:
				case OP_MUL:
				// div operation starts:
				case OP_DIV:
					// Number of children for all operations except "let":
					int numArms = 2;
					if(topToken.equals(OP_LET))
						numArms = 3;

					// if this is first time this node is accessed [as arg of this recursive fn]:
					if(n.getOpName() == null) {
						// assign operation name
						n.setOpName(topToken);
						
						// consume top token
						this.tokens.poll();
						
						// append a child
						Node child = new Node();
						n.appendChild(child);
						child.setParent(n);
						
						// process child:
						this.makeTree(child);
					}
					
					while(n.getChildren().size() < numArms) {						
						// append next child
						Node child = new Node();
						n.appendChild(child);
						child.setParent(n);

						this.makeTree(child);
					}
					
					break;
				
				// others (including numbers):
				default:

					boolean isVariable = this.isLiteral(topToken);
					boolean isNumber   = this.isNumeric(topToken);
					
					if (isVariable) {
						// a variable needs a parent:
						if(n.getParent() == null) {
							throw new BadInputException(Calculator.WRONG_VARIABLE_POSITION); // TODO: add other info to the exception
						}
						
						// add a variable child:						
						n.setOpName(OP_VAR);
						n.setVarName(topToken);
						
						// consume top token
						this.tokens.poll();
					}
					else if (isNumber) {
						// add a numeric child:
						n.setOpName(OP_INT);
						n.setValue(Double.parseDouble(topToken));
						
						// consume top token
						this.tokens.poll();
					}
					else {
						// TODO: add other info to the exception:
						throw new BadInputException(Calculator.UNKNOWN_LITERAL_TYPE);
					}

					break;
			}
		}
	
		private void cleanupTree() {
			// TODO
		}

		/**
		 * Evaluate the tree to compute the result.
		 * @return result of the expression presented as token tree
		 * @throws BadInputException
		 */
		public double evaluate() throws BadInputException {
			if(this.root != null) {
				// cleanup required
				cleanupTree();
			}

			// construct the tree from the queue of tokens:
			this.makeTree(this.root);
			
			if(varMap == null)
				varMap = new Hashtable<>();
			else {
				// cleanup required:
				varMap.clear();
			}
			// evaluate the tree
			return this.evaluate(this.root);
		}
		
		private double evaluate(Node n) throws BadInputException {
			
			String opName = n.getOpName();
			
			List<Node> children;
			Node child1;
			Node child2;
			Node child3;
			int numDesiredChildren;
			
			switch(opName) {
			case OP_LET:
			case OP_ADD:
			case OP_SUB:
			case OP_MUL:
			case OP_DIV:
				if (opName.equals(OP_LET))
					numDesiredChildren = 3;
				else
					numDesiredChildren = 2;
				
				children = n.getChildren();
				if(children.size() != numDesiredChildren)
					throw new BadInputException(Calculator.WRONG_NUM_OF_OPERANDS + " for " + opName);
				
				child1 = children.get(0);
				child2 = children.get(1);
				
				if(opName.equals(OP_ADD))
					return this.evaluate(child1) + this.evaluate(child2);
				else if(opName.equals(OP_SUB))
					return this.evaluate(child1) - this.evaluate(child2);
				else if(opName.equals(OP_MUL))
					return this.evaluate(child1) * this.evaluate(child2);
				else if(opName.equals(OP_DIV))
					return 1.0 * this.evaluate(child1) / this.evaluate(child2);
				else if(opName.equals(OP_LET)) {
					// child1 should be a variable
					if(! child1.getOpName().equals(OP_VAR))
						throw new BadInputException(Calculator.FIRST_LET_OPERAND_SHOULD_BE_VARIABLE);
					
					// assign variable value to hash:
					this.varMap.put(child1.getVarName(), this.evaluate(child2));

					// return the value of child 3:
					child3 = children.get(2);
					return this.evaluate(child3);
				}
				
				break;

			case OP_VAR:
				// if this variable is not found in the variable hashtable:
				if(! this.varMap.containsKey(n.getVarName()) ) {
					throw new BadInputException(Calculator.UNDEFINED_VARIABLE + ": " + n.getVarName()); // TODO: Add more info
				}
				return this.varMap.get(n.getVarName());
				
			case OP_INT:
				return n.getValue();
				
			default:
				throw new BadInputException(Calculator.UNKNOWN_LITERAL_TYPE + ": " + opName);
			}
			
			// control of execution should not reach following line:
			throw new BadInputException(UNREACHABLE_CODE_REACHED);
		}

	}
	
}
