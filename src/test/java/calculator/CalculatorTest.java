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
	 *  Empty string should raise Calculator.BadInputException.
	 */
	@Test(expected=Calculator.BadInputException.class)
	public void evaluateEmptyExpression() throws Calculator.BadInputException {
		System.out.println("Empty string should raise Calculator.BadInputException.");
		int sum = calc.evaluate("");
	}

	/**
	 *  string with only spaces should raise Calculator.BadInputException.
	 */
	@Test(expected=Calculator.BadInputException.class)
	public void evaluateWhitespaceExpression() throws Calculator.BadInputException {
		System.out.println("String with only spaces should raise Calculator.BadInputException.");
		int sum = calc.evaluate("  	");
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
	 *  string with only delimiters should raise Calculator.BadInputException.
	 */
	@Test(expected=Calculator.BadInputException.class)
	public void evaluateOnlyDelimiters() throws Calculator.BadInputException {
		System.out.println("String with only delimiters should raise Calculator.BadInputException.");
		int sum = calc.evaluate(",() ");
	}

	/**
	 *  string with non-alphanumeric characters should raise Calculator.BadInputException.
	 */
	@Test
	public void evaluateNonAlphaNumeric() throws Calculator.BadInputException {
		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.UNKNOWN_LITERAL_TYPE);
		System.out.println("String with non-alphanumeric characters should raise Calculator.BadInputException.");
		int sum = calc.evaluate("[!");
	}










}
