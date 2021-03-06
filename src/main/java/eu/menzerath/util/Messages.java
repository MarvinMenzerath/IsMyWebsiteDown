package eu.menzerath.util;

import eu.menzerath.imwd.Main;

/**
 * Some often-used Strings can be found here.
 */
public class Messages {
	// START: Main
	public static final String COPYRIGHT = "© 2012-2015: Marvin Menzerath";
	public static final String CONSOLE_HELP = "You have to use the following syntax: java -jar IMWD.jar [URL] [INTERVAL] {ARG}";
	public static final String CONSOLE_HELP_EXAMPLES = "Examples:\n    java -jar IMWD.jar\n    java -jar IMWD.jar http://website.com 30\n    java -jar IMWD.jar http://website.com 30 --nolog\n    java -jar IMWD.jar http://website.com 0 --once";
	public static final String INVALID_PARAMETERS = "You have to enter a valid url (including the specific protocol) and interval (numbers only - between " + Main.MIN_INTERVAL + " and " + Main.MAX_INTERVAL + " seconds).";
	public static final String USING_HTTP = "Using HTTP as protocol. You should always specify the protocol when entering an url to prevent failures.";
	// END: Main

	// START: Message-Bubbles
	public static final String OK = "OK";
	public static final String ERROR_NOT_REACHABLE_TITLE = "Not Reachable";
	public static final String ERROR_NOT_REACHABLE_PING = "Sorry, but I was unable to receive Content from your Website while a Ping was successful.";
	public static final String ERROR_NOT_REACHABLE_NO_PING = "Uh... It seems like that your entire Server is gone!";
	public static final String ERROR_NO_CONNECTION_TITLE = "No Connection";
	public static final String ERROR_NO_CONNECTION = "Please check your Connection to the Internet.";
	// END: Message-Bubbles

	// START: Log
	public static final String LOG_START = "Checking %url every %interval seconds.";
	public static final String LOG_OK = "Everything OK.";
	public static final String LOG_PING_ONLY = "Only a Ping was successful.";
	public static final String LOG_ERROR = "Unable to reach the Website.";
	public static final String LOG_NO_CONNECTION = "No Connection to the Internet.";
	// END: Log

	// START: ConsoleApplication
	public static final String CONSOLE_START = "Starting with the following settings:";
	// END: ConsoleApplication

	// START: GuiApplication
	public static final String ABOUT_ICONS = "Icons: Ampeross - http://ampeross.deviantart.com";
	public static final String ABOUT_SOURCE = "Sourcecode: http://github.com/MarvinMenzerath/IsMyWebsiteDown - GPLv3";
	public static final String ABOUT_AUTHOR = COPYRIGHT + " - https://menzerath.eu";
	public static final String AUTORUN_ERROR = "Could not copy " + Main.APPLICATION_SHORT + " to your Autorun. Please check...\n\n  * You are allowed to copy files to the Autorun-Folder.\n  * You are running Windows Vista or higher.";
	// END: GuiApplication

	// START: Updates
	public static final String UPDATE_NO_UPDATE = "No Update found!";
	public static final String UPDATE_NO_UPDATE_LONG = "You are running the latest version of \"" + Main.APPLICATION + "\" (v" + Main.VERSION + ").";
	public static final String UPDATE_ERROR = "Unable to search for Updates. Please visit \"" + Main.URL_RELEASE + "\".";
	public static final String UPDATE_SNAPSHOT = "This is a SNAPSHOT-Release. The Updater is therefore deactivated!";
	public static final String UPDATE_AVAILABLE_TITLE = "Update available!";
	public static final String UPDATE_AVAILABLE = "There is an Update to version %version available.";
	public static final String UPDATE_AVAILABLE_CHANGES = "Changes: %changes";
	public static final String UPDATE_NOW = "Do you want to Download it now?";
	// END: Updates
}