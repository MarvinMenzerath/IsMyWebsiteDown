package eu.menzerath.util;

import eu.menzerath.imwd.Main;

public class Messages {
    // START: Main
    public static final String INVALID_ARGUMENTS = "You have to pass the following arguments in this order:\n--url=http://website.com --interval=30";
    public static final String INVALID_PARAMETERS = "Enter a valid URL and interval:\nURL: Starts with \"http://\"\nInterval: Only Numbers, between " + Main.MIN_INTERVAL + " and " + Main.MAX_INTERVAL;
    // END: Main

    // START: Message-Bubbles
    public static final String OK = "OK";
    public static final String ERROR_NOT_REACHABLE_TITLE = "Not Reachable";
    public static final String ERROR_NOT_REACHABLE_PING = "Sorry, but I was unable to receive Content from your Website while a Ping was successful.";
    public static final String ERROR_NOT_REACHABLE_NO_PING = "Uh... It seems like that your entire Server is gone!";
    public static final String ERROR_NO_CONNECTION_TITLE = "No Connection";
    public static final String ERROR_NO_CONNECTION = "Please check our Connection to the Internet.";
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
    public static final String ABOUT_AUTHOR = "© 2012-2014: Marvin Menzerath - http://menzerath.eu";
    public static final String NO_CHECK_SELECTED = "You have to select at least one Check-Type (Content / Ping)!";
    public static final String AUTORUN_ERROR = "Could not copy " + Main.APPLICATION_SHORT + " to your Autorun. Please check...\n\n  * You are allowed to copy files to the Autorun-Folder.\n  * You are running Windows Vista or higher.";
    // END: GuiApplication

    // START: Updates
    public static final String UPDATE_NO_UPDATE = "No Update found!";
    public static final String UPDATE_NO_UPDATE_LONG = "You are running the latest version of \"" + Main.APPLICATION + "\" (v" + Main.VERSION + ").";
    public static final String UPDATE_ERROR = "Unable to search for Updates. Please visit \"" + Main.URL_RELEASE + "\".";
    public static final String UPDATE_AVAILABLE_TITLE = "Update available";
    public static final String UPDATE_AVAILABLE = "There is an Update for " + Main.APPLICATION_SHORT + " to version %version available.";
    public static final String UPDATE_AVAILABLE_CHANGES = "Changes: %changes\nVisit \"" + Main.URL_RELEASE + "\" for a full Changelog.";
    public static final String UPDATE_NOW = "Do you want to Download it now?";
    public static final String UPDATE_MANUAL = "Download Update using \"wget " + Main.URL_RELEASE + "/download/v%version/IMWD.jar\".";
    // END: Updates
}
