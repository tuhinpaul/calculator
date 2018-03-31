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
	 * logger
	 */
	private static Logger logger;

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


		Logger logger;

		if(name.equals(AppLogger.class.getName())) {
			if (AppLogger.logger == null) {
				AppLogger.logger = Logger.getLogger(name);
			}
			logger = AppLogger.logger;
		}
		else
			logger = Logger.getLogger(name);

		// set file handler for logger
		Handler fileHandler = null;
		try {
			// get rid of previous handlers
			Handler[] prevHandlers = logger.getHandlers();
			for(Handler h: prevHandlers) {
				logger.removeHandler(h);
				h.close();
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


	public static void closeHandlers() {
		if (logger != null) {
			// close handlers
			Handler[] prevHandlers = logger.getHandlers();
			for(Handler h: prevHandlers) {
				logger.removeHandler(h);
				h.close();
			}
		}
	}

	/**
	 * Don't allow instantiation
	 */
	private AppLogger() {
	}


	/**
	 * Sets verbose layer of the logger shared by info(), errror() and debug() methods of this class.
	 * @param level should be the command line verbose level provided to the main application.
	 * */
	public static void setLevel(String level) {
		String lvl = level.toLowerCase();

		Logger logger = getAppLogger();

		if(lvl.equals("debug")) {
			logger.setLevel(Level.FINEST);
		}
		else if(lvl.equals("info")) {
			logger.setLevel(Level.INFO);
		}
		else if(lvl.equals("error")) {
			logger.setLevel(Level.SEVERE);
		}
		else {
			// verbose level could not be set. so log ERROR:
			info("Wrong verbose level: " + level);
		}
	}

	/**
	 * Log using Level.INFO and default logger name. You may instead call AppLogger.getAppLogger(String name) to get a java.util.Logger instance with the name of the class where you are logging. Then you may use any of the java.util.logging.Level levels.
	 * @param info message to log
	 * */
	public static void info(String info) {
		// use the default logger name
		Logger logger = getAppLogger();

		// level.INFO for information
		logger.log(Level.INFO, info);
	}

	/**
	 * Log using Level.SEVERE and default logger name. You may instead call AppLogger.getAppLogger(String name) to get a java.util.Logger instance with the name of the class where you are logging. Then you may use any of the java.util.logging.Level levels.
	 * @param err message to log
	 * */
	public static void error(String err) {
		// use the default logger name
		Logger logger = getAppLogger();

		// use SEVERE level for errors
		logger.log(Level.SEVERE, err);
	}

	/**
	 * Log using Level.FINEST and default logger name. You may instead call AppLogger.getAppLogger(String name) to get a java.util.Logger instance with the name of the class where you are logging. Then you may use any of the java.util.logging.Level levels.
	 * @param debugInfo message to log
	 * */
	public static void debug(String debugInfo) {
		// use the default logger name
		Logger logger = getAppLogger();

		// use Level.FINEST for debug information
		logger.log(Level.FINEST, debugInfo);
	}
}
