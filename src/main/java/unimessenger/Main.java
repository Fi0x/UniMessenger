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

        Out.create("Uni-Messenger starting...").verbose().print();
        Out.create("Initializing storage...").verbose().print();
        WireStorage.init();
        Out.create("Storage initialized").verbose().print();

        Out.create("Checking Disk encryption...").verbose().print();
        MenuDiskCrypto.showMenu();
        Out.create("Disk is decrypted").verbose().print();

        Out.create("Loading login files...").verbose().print();
        WireStorage.readDataFromFiles();
        Out.create("File-loading finished").verbose().print();

        Out.create("Creating Threads for").verbose().print();
        Out.create("Program end").verbose().print();
        stp = new Thread(new Stop());
        Out.create("Updater").verbose().print();
        updt = new Thread(new Updater());
        Out.create("CLI").verbose().print();
        cli = new Thread(new CLI());
        Out.create("Threads created").verbose().print();

        Out.create("Starting updater thread").verbose().print();
        updt.start();
        Out.create("Updater thread started").verbose().print();

        Out.create("Creating new Thread for CLI...").verbose().print();
        cli = new Thread(new CLI());
        Out.create("CLI thread created").verbose().print();

        Out.create("Creating new Thread for GUI...").verbose().print();
        gui = new Thread(() -> MainWindow.launch(MainWindow.class, args));
        Out.create("GUI thread created").verbose().print();

        startUI();
        Out.create("Uni-Messenger started").verbose().print();
    }

    private static void startUI()
    {
        boolean guib = Inputs.getBoolAnswerFrom("Would you like to use a GUI?");

        if(guib)
        {
            Out.create("GUI starting...").verbose().print();
            gui.start();
            Out.create("GUI started").verbose().print();
        } else
        {
            Out.create("Starting CLI thread").verbose().print();
            cli.start();
            Out.create("CLI thread started").verbose().print();
        }

        Out.create("Uni-Messenger started").verbose().print();
    }
}