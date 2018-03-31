package calculator;

import calculator.Calculator.BadInputException;

import java.util.logging.Logger;

/**
 *
 * @author Tuhin Paul
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		String expression    = null;
		String verboseSwitch = "-v";
		String verboseLevel  = null;

		boolean argInError = false;

		if(args.length == 1 && !args[0].equals(verboseSwitch)) {
			// one argument and that's not verbose switch
			// consider this argument as expression

			expression = args[0];
		}
		else if (args.length == 2 && args[1].equals(verboseSwitch)) {
			// two arguments and the second one is verbose switch
			// ignore verbose switch. allow all logs
			// consider the first as expression

			expression = args[0];
		}
		else if (args.length == 3) {
			// three arguments
			// the first or the second argument must be verbose switch. Verbose level will follow verbose switch.
			// the remaining one should be the expression

			if(args[0].equals(verboseSwitch)) {
				verboseLevel = args[1];
				expression = args[2];
			}
			else if(args[1].equals(verboseSwitch)) {
				verboseLevel = args[2];
				expression = args[0];
			}
			else {
				argInError = true;
			}
		}
		else {
			// if there is no argument, there is no expression
			// if there are more than 3 arguments, no way to know which one the expression is.
			// this else also catches other abnormal inputs: e.g., -v add(1,2)
			argInError = true;
		}


		if (argInError) {
			// verbose level could not be set. so log ERROR:
			AppLogger.error("Wrong command line arguments");

			System.err.println("Wrong command line arguments were provided. Usage:");
			System.err.println("calculator.Main \"expression\" [-v DEBUG|INFO|ERROR]");
			System.err.println("OR");
			System.err.println("calculator.Main -v DEBUG|INFO|ERROR \"expression\"");
		}
		else {

			if(verboseLevel != null) {
				AppLogger.setLevel(verboseLevel);
			}

			// there should be an expression here:
			Calculator calc = new Calculator();

			try {
				int result = calc.evaluate(expression);
				System.out.println(result);
				AppLogger.info("Expression " + expression + " evaluated to " + result);
			}
			catch(BadInputException ex) {
				AppLogger.error("Exception " + ex.getClass().getName() + " occurred: " + ex.getMessage());

				System.err.println("Error occurred. Please check stacktrace below:");
				ex.printStackTrace();
			}
		}

		// close logger handlers
		AppLogger.closeHandlers();

	}
}
