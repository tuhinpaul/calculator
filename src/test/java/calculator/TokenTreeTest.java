package calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import calculator.Calculator.TokenTree;

/**
 * Test Calculator.TokenTree class
 * @author Tuhin Paul
 * */
public class TokenTreeTest {


	private static Calculator calc;

	@BeforeClass
	public static void setUp () {
		calc = new Calculator();
	}


	@Rule
	public ExpectedException thrown = ExpectedException.none();


	/**
	 * Test constructor with null argument
	 */
	@Test
	public void testConstructorWithNull() throws Calculator.BadInputException {

		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.INVALID_INPUT);

		String[] arg = null;
		TokenTree tree = calc.new TokenTree(arg);
	}

	/**
	 * Test TokenTree with empty token array.
	 * This should not allow tree creation, which is a private method
	 * This can be tested by calling TokenTree.evaluate()
	 */
	@Test
	public void testConstructorWithEmptyArr() throws Calculator.BadInputException {

		//  Should not throw exception.
		TokenTree tree = calc.new TokenTree(new String[] {});

		// call evaluate() to check that tree creation fails
		thrown.expect(Calculator.BadInputException.class);
		thrown.expectMessage(Calculator.BAD_EXPRESSION);
		tree.evaluate();

	}




	/**
	 * Test Calculator.TokenTree.isLiteral()
	 * */
	@Test
	public void testLiteralCheck() throws Calculator.BadInputException {

		System.out.println("Testing Calculator.TokenTree.isLiteral():");

		String str;
		boolean isValid;

		//  Should not throw exception.
		TokenTree tree = calc.new TokenTree(new String[] {});

		assertEquals("null is not a literal", tree.isLiteral(null), false);
		System.out.println("Passed null test");

		assertEquals("whitespace string is not literal", tree.isLiteral("\t \n"), false);
		System.out.println("Passed whitespace string test");

		str = "_";
		isValid = false;
		assertEquals("According to specs, this is NOT a valid literal: " + str, tree.isLiteral(str), isValid);
		System.out.println("Passed isLiteral() check for " + str);

		str = "A_";
		isValid = false;
		assertEquals("According to specs, this is NOT a valid literal: " + str, tree.isLiteral(str), isValid);
		System.out.println("Passed isLiteral() check for " + str);
	}


	/**
	 * Test Calculator.TokenTree.isNumeric
	 * */
	@Test
	public void testNumericCheck() throws Calculator.BadInputException {

		System.out.println("Testing Calculator.TokenTree.isNumeric():");

		String str;
		boolean isValid;

		//  Should not throw exception.
		TokenTree tree = calc.new TokenTree(new String[] {});

		assertEquals("null is not a number", tree.isNumeric(null), false);
		System.out.println("Passed null test");

		assertEquals("whitespace string is not a number", tree.isNumeric("\t \n"), false);
		System.out.println("Passed whitespace string test");

		str = " 1 ";
		isValid = false;
		assertEquals("According to specs/implementation, this is NOT a valid number: " + str, tree.isNumeric(str), isValid);
		System.out.println("Passed isNumeric() check for " + str);

		str = "999999999999999999999999999999999999";
		isValid = false;
		assertEquals("According to specs/implementation, this is NOT a valid number: " + str, tree.isNumeric(str), isValid);
		System.out.println("Passed isNumeric() check for " + str);

		str = "2147483648";
		isValid = false;
		assertEquals("According to specs/implementation, this is NOT a valid number: " + str, tree.isNumeric(str), isValid);
		System.out.println("Passed isNumeric() check for " + str);

		str = "-2147483649";
		isValid = false;
		assertEquals("According to specs/implementation, this is NOT a valid number: " + str, tree.isNumeric(str), isValid);
		System.out.println("Passed isNumeric() check for " + str);

		str = "00";
		isValid = true;
		assertEquals("According to specs/implementation, this is a valid number: " + str, tree.isNumeric(str), isValid);
		System.out.println("Passed isNumeric() check for " + str);
	}

}
