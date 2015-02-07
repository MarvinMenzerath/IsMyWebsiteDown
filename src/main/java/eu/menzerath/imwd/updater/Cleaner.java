package eu.menzerath.imwd.updater;

import eu.menzerath.imwd.Main;
import eu.menzerath.util.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class Cleaner {

    public static void main(String[] args) {
        Main.sayHello();

        // give the Updater time to finish
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        // args[0] has to be the name of the old file
        if (args.length != 2 || args[0].isEmpty() || args[1].isEmpty()) System.exit(1);
        new Cleaner(args[0], args[1]);
    }

    /**
     * The Cleaning-Crew: Removes the old jar-file and starts the Main-class of the new jar-file
     *
     * @param oldFileName Name of the old jar-file
     */
    public Cleaner(String oldFileName, String startNewVersion) {
        Logger.info("Starting Cleaner...");

        File oldFile = new File(oldFileName);
        boolean startNewVersionBoolean = Boolean.valueOf(startNewVersion);

        // get this file's name
        String currentFileName = "";
        try {
            CodeSource cSource = Cleaner.class.getProtectionDomain().getCodeSource();
            File currentVersion = new File(cSource.getLocation().toURI().getPath());
            currentFileName = currentVersion.getName();
        } catch (URISyntaxException e) {
            Logger.error("Unable to get this file's name: " + e.getMessage());
        }

        // try to delete the old file
        if (oldFile.delete()) {
            Logger.info("Old file successfully deleted");
        } else {
            Logger.error("Unable to delete " + oldFileName);
        }

        // start the new version (if wanted)
        if (startNewVersionBoolean) {
            try {
                Runtime.getRuntime().exec("java -jar " + currentFileName);
            } catch (IOException e) {
                Logger.error("Unable to start " + currentFileName);
            }
        }

        System.exit(0);
    }
}