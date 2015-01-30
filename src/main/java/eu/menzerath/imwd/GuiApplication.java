package eu.menzerath.imwd;

import eu.menzerath.util.Helper;
import eu.menzerath.util.Messages;
import eu.menzerath.util.Updater;
import org.fusesource.jansi.Ansi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class GuiApplication extends JFrame {
    // Tray-Icons
    private static final Image iconOk = Toolkit.getDefaultToolkit().getImage(GuiApplication.class.getResource("/icons/ok.png"));
    private static final Image iconWarning = Toolkit.getDefaultToolkit().getImage(GuiApplication.class.getResource("/icons/warning.png"));
    private static final Image iconError = Toolkit.getDefaultToolkit().getImage(GuiApplication.class.getResource("/icons/error.png"));
    private static final Image iconNoConnection = Toolkit.getDefaultToolkit().getImage(GuiApplication.class.getResource("/icons/noConnection.png"));

    // GUI-Elements
    private static JFrame frame;
    private JPanel mainPanel;
    private JMenu mnChecker;
    private JMenu mnLogs;
    private JPanel websiteSettings1;
    private JPanel websiteSettings2;
    private JPanel websiteSettings3;
    private JPanel websiteSettings4;
    private JPanel websiteSettings5;
    private JPanel websiteSettings6;
    private JPanel websiteSettings7;
    private JPanel websiteSettings8;
    private JTextField url1;
    private JTextField url2;
    private JTextField url3;
    private JTextField url4;
    private JTextField url5;
    private JTextField url6;
    private JTextField url7;
    private JTextField url8;
    private JTextField interval1;
    private JTextField interval2;
    private JTextField interval3;
    private JTextField interval4;
    private JTextField interval5;
    private JTextField interval6;
    private JTextField interval7;
    private JTextField interval8;
    private JCheckBox content1;
    private JCheckBox content2;
    private JCheckBox content3;
    private JCheckBox content4;
    private JCheckBox content5;
    private JCheckBox content6;
    private JCheckBox content7;
    private JCheckBox content8;
    private JCheckBox ping1;
    private JCheckBox ping2;
    private JCheckBox ping3;
    private JCheckBox ping4;
    private JCheckBox ping5;
    private JCheckBox ping6;
    private JCheckBox ping7;
    private JCheckBox ping8;
    private JButton startButton;
    private JButton stopButton;

    // GUI-Element-Arrays
    private JPanel[] websiteSettings;
    private JTextField[] url;
    private JTextField[] interval;
    private JCheckBox[] content;
    private JCheckBox[] ping;

    // Other
    private static final int maxCheckerId = 8;
    private static TrayIcon[] trayIcon = new TrayIcon[maxCheckerId];
    private Checker[] checker = new Checker[maxCheckerId];

    /**
     * Start the GUI!
     * Prepares everything and then shows the form
     */
    public static void startGUI() {
        Main.sayHello();

        // For an nicer look according to the used OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        frame = new JFrame("GuiApplication");
        GuiApplication gui = new GuiApplication();
        frame.setContentPane(gui.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle(Main.APPLICATION);
        frame.setIconImage(iconOk);
        frame.setResizable(false);
        if (!SettingsManager.getAutorunFromSettings()) frame.setVisible(true);

        // Run an update-check
        runUpdateCheck(true);

        if (SettingsManager.getAutorunFromSettings()) {
            for (int i = 0; i < SettingsManager.getCheckerCountFromSettings(); i++) {
                gui.start(i);
            }
        }
    }

    /**
     * Gives every button an action and adds an JMenuBar
     */
    public GuiApplication() {
        // Load important GUI-elements
        websiteSettings = new JPanel[]{websiteSettings1, websiteSettings2, websiteSettings3, websiteSettings4, websiteSettings5, websiteSettings6, websiteSettings7, websiteSettings8};
        url = new JTextField[]{url1, url2, url3, url4, url5, url6, url7, url8};
        interval = new JTextField[]{interval1, interval2, interval3, interval4, interval5, interval6, interval7, interval8};
        content = new JCheckBox[]{content1, content2, content3, content4, content5, content6, content7, content8};
        ping = new JCheckBox[]{ping1, ping2, ping3, ping4, ping5, ping6, ping7, ping8};

        // Load saved / default values
        for (int i = 0; i < maxCheckerId; i++) {
            url[i].setText(SettingsManager.getUrlFromSettings(i));
            interval[i].setText("" + SettingsManager.getIntervalFromSettings(i));
            content[i].setSelected(SettingsManager.getCheckContentFromSettings(i));
            ping[i].setSelected(SettingsManager.getCheckPingFromSettings(i));
        }

        // How many Checkers will be shown
        final int checkerAmount = SettingsManager.getCheckerCountFromSettings();
        addWebsiteSettings(checkerAmount);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean allowStart= true;
                for (int i = 0; i < SettingsManager.getCheckerCountFromSettings(); i++) {
                    if (!checkInput(i)) allowStart = false;
                }

                if (allowStart) {
                    for (int i = 0; i < SettingsManager.getCheckerCountFromSettings(); i++) {
                        start(i);
                    }
                }
            }
        });

        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop();
            }
        });

        // START: JMenuBar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // START: File-Menu
        JMenu mnFile = new JMenu("File");
        JMenuItem mntmAbout = new JMenuItem("About");
        JMenuItem mntmExit = new JMenuItem("Exit");
        menuBar.add(mnFile);

        mntmAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(null,
                        Main.APPLICATION + " - Version " + Main.VERSION +
                                "\n\n" + Messages.ABOUT_ICONS +
                                "\n" + Messages.ABOUT_SOURCE +
                                "\n" + Messages.ABOUT_AUTHOR, "About", JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        mnFile.add(mntmAbout);

        mntmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                for (int i = 0; i < 5; i++) {
                    try {
                        checker[i].stopTesting();
                    } catch (NullPointerException ignored) {
                    }
                }
                System.exit(0);
            }
        });
        mnFile.add(mntmExit);
        // END: File-Menu

        // START: Checker-Menu
        mnChecker = new JMenu("Checker");

        final JRadioButtonMenuItem[] rb = new JRadioButtonMenuItem[maxCheckerId];
        ButtonGroup checkerGroup = new ButtonGroup();
        for (int i = 0; i < maxCheckerId; i++) {
            final int j = i + 1;
            rb[i] = new JRadioButtonMenuItem("" + (i + 1));
            rb[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SettingsManager.setCheckerCountForSettings(j);
                    addWebsiteSettings(j);
                }
            });
            checkerGroup.add(rb[i]);
            mnChecker.add(rb[i]);
        }
        rb[checkerAmount - 1].setSelected(true);

        menuBar.add(mnChecker);
        // END: Checker-Menu

        // START: Log-Menu
        mnLogs = new JMenu("Logs");
        final JCheckBoxMenuItem cbLogEnable = new JCheckBoxMenuItem("Enable");
        final JCheckBoxMenuItem cbLogValid = new JCheckBoxMenuItem("Log valid Checks");
        menuBar.add(mnLogs);

        cbLogEnable.setSelected(SettingsManager.getCreateLogFromSettings());
        cbLogEnable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsManager.setCreateLogForSettings(cbLogEnable.isSelected());

                if (!cbLogEnable.isSelected()) {
                    cbLogValid.setEnabled(false);
                    cbLogValid.setSelected(false);
                    SettingsManager.setCreateValidLogForSettigs(false);
                } else {
                    cbLogValid.setEnabled(true);
                }
            }
        });
        mnLogs.add(cbLogEnable);

        cbLogValid.setEnabled(cbLogEnable.isSelected());
        cbLogValid.setSelected(SettingsManager.getCreateValidLogFromSettings());
        cbLogValid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsManager.setCreateValidLogForSettigs(cbLogValid.isSelected());
            }
        });
        mnLogs.add(cbLogValid);
        // END: Log-Menu

        // START: Tools-Menu
        JMenu mnTools = new JMenu("Tools");
        final JCheckBoxMenuItem cbAutorun = new JCheckBoxMenuItem("Start with Windows");
        menuBar.add(mnTools);

        cbAutorun.setSelected(SettingsManager.getAutorunFromSettings());
        if (SettingsManager.getAutorunFromSettings())
            Helper.addToAutorun(); // If there was an update, update file in Autorun!
        cbAutorun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SettingsManager.setAutorunForSettigs(cbAutorun.isSelected());

                if (cbAutorun.isSelected()) {
                    if (!Helper.addToAutorun()) {
                        JOptionPane.showMessageDialog(null, Messages.AUTORUN_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    Helper.removeFromAutorun();
                }
            }
        });

        // Do not show these items if the user doesn't use Windows
        if (!System.getProperty("os.name").startsWith("Windows")) {
            cbAutorun.setVisible(false);
        } else if (System.getProperty("os.name").equals("Windows XP")) {
            cbAutorun.setVisible(false);
        }
        mnTools.add(cbAutorun);

        JMenuItem mntmUpdates = new JMenuItem("Check for Updates");
        mntmUpdates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runUpdateCheck(false);
            }
        });
        mnTools.add(mntmUpdates);
        // END: Tools-Menu

        // START: Other-Menu
        JMenu mnOther = new JMenu("Other");
        final JCheckBoxMenuItem cbNotificationBubbles = new JCheckBoxMenuItem("Show Notification-Bubbles");
        menuBar.add(mnOther);

        cbNotificationBubbles.setSelected(SettingsManager.getShowBubblesSettings());
        cbNotificationBubbles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SettingsManager.setShowBubblesSettigs(cbNotificationBubbles.isSelected());
            }
        });
        mnOther.add(cbNotificationBubbles);
        // END: Other-Menu
        // END: JMenuBar
    }

    /**
     * This will create an TrayIcon and show information about the current check(s)
     */
    private static void createTrayIcon(int checkerId) {
        // Not supported? Bye, Bye!
        if (!SystemTray.isSupported()) {
            System.out.println(new Ansi().fg(Ansi.Color.RED).bold().a("[ERROR]").reset() + " " + "SystemTray is not supported. Exiting...");
            System.exit(1);
        }

        SystemTray tray = SystemTray.getSystemTray();
        trayIcon[checkerId] = new TrayIcon(iconOk, Main.APPLICATION);
        trayIcon[checkerId].setImageAutoSize(true);
        trayIcon[checkerId].setToolTip("Stopped - " + Main.APPLICATION_SHORT);
        trayIcon[checkerId].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
            }
        });

        try {
            tray.add(trayIcon[checkerId]);
        } catch (AWTException e) {
            // Not possible? Bye, Bye!
            System.out.println(new Ansi().fg(Ansi.Color.RED).bold().a("[ERROR]").reset() + " " + "TrayIcon could not be added. Exiting...");
            System.exit(1);
        }
    }

    /**
     * Check whether a particular Checker is allowed to start
     * @param checkerId Checker to check
     */
    private boolean checkInput(int checkerId) {
        if (!Helper.validateUrlInput(url[checkerId].getText().trim()) || !Helper.validateIntervalInput(Helper.parseInt(interval[checkerId].getText().trim()))) {
            // +1 to be more user-friendly
            JOptionPane.showMessageDialog(null, Messages.INVALID_PARAMETERS, "Invalid Input (Website " + (checkerId + 1) + ")", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Start testing!
     * Prepares the GUI, the TrayIcon and starts the Checker
     */
    private void start(int checkerId) {
        String cUrl = url[checkerId].getText().trim();
        int cInterval = Helper.parseInt(interval[checkerId].getText().trim());
        boolean cContent = content[checkerId].isSelected();
        boolean cPing = ping[checkerId].isSelected();

        createTrayIcon(checkerId);
        trayIcon[checkerId].setToolTip("Running - " + Main.APPLICATION_SHORT);

        // Disable/Dispose GUI(-elements)
        frame.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mnChecker.setEnabled(false);
        mnLogs.setEnabled(false);
        for (int i = 0; i < maxCheckerId; i++) {
            this.url[i].setEnabled(false);
            this.interval[i].setEnabled(false);
            this.content[i].setEnabled(false);
            this.ping[i].setEnabled(false);
        }
        startButton.setEnabled(false);
        stopButton.setEnabled(true);

        // Save the values
        SettingsManager.setUrlForSettings(checkerId, cUrl);
        SettingsManager.setIntervalForSettings(checkerId, cInterval);
        SettingsManager.setCheckContentForSettings(checkerId, cContent);
        SettingsManager.setCheckPingForSettings(checkerId, cPing);

        // Create the Checker
        checker[checkerId] = new Checker(checkerId, cUrl, cInterval, cContent, cPing, SettingsManager.getCreateLogFromSettings(), SettingsManager.getCreateValidLogFromSettings(), true);
        checker[checkerId].startTesting();
    }

    /**
     * Stop testing!
     * Prepares the GUI, the TrayIcon and stops the Checker
     */
    private void stop() {
        SystemTray tray = SystemTray.getSystemTray();
        for (int i = 0; i < maxCheckerId; i++) {
            try {
                tray.remove(trayIcon[i]);
                checker[i].stopTesting();
            } catch (Exception ignored) {
            }
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Enable GUI-elements
        mnChecker.setEnabled(true);
        mnLogs.setEnabled(true);
        for (int i = 0; i < maxCheckerId; i++) {
            this.url[i].setEnabled(true);
            this.interval[i].setEnabled(true);
            this.content[i].setEnabled(true);
            this.ping[i].setEnabled(true);
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    /**
     * Hide unused Checkers and show the needed ones
     * @param checkerAmount Amount of used Websites
     */
    private void addWebsiteSettings(int checkerAmount) {
        for (int i = 0; i < maxCheckerId; i++) {
            websiteSettings[i].setVisible(true);
        }
        for (int i = checkerAmount; i < maxCheckerId; i++) {
            websiteSettings[i].setVisible(false);
        }
        frame.pack();
    }

    /**
     * An update-check for the "GuiApplication": If there is an update available, it will show an message and a button to open
     * the website in a browser.
     *
     * @param startup Running this on startup? Then don't show "error"'s or "ok"'s.
     */
    private static void runUpdateCheck(final boolean startup) {
        Thread thread = new Thread() {
            public void run() {
                Updater myUpdater = new Updater();
                if (myUpdater.getServerVersion().equalsIgnoreCase("Error")) {
                    // Show this message if the Updater was created by the user
                    if (!startup) {
                        JOptionPane.showMessageDialog(null, Messages.UPDATE_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (myUpdater.getServerVersion().equalsIgnoreCase("SNAPSHOT")) {
                    // Show this message if the Updater was created by the user
                    if (!startup) {
                        JOptionPane.showMessageDialog(null, Messages.UPDATE_SNAPSHOT, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (myUpdater.isUpdateAvailable()) {
                    int value = JOptionPane.showConfirmDialog(null, Messages.UPDATE_AVAILABLE.replace("%version", myUpdater.getServerVersion()) +
                            "\n" + Messages.UPDATE_AVAILABLE_CHANGES.replace("%changes", myUpdater.getServerChangelog()) +
                            "\n\n" + Messages.UPDATE_NOW, Messages.UPDATE_AVAILABLE_TITLE, JOptionPane.YES_NO_OPTION);
                    if (value == JOptionPane.YES_OPTION) {
                        try {
                            Desktop.getDesktop().browse(new URI(Main.URL_RELEASE));
                        } catch (Exception ignored) {
                        }
                        System.exit(0);
                    }
                } else {
                    // Show this message if the Updater was created by the user
                    if (!startup) {
                        JOptionPane.showMessageDialog(null, Messages.UPDATE_NO_UPDATE_LONG, Messages.UPDATE_NO_UPDATE, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        };
        thread.start();
    }

    /**
     * Changes TrayIcon (icon, tooltip) and shows a message
     *
     * @param checker     Which Checker updates the TrayIcon
     * @param status      The test-result
     * @param showMessage Display a message
     */
    public static void updateTrayIcon(Checker checker, int status, boolean showMessage) {
        if (status == 1) {
            trayIcon[checker.ID].setImage(iconOk);
            trayIcon[checker.ID].setToolTip(Messages.OK + " - " + Main.APPLICATION_SHORT + "\n" + checker.getUrlWithoutProtocol());
        } else if (status == 2) {
            trayIcon[checker.ID].setImage(iconWarning);
            trayIcon[checker.ID].setToolTip(Messages.ERROR_NOT_REACHABLE_TITLE + " - " + Main.APPLICATION_SHORT + "\n" + checker.getUrlWithoutProtocol());
            if (showMessage && SettingsManager.getShowBubblesSettings())
                trayIcon[checker.ID].displayMessage(Messages.ERROR_NOT_REACHABLE_TITLE + ": " + checker.getUrlWithoutProtocol(), Messages.ERROR_NOT_REACHABLE_PING, TrayIcon.MessageType.WARNING);
        } else if (status == 3) {
            trayIcon[checker.ID].setImage(iconError);
            trayIcon[checker.ID].setToolTip(Messages.ERROR_NOT_REACHABLE_TITLE + " - " + Main.APPLICATION_SHORT + "\n" + checker.getUrlWithoutProtocol());
            if (showMessage && SettingsManager.getShowBubblesSettings())
                trayIcon[checker.ID].displayMessage(Messages.ERROR_NOT_REACHABLE_TITLE + ": " + checker.getUrlWithoutProtocol(), Messages.ERROR_NOT_REACHABLE_NO_PING, TrayIcon.MessageType.ERROR);
        } else if (status == 4) {
            trayIcon[checker.ID].setImage(iconNoConnection);
            trayIcon[checker.ID].setToolTip(Messages.ERROR_NO_CONNECTION_TITLE + " - " + Main.APPLICATION_SHORT + "\n" + checker.getUrlWithoutProtocol());
            if (showMessage && SettingsManager.getShowBubblesSettings())
                trayIcon[checker.ID].displayMessage(Messages.ERROR_NO_CONNECTION_TITLE, Messages.ERROR_NO_CONNECTION, TrayIcon.MessageType.ERROR);
        }
    }
}