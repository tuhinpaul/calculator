package calculator;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Logging layer
 * @author Tuhin Paul
 * */
public class AppLogger {

	/**
	 * The log file directory
	 */
	private static String logFileDir = "logs";

	/**
	 * The log file name
	 */
	private static String logFileName = "calculator.log";


	/**
	 * get a logger for the name provided
	 * @param name logger name. Should not be null but don't raise error- use this class name as default.
	 * @return the logger with the given name
	 * */
	public static Logger getAppLogger(String name) {

		// name can't be null
		if(name == null)
			return AppLogger.getAppLogger(AppLogger.class.getName());

		Logger logger = Logger.getLogger(name);

		// set file handler for logger
		Handler fileHandler = null;
		try {
			// get rid of previous handlers
			Handler[] prevHandlers = logger.getHandlers();
			for(Handler h: prevHandlers) {
				logger.removeHandler(h);
			}

			new File(logFileDir).mkdirs();
			String logFileRelativePath = logFileDir + "/" + logFileName;
			fileHandler = new FileHandler(logFileRelativePath, true);

			//plain text formatter
			Formatter plainTextFormatter = new SimpleFormatter();
			fileHandler.setFormatter(plainTextFormatter);

			// add handler to logger
			logger.addHandler(fileHandler);

		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

		// don't log to console
		logger.setUseParentHandlers(false);

		return logger;
	}

	public static Logger getAppLogger() {
		return AppLogger.getAppLogger(AppLogger.class.getName());
	}

	/**
	 * Don't allow instantiation
	 */
	private AppLogger() {
	}


	/**
	 * Log using Level.INFO and default logger name
	 * @param info message to log
	 * @deprecated Use AppLogger.getAppLogger(...) to get a java.util.Logger instance with the name of the class where you are logging. Then use any of the java.util.logging.Level levels.
	 * */
	public static void info(String info) {
		// use the default logger name
		Logger logger = getAppLogger();

		// level.INFO for information
		logger.log(Level.INFO, info);
	}

	/**
	 * Log using Level.SEVERE and default logger name
	 * @param err message to log
	 * @deprecated Use AppLogger.getAppLogger(...) to get a java.util.Logger instance with the name of the class where you are logging. Then use any of the java.util.logging.Level levels.
	 * */
	public static void error(String err) {
		// use the default logger name
		Logger logger = getAppLogger();

		// use SEVERE level for errors
		logger.log(Level.SEVERE, err);
	}

	/**
	 * Log using Level.FINE and default logger name
	 * @param debugInfo message to log
	 * @deprecated Use AppLogger.getAppLogger(...) to get a java.util.Logger instance with the name of the class where you are logging. Then use any of the java.util.logging.Level levels.
	 * */
	public static void debug(String debugInfo) {
		// use the default logger name
		Logger logger = getAppLogger();

		// use Level.FINE for debug information
		logger.log(Level.FINE, debugInfo);
	}
}
