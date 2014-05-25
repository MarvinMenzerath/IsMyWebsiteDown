package eu.menzerath.util;

import eu.menzerath.imwd.Main;
import org.junit.Assert;
import org.junit.Test;

public class UpdaterTest {

    @Test
    public void testRefreshVersion() {
        Updater updater = new Updater();

        if (Main.VERSION.contains("SNAPSHOT")) {
            Assert.assertEquals("Error", updater.getServerVersion());
        } else {
            Assert.assertFalse(updater.getServerVersion().equals("Error"));
        }
    }

    @Test
    public void testRefreshChangelog() {
        Updater updater = new Updater();

        if (Main.VERSION.contains("SNAPSHOT")) {
            Assert.assertEquals("Error", updater.getServerChangelog());
        } else {
            Assert.assertFalse(updater.getServerChangelog().equals("Error"));
        }
    }
}
