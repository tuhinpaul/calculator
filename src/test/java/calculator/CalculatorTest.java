package calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test Calculator class
 * @author Tuhin Paul
 * */
public class CalculatorTest {

	private static Calculator calc;

	@BeforeClass
	public static void setUp () {
		calc = new Calculator();
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();


	/**
	 * Test Calculator.tokenize() with null
	 */
	@Test
	public void testTokenizeWithNull() {
		String[] tokens = calc.tokenize(null);
		assertNotSame(null, tokens);
		assertEquals("Tokenize should return 0-length array on null", tokens.length, 0);
	}







	/**
	 * Test with first let operand not being a variable
	 * */
	@Test
	public void testWrongLet1() throws Calculator.BadInputException {
		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.FIRST_LET_OPERAND_SHOULD_BE_VARIABLE);
		int sum = calc.evaluate("let(add(2,3), 5, 0)");
	}


	/**
	 * Test with wrong number of operands
	 * */
	@Test
	public void testWrongNumOfOperands1() throws Calculator.BadInputException {
		thrown.expect(Calculator.BadInputException.class);
		/**
		 * NOTE: Change TokenTree.makeTree() implementation to report WRONG_NUM_OF_OPERANDS in this case:
		 * */
		thrown.expectMessage(Calculator.BAD_EXPRESSION);
		int sum = calc.evaluate("add(3,4,2)");
	}


	/**
	 * Test undefined variable
	 * */
	@Test
	public void testUndefinedVar1() throws Calculator.BadInputException {
		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.UNDEFINED_VARIABLE);
		int sum = calc.evaluate("let(a, add(a,6), 5)");
	}

	/**
	 * Test undefined variable
	 * */
	@Test
	public void testUndefinedVar2() throws Calculator.BadInputException {
		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.UNDEFINED_VARIABLE);
		int sum = calc.evaluate("let(a,a,5)");
	}

	/**
	 * Test wrong variable position
	 * */
	@Test
	public void testWrongVarPosition1() throws Calculator.BadInputException {
		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.WRONG_VARIABLE_POSITION);
		int sum = calc.evaluate("b let(a,a,5) c");
	}

	/**
	 * Test Malformed expression
	 * */
	@Test
	public void testBadExpr1() throws Calculator.BadInputException {
		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.BAD_EXPRESSION);
		int sum = calc.evaluate("let(a,a,5) b");
	}


	/**
	 *  Evaluating null should raise Calculator.BadInputException.
	 */
	@Test
	public void evaluateNull() throws Calculator.BadInputException {
		System.out.println("Evaluating null should raise Calculator.BadInputException.");

		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.BAD_EXPRESSION);

		int sum = calc.evaluate(null);
	}


	/**
	 *  Empty string should raise Calculator.BadInputException.
	 */
	@Test
	public void evaluateEmptyExpression() throws Calculator.BadInputException {
		System.out.println("Empty string should raise Calculator.BadInputException.");

		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.BAD_EXPRESSION);

		int sum = calc.evaluate("");
	}

	/**
	 *  string with only spaces should raise Calculator.BadInputException.
	 */
	@Test
	public void evaluateWhitespaceExpression() throws Calculator.BadInputException {
		System.out.println("String with only spaces should raise Calculator.BadInputException.");

		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.BAD_EXPRESSION);

		int sum = calc.evaluate("  	");
	}


	/**
	 *  string with only delimiters should raise Calculator.BadInputException.
	 */
	@Test
	public void evaluateOnlyDelimiters() throws Calculator.BadInputException {
		System.out.println("String with only delimiters should raise Calculator.BadInputException.");

		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.BAD_EXPRESSION);

		int sum = calc.evaluate(",() ");
	}





	/**
	 *  string with non-alphanumeric characters should raise Calculator.BadInputException.
	 */
	@Test
	public void evaluateNonAlphaNumeric() throws Calculator.BadInputException {
		System.out.println("String with non-alphanumeric characters should raise Calculator.BadInputException.");

		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.UNKNOWN_LITERAL_TYPE);

		int sum = calc.evaluate("[!");
	}










}
