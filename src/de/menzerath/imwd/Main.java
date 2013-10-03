package de.menzerath.imwd;

import java.awt.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.prefs.Preferences;

public class Main {
	/**
	 * The Beginning!
	 * If arguments were passed they will be handled, otherwise it will start a ConsoleApplication OR the GuiApplication.
	 * @param args Passed arguments for start
	 */
	public static void main(String[] args) {
		sayHello();
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("--nogui")) {
				// Start the "ConsoleApplication" and ask every parameter
				new ConsoleApplication(false);
			} else if (args[0].equalsIgnoreCase("--usesettings")) {
				// Start the "ConsoleApplication" and start using the saved settings
				new ConsoleApplication(true);
			} else if (args[0].equalsIgnoreCase("--help") || args[0].equalsIgnoreCase("-h")) {
				// Show a message with the available commands
				System.out.println("The following commands are available:");
				System.out.println("--nogui         ==> Start without a GUI");
				System.out.println("--usesettings   ==> Start using your settings and without a GUI");
			} else {
				// Argument(s) unknown; show message with a hint on how to get help
				System.out.println("Unknown command. Use \"--help\" for help.");
			}
		} else {
			// If this is running on a setup without an graphical desktop (eg. Linux without X-Desktop)
			// the ConsoleApplication will get started, otherwise (eg. Windows) the GuiApplication.
			if (GraphicsEnvironment.isHeadless()) {
				new ConsoleApplication(false);
			} else {
				GuiApplication.startGUI();
			}
		}
	}

	/**
	 * Shows an informative message about "Is My Website Down?" on start
	 */
	private static void sayHello() {
		System.out.println("#####################################");
		System.out.println("### Is My Website Down? v" + Main.getVersion() + "    ###");
		System.out.println("###                               ###");
		System.out.println("### © 2012-2013: Marvin Menzerath ###");
		System.out.println("### http://marvin-menzerath.de    ###");
		System.out.println("#####################################\n");
	}

	/**
	 * Displays the version of this "Is My Website Down?"-file
	 * @return The version of this file
	 */
	public static String getVersion() {
		return "1.3.3";
	}

	/**
	 * Compares the version of this "IsMyWebsiteDown?"-file and the servers' version
	 * @return Availability of an update
	 */
	public static boolean isUpdateAvailable() {
		return !getServerVersion().equalsIgnoreCase(getVersion());
	}

	/**
	 * Pulls the current version from the server
	 * @return Server version
	 */
	public static String getServerVersion() {
		String serverVersion;
		try {
			URLConnection connection = new URL("http://marvin-menzerath.de/download/imwd_version.txt").openConnection();
			Scanner sc = new Scanner(connection.getInputStream()).useDelimiter("\\Z");
			serverVersion = sc.next();
			sc.close();
		} catch (Exception e) {
			serverVersion = "Error";
		}
		return serverVersion.trim();
	}

	/**
	 * Pulls the changelog from the server
	 * @return Changelog
	 */
	public static String getServerChangelog() {
		String changelog;
		try {
			URLConnection connection = new URL("http://marvin-menzerath.de/download/imwd_changelog.txt").openConnection();
			Scanner sc = new Scanner(connection.getInputStream()).useDelimiter("\\Z");
			changelog = sc.next();
			sc.close();
		} catch (Exception e) {
			changelog = "Error";
		}
		return changelog.trim();
	}

	/**
	 * Get the Preferences from the user-node AND the "Main"-class
	 * @return Preferences
	 */
	public static Preferences getPreferences() {
		return Preferences.userNodeForPackage(Main.class);
	}

	/**
	 * Get the last used setting OR the default value
	 * @return Last used url OR default value
	 */
	public static String getUrlFromSettings() {
		return getPreferences().get("url", "http://google.com");
	}

	/**
	 * Get the last used setting OR the default value
	 * @return Last used interval OR default value
	 */
	public static int getIntervalFromSettings() {
		return getPreferences().getInt("interval", 30);
	}

	/**
	 * Get the last used setting OR the default value
	 * @return Create logfile OR default value
	 */
	public static boolean getCreateLogFromSettings() {
		return getPreferences().getBoolean("createLog", true);
	}

	/**
	 * Get the last used setting OR the default value
	 * @return Log successful checks OR default value
	 */
	public static boolean getCreateValidLogFromSettings() {
		return getPreferences().getBoolean("createValidLog", false);
	}

	/**
	 * Save the specified value in the settings
	 * @param value The url to save
	 */
	public static void setUrlForSettings(String value) {
		getPreferences().put("url", value);
	}

	/**
	 * Save the specified value in the settings
	 * @param value The interval to save
	 */
	public static void setIntervalForSettings(int value) {
		getPreferences().putInt("interval", value);
	}

	/**
	 * Save the specified value in the settings
	 * @param value "create a log"-setting to save
	 */
	public static void setCreateLogForSettings(boolean value) {
		getPreferences().putBoolean("createLog", value);
	}

	/**
	 * Save the specified value in the settings
	 * @param value "log successful checks"-setting to save
	 */
	public static void setCreateValidLogForSettigs(boolean value) {
		getPreferences().putBoolean("createValidLog", value);
	}
}