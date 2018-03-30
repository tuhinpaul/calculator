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

		if(args.length == 1) {
			Calculator calc = new Calculator();

			try {
				String expr = args[0];
				System.out.println(calc.evaluate(expr));
			}
			catch(BadInputException ex) {
				System.err.println("Error occurred. Please check stacktrace below:");
				ex.printStackTrace();
			}
		}
		else {
			System.err.println("Only single argument must be provided. Usage example:");
			System.err.println("calculator.Main \"let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))\"");
		}
	}
	
}
