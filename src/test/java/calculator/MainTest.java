package calculator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Hashtable;
import java.util.Set;


/**
 * Overall input/output test
 * @author Tuhin Paul
 * */
public class MainTest extends TestCase
{

	private Calculator calc;

	protected void setUp() {
		calc = new Calculator();
	}

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public MainTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( MainTest.class );
	}

	/**
	 * blackbox Test: overall input/output test.
	 */
	public void testMainWithGoodExpressions() {
		Hashtable<String, Integer> testExpressions = new Hashtable<>();

		testExpressions.put("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))", 40);
		testExpressions.put("let(a, 5, let(b, mult(a, 10),add(b, a)))", 55);
		testExpressions.put("let(a, 5, add(a, a))", 10);
		testExpressions.put("mult(add(2, 2), div(9, 3))", 12);
		testExpressions.put("add(1, mult(2, 3))", 7);
		testExpressions.put("add(1, 2)", 3);
		testExpressions.put("let(Let, 5, add(Let, Let))", 10);

		testExpressions.put("let(a,5,a)", 5);
		testExpressions.put("let(a, add(4,6), 5)", 5);

		String[] expressions = {
				"let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))",
				"let(a, 5, let(b, mult(a, 10),add(b, a)))",
				"let(a, 5, add(a, a))",
				"mult(add(2, 2), div(9, 3))",
				"add(1, mult(2, 3))",
				"add(1, 2)",
				"let(Let, 5, add(Let, Let))",
				"let(a, add(4,6), 5)",
				"let(a,5,a)"
		};


		for (String expr: expressions) {

			System.out.println("Testing " + expr);

			try {
				int foundVal = calc.evaluate(expr);
				int trueVal = testExpressions.get(expr);

				assertTrue( expr, foundVal == trueVal);

				if(foundVal == trueVal)
					System.out.println(": pass.");
				else
					System.out.println(": fail.");
			}
			catch (Calculator.BadInputException ex) {
				fail("Failed expression: " + expr);
			}
		}
	}

	/**
	 * overall input/output test for invalid inputs that raise no error and actually works if
	 * the delimiter problems are ignored.
	 */
	public void testMainWithBadExpressionsThatWork() {
		Hashtable<String, Integer> testExpressions = new Hashtable<>();

		testExpressions.put("add((1, 2)", 3);
		testExpressions.put("let(Let, 5, add(Let, Let))))))", 10);

		String[] expressions = {
				"add((1, 2)",
				"let(Let, 5, add(Let, Let))))))"
		};


		for (String expr: expressions) {

			System.out.println("Testing " + expr);

			try {
				int foundVal = calc.evaluate(expr);
				int trueVal = testExpressions.get(expr);

				assertTrue( expr, foundVal == trueVal);

				if(foundVal == trueVal)
					System.out.println(": pass.");
				else
					System.out.println(": fail.");
			}
			catch (Calculator.BadInputException ex) {
				fail("Failed expression: " + expr);
			}
		}
	}

}
