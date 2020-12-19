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

        Out.newBuilder("Uni-Messenger starting...").vv().print();
        Out.newBuilder("Initializing storage...").v().print();
        WireStorage.init();
        Out.newBuilder("Storage initialized").v().print();

        Out.newBuilder("Checking Disk encryption...").v().print();
        MenuDiskCrypto.showMenu();
        Out.newBuilder("Disk is decrypted").v().print();

        Out.newBuilder("Loading login files...").v().print();
        WireStorage.readDataFromFiles();
        Out.newBuilder("File-loading finished").v().print();

        Out.newBuilder("Creating Threads for").v().print();
        Out.newBuilder("Program end").v().print();
        stp = new Thread(new Stop());
        Out.newBuilder("Updater").v().print();
        updt = new Thread(new Updater());
        Out.newBuilder("CLI").v().print();
        cli = new Thread(new CLI());
        Out.newBuilder("Threads created").v().print();

        Out.newBuilder("Starting updater thread").v().print();
        updt.start();
        Out.newBuilder("Updater thread started").v().print();

        Out.newBuilder("Creating new Thread for CLI...").v().print();
        cli = new Thread(new CLI());
        Out.newBuilder("CLI thread created").v().print();

        Out.newBuilder("Creating new Thread for GUI...").v().print();
        gui = new Thread(() -> MainWindow.launch(MainWindow.class, args));
        Out.newBuilder("GUI thread created").v().print();

        startUI();
        Out.newBuilder("Uni-Messenger started").v().print();
    }

    private static void startUI()
    {
        boolean guib = Inputs.getBoolAnswerFrom("Would you like to use a GUI?");

        if(guib)
        {
            Out.newBuilder("GUI starting...").v().print();
            gui.start();
            Out.newBuilder("GUI started").v().print();
        } else
        {
            Out.newBuilder("Starting CLI thread").v().print();
            cli.start();
            Out.newBuilder("CLI thread started").v().print();
        }

        Out.newBuilder("Uni-Messenger started").v().print();
    }
}