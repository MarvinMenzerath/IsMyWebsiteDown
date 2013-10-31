package de.menzerath.imwd;

import java.awt.*;
import java.util.prefs.Preferences;

public class Main {
    private static final String IMWD_VERSION = "1.3.5";
    private static final String PREF_URL = "url";
    private static final String PREF_INTERVAL = "interval";
    private static final String PREF_CREATELOG = "createLog";
    private static final String PREF_CREATEVALIDLOG = "createValidLog";

    /**
     * The Beginning!
     * If arguments were passed they will be handled, otherwise it will start a ConsoleApplication OR the GuiApplication.
     *
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
     *
     * @return The version of this file
     */
    public static String getVersion() {
        return IMWD_VERSION;
    }

    /**
     * Get the Preferences from the user-node AND the "Main"-class
     *
     * @return Preferences
     */
    public static Preferences getPreferences() {
        return Preferences.userNodeForPackage(Main.class);
    }

    /**
     * Get the last used setting OR the default value
     *
     * @return Last used url OR default value
     */
    public static String getUrlFromSettings() {
        return getPreferences().get(PREF_URL, "http://google.com");
    }

    /**
     * Get the last used setting OR the default value
     *
     * @return Last used interval OR default value
     */
    public static int getIntervalFromSettings() {
        return getPreferences().getInt(PREF_INTERVAL, 30);
    }

    /**
     * Get the last used setting OR the default value
     *
     * @return Create logfile OR default value
     */
    public static boolean getCreateLogFromSettings() {
        return getPreferences().getBoolean(PREF_CREATELOG, false);
    }

    /**
     * Get the last used setting OR the default value
     *
     * @return Log successful checks OR default value
     */
    public static boolean getCreateValidLogFromSettings() {
        return getPreferences().getBoolean(PREF_CREATEVALIDLOG, false);
    }

    /**
     * Save the specified value in the settings
     *
     * @param value The url to save
     */
    public static void setUrlForSettings(String value) {
        getPreferences().put(PREF_URL, value);
    }

    /**
     * Save the specified value in the settings
     *
     * @param value The interval to save
     */
    public static void setIntervalForSettings(int value) {
        getPreferences().putInt(PREF_INTERVAL, value);
    }

    /**
     * Save the specified value in the settings
     *
     * @param value "create a log"-setting to save
     */
    public static void setCreateLogForSettings(boolean value) {
        getPreferences().putBoolean(PREF_CREATELOG, value);
    }

    /**
     * Save the specified value in the settings
     *
     * @param value "log successful checks"-setting to save
     */
    public static void setCreateValidLogForSettigs(boolean value) {
        getPreferences().putBoolean(PREF_CREATEVALIDLOG, value);
    }
}