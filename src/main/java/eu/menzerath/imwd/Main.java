package eu.menzerath.imwd;

import eu.menzerath.util.Messages;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.awt.*;

/**
 * This application's entry-class. It contains important constants and the main()-method.
 */
public class Main {
	public static final String APPLICATION = "Is My Website Down?";
	public static final String APPLICATION_SHORT = "IMWD";
	public static final String VERSION = Main.class.getPackage().getImplementationVersion();
	public static final String URL = "https://github.com/MarvinMenzerath/IsMyWebsiteDown";
	public static final String URL_RELEASE = "https://github.com/MarvinMenzerath/IsMyWebsiteDown/releases";
	public static final String USER_AGENT = "IsMyWebsiteDown/" + VERSION + " (" + URL + ")";
	public static final String[] PROTOCOLS = {"http://", "https://"};
	public static final int MIN_INTERVAL = 10;
	public static final int MAX_INTERVAL = 300;

	/**
	 * The Beginning!
	 * If arguments were passed they will be handled, otherwise it will start a ConsoleApplication OR the GuiApplication.
	 *
	 * @param args Passed arguments for start
	 */
	public static void main(String[] args) {
		if (args.length == 0) { // Running on a setup without graphical desktop and no arguments passed: show the needed arguments
			if (GraphicsEnvironment.isHeadless()) {
				printHelp();
			} else { // Otherwise: Open the GUI
				GuiApplication.startGUI();
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("--help")) { // Give the user some help
			printHelp();
		} else if (args.length == 2) { // Start a ConsoleApplication and create a Log-File
			new ConsoleApplication(args[0].trim(), args[1].trim(), true);
		} else if (args.length == 3 && args[2].equalsIgnoreCase("--nolog")) { // Start a ConsoleApplication but do not create a Log-File
			new ConsoleApplication(args[0].trim(), args[1].trim(), false);
		} else if (args.length == 3 && args[2].equalsIgnoreCase("--once")) { // Run a QuickTest
			new QuickTest(args[0].trim()).run();
		} else { // Something went wrong (blame the user!)
			printHelp();
		}
	}

	/**
	 * Shows an informative message about "Is My Website Down?" on start
	 */
	public static void sayHello() {
		AnsiConsole.systemInstall();

		String lineVersion = APPLICATION + " v" + VERSION;
		for (int i = lineVersion.length(); i < 42; i++) lineVersion += " ";

		System.out.println("##################################################");
		System.out.println("### " + new Ansi().fg(Ansi.Color.GREEN).bold().a(lineVersion).reset() + " ###");
		System.out.println("###                                            ###");
		System.out.println("### " + Messages.COPYRIGHT + "              ###");
		System.out.println("### " + URL.substring(8) + " ###");
		System.out.println("##################################################\n");
	}

	/**
	 * Gives the user a colored help-message including a few examples
	 */
	public static void printHelp() {
		sayHello();
		System.out.println(new Ansi().bold().a(Messages.CONSOLE_HELP).reset());
		System.out.println(Messages.CONSOLE_HELP_EXAMPLES);
	}
}