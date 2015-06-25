package eu.menzerath.imwd.checker;

import eu.menzerath.imwd.GuiApplication;
import eu.menzerath.util.Helper;
import eu.menzerath.util.Messages;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the Logger-class to be used by a Checker-object. It manages access to log-files and the general console-output for a single Checker.
 */
public class Logger {
	private final Checker CHECKER;
	private final boolean CREATE_FILE;
	private final boolean LOG_VALID_CHECKS;
	private final GuiApplication GUI;

	/**
	 * Constructor: Put the specified values in our own parameters
	 *
	 * @param checker        the Logger's "parent"
	 * @param createFile     Create a log-file?
	 * @param logValidChecks Log even successful checks?
	 */
	public Logger(Checker checker, boolean createFile, boolean logValidChecks) {
		this.CHECKER = checker;
		this.CREATE_FILE = createFile;
		this.LOG_VALID_CHECKS = logValidChecks;
		this.GUI = null;
	}

	/**
	 * Constructor: Put the specified values in our own parameters
	 *
	 * @param checker        the Logger's "parent"
	 * @param createFile     Create a log-file?
	 * @param logValidChecks Log even successful checks?
	 * @param gui            the GUI that needs to be updated
	 */
	public Logger(Checker checker, boolean createFile, boolean logValidChecks, GuiApplication gui) {
		this.CHECKER = checker;
		this.CREATE_FILE = createFile;
		this.LOG_VALID_CHECKS = logValidChecks;
		this.GUI = gui;
	}

	/**
	 * New log-entry: the Checker started it's work
	 */
	public void start() {
		String message = Messages.LOG_START.replace("%url", CHECKER.URL).replace("%interval", "" + CHECKER.INTERVAL);
		System.out.println(getLogHead() + new Ansi().fg(Ansi.Color.CYAN).a("[INFO]").reset() + " " + message);
		write(getLogHead() + "[INFO]" + " " + message);
	}

	/**
	 * New log-entry: the Checker made a successful check
	 */
	public void ok() {
		if (LOG_VALID_CHECKS) {
			System.out.println(getLogHead() + new Ansi().fg(Ansi.Color.GREEN).a("[OK]").reset() + " " + Messages.LOG_OK);
			write(getLogHead() + "[OK]" + " " + Messages.LOG_OK);
		}
		updateGui(1);
	}

	/**
	 * New log-entry: the Checker issued a warning (e.g. no response from the webserver)
	 */
	public void warning() {
		System.out.println(getLogHead() + new Ansi().fg(Ansi.Color.YELLOW).a("[WARNING]").reset() + " " + Messages.LOG_PING_ONLY);
		write(getLogHead() + "[WARNING]" + " " + Messages.LOG_PING_ONLY);
		updateGui(2);
	}

	/**
	 * New log-entry: the Checker issued an error (no response from the webserver and no successful ping)
	 */
	public void error() {
		System.out.println(getLogHead() + new Ansi().fg(Ansi.Color.RED).a("[ERROR]").reset() + " " + Messages.LOG_ERROR);
		write(getLogHead() + "[ERROR]" + " " + Messages.LOG_ERROR);
		updateGui(3);
	}

	/**
	 * New log-entry: there is no connection to the internet
	 */
	public void noConnection() {
		// Print this only once, but update the GUI!
		if (CHECKER.ID != 0) {
			updateGui(4);
			return;
		}
		System.out.println(getLogHead().replace("[1]", "[A]") + new Ansi().fg(Ansi.Color.RED).a("[ERROR]").reset() + " " + Messages.LOG_NO_CONNECTION);
		write(getLogHead().replace("[1]", "[A]") + "[ERROR]" + " " + Messages.LOG_NO_CONNECTION);
		updateGui(4);
	}

	/**
	 * Adds a message to the log-file (if enabled)
	 *
	 * @param message Message to print/output
	 */
	private void write(String message) {
		if (CREATE_FILE) {
			File file = new File("imwd_" + Helper.getUrlWithoutProtocol(CHECKER.URL) + ".txt");
			try {
				PrintWriter out = new PrintWriter(new FileOutputStream(file, true));
				out.append(message.replace("[" + CHECKER.ID + "] ", "")).append("\r\n");
				out.close();
			} catch (IOException ignored) {
			}
		}
	}

	/**
	 * Updates the GUI (if enabled): changes the TrayIcon's icon and displays a message-bubble (if enabled and there was a status-change)
	 *
	 * @param status New status the GUI has to display
	 */
	private void updateGui(int status) {
		if (GUI != null) {
			if (status == 4 && CHECKER.ID != 1) {
				GUI.updateTrayIcon(CHECKER, status, false);
			} else {
				GUI.updateTrayIcon(CHECKER, status, true);
			}
		}
	}

	/**
	 * Builds the default beginning of a log-entry
	 *
	 * @return Beginning of a log-entry
	 */
	private String getLogHead() {
		// +1 to be more user-friendly
		return "[" + (CHECKER.ID + 1) + "] [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "] ";
	}
}