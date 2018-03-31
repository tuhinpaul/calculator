package calculator;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.logging.*;

/**
 * Logging layer
 * @author Tuhin Paul
 * */
public class AppLogger {

	/**
	 * logger used by the logging methods in this class
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
	 * whether to log to console instead of filesystem.
	 * */
	private static boolean shouldLogToConsoleOnly = false;


	/**
	 * whether to log to console instead of filesystem.
	 * */
	public static void LogToConsoleOnly(boolean consoleOnly) {
		shouldLogToConsoleOnly = consoleOnly;
	}



	/**
	 * Custom simple formatter
	 * @author Tuhin Paul
	 * Assumption: log record message will contain fullClassname.methodName():lineNum<b>newline</b>message
	 * */
	public static class MyFormatter extends Formatter {

		/**
		 * format log record
		 * */
		@Override
		public String format(final LogRecord record) {
			// return YYYY-MM-dd hh::mm::ss.sss LogLevel message newline newline
			// note assumption at the description of this class.
			return String.format("%1$tF %1$tT.%1$tL %2$s %3$s%0$n%0$n",
					new GregorianCalendar(),
					record.getLevel().getName(),
					formatMessage(record));
		}
	}

	/**
	 * Get a logger for the name provided. If the name is the same as this class name, AppLogger.logger is returned.
	 * @param name logger name. Should not be null but don't raise error- use this class name as default.
	 * @return the logger with the given name
	 * */
	public static Logger getAppLogger(String name) {

		// name can't be null
		if(name == null)
			return AppLogger.getAppLogger(AppLogger.class.getName());

//		LogManager.getLogManager().getLogger(name).setLevel(Level.ALL);

		Logger logger;

		// If the name is the same as this class name, return AppLogger.logger
		if(name.equals(AppLogger.class.getName())) {
			// instantiate AppLogger.logger if necessary
			if (AppLogger.logger == null) {
				AppLogger.logger = Logger.getLogger(name);
			}

			logger = AppLogger.logger;
		}
		else // create/get logger for the other name provided
			logger = Logger.getLogger(name);

		// set file handler for logger
		Handler logDestHandler = null;

		try {
			// get rid of previous handlers
			Handler[] prevHandlers = logger.getHandlers();
			for(Handler h: prevHandlers) {
				logger.removeHandler(h);
				h.close();
			}

			// if logs should be sent to console OR file
			if(shouldLogToConsoleOnly) {
				// send logs to console only
				logDestHandler = new ConsoleHandler();
			}
			else {
				// send logs to filesystem only

				// mkdir logs directory if not existent
				File dir = new File(logFileDir);
				dir.mkdirs();

				// relative log file path
				String logFileRelativePath = logFileDir + "/" + logFileName;
				// log to file
				logDestHandler = new FileHandler(logFileRelativePath, true);
			}


			//custom log text formatter
			Formatter formatter = new MyFormatter();
			logDestHandler.setFormatter(formatter);

			// add handler to logger
			logger.addHandler(logDestHandler);

		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

		// don't log to console
		logger.setUseParentHandlers(false);

		return logger;
	}

	/**
	 * Logger with the name of this class's name - shared by logging methods (error(), info(), debug(), closeHandlers()) in this class.
	 * */
	public static Logger getAppLogger() {
		return AppLogger.getAppLogger(AppLogger.class.getName());
	}



	/**
	 * close the handlers associated with AppLogger.logger
	 * */
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
	 * @return if log level was set properly
	 * */
	public static boolean setLevel(String level) {
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
		else if(lvl.equals("off")) {
			logger.setLevel(Level.OFF);
		}
		else {
			// verbose level could not be set. so log ERROR:
			info("Wrong verbose level: " + level);

			// log level was not set properly:
			return false;
		}

		// all ok
		return true;
	}

	/**
	 * Get the line number, classname, and method name of the method that issued the log request.
	 * @return the line number, classname, and method name of the method that issued the log request.
	 * */
	public static String getLogIssuerInfo() {

		int issuerIndex = 3;

		StackTraceElement[] fullTrace = Thread.currentThread().getStackTrace();
		StackTraceElement trace = fullTrace[issuerIndex];

		String classNameFull = trace.getClassName();
		String methodName    = trace.getMethodName();
		int    lineNumber    = trace.getLineNumber();

		return classNameFull + "." + methodName + "():" + lineNumber;
	}


	/**
	 * Log using Level.INFO and default logger name. You may instead call AppLogger.getAppLogger(String name) to get a java.util.Logger instance with the name of the class where you are logging. Then you may use any of the java.util.logging.Level levels.
	 * @param info message to log
	 * */
	public static void info(String info) {

		String msg = String.format( "%1$s%0$n%2$s", getLogIssuerInfo(), info);

		// use the default logger name
		Logger logger = getAppLogger();

		// level.INFO for information
		logger.log(Level.INFO, msg);
	}

	/**
	 * Log using Level.SEVERE and default logger name. You may instead call AppLogger.getAppLogger(String name) to get a java.util.Logger instance with the name of the class where you are logging. Then you may use any of the java.util.logging.Level levels.
	 * @param err message to log
	 * */
	public static void error(String err) {

		String msg = String.format( "%1$s%0$n%2$s", getLogIssuerInfo(), err);

		// use the default logger name
		Logger logger = getAppLogger();

		// use SEVERE level for errors
		logger.log(Level.SEVERE, msg);
	}

	/**
	 * Log using Level.FINEST and default logger name. You may instead call AppLogger.getAppLogger(String name) to get a java.util.Logger instance with the name of the class where you are logging. Then you may use any of the java.util.logging.Level levels.
	 * @param debugInfo message to log
	 * */
	public static void debug(String debugInfo) {

		String msg = String.format( "%1$s%0$n%2$s", getLogIssuerInfo(), debugInfo);

		// use the default logger name
		Logger logger = getAppLogger();

		// use Level.FINEST for debug information
		logger.log(Level.FINEST, msg);
	}
}
