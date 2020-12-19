package unimessenger;

import unimessenger.abstraction.storage.WireStorage;
import unimessenger.userinteraction.gui.MainWindow;
import unimessenger.userinteraction.tui.CLI;
import unimessenger.userinteraction.tui.Inputs;
import unimessenger.userinteraction.tui.Out;
import unimessenger.userinteraction.tui.menu.MenuDiskCrypto;
import unimessenger.util.Stop;
import unimessenger.util.Updater;

import java.util.ArrayList;
import java.util.Arrays;

public class Main
{
    public static Thread cli;
    public static Thread gui;
    public static Thread updt;
    public static Thread stp;

    public static void main(String[] args)
    {
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));

        if(arguments.contains("-d")) Out.d = true;
        if(arguments.contains("-v")) Out.v = true;
        if(arguments.contains("-vv")) Out.vv = true;
        if(arguments.contains("-vvv")) Out.vvv = true;

        Out.create("Uni-Messenger starting...").v().print();
        Out.create("Initializing storage...").v().print();
        WireStorage.init();
        Out.create("Storage initialized").v().print();

        Out.create("Checking Disk encryption...").v().print();
        MenuDiskCrypto.showMenu();
        Out.create("Disk is decrypted").v().print();

        Out.create("Loading login files...").v().print();
        WireStorage.readDataFromFiles();
        Out.create("File-loading finished").v().print();

        Out.create("Creating Threads for").v().print();
        Out.create("Program end").v().print();
        stp = new Thread(new Stop());
        Out.create("Updater").v().print();
        updt = new Thread(new Updater());
        Out.create("CLI").v().print();
        cli = new Thread(new CLI());
        Out.create("Threads created").v().print();

        Out.create("Starting updater thread").v().print();
        updt.start();
        Out.create("Updater thread started").v().print();

        Out.create("Creating new Thread for CLI...").v().print();
        cli = new Thread(new CLI());
        Out.create("CLI thread created").v().print();

        Out.create("Creating new Thread for GUI...").v().print();
        gui = new Thread(() -> MainWindow.launch(MainWindow.class, args));
        Out.create("GUI thread created").v().print();

        startUI();
        Out.create("Uni-Messenger started").v().print();
    }

    private static void startUI()
    {
        boolean guib = Inputs.getBoolAnswerFrom("Would you like to use a GUI?");

        if(guib)
        {
            Out.create("GUI starting...").v().print();
            gui.start();
            Out.create("GUI started").v().print();
        } else
        {
            Out.create("Starting CLI thread").v().print();
            cli.start();
            Out.create("CLI thread started").v().print();
        }

        Out.create("Uni-Messenger started").v().print();
    }
}