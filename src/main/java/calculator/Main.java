package calculator;

import calculator.Calculator.BadInputException;

/**
 *
 * @author Tuhin Paul
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Calculator calc = new Calculator();

		String[] inputs = {
			"let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))",
			"let(a, 5, let(b, mult(a, 10),add(b, a)))",
			"let(a, 5, add(a, a))",
			"mult(add(2, 2), div(9, 3))",
			"add(1, mult(2, 3))",
			"add(1, 2)"
		};

		try {
			for(String expr: inputs) {
				System.out.println(calc.evaluate(expr));
			}
		}
		catch(BadInputException ex) {
			ex.printStackTrace();
		}
	}
	
}
