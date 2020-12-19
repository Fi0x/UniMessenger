package unimessenger.util;

import unimessenger.Main;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.abstraction.wire.crypto.CryptoFactory;
import unimessenger.userinteraction.tui.Out;

public class Stop implements Runnable
{
    @Override
    public void run()
    {
        Out.create("Stopping update thread...").verbose().print();
        Main.updt.interrupt();
        Out.create("Update thread stopped").verbose().print();

        Out.create("Stopping CLI thread...").verbose().print();
        Main.cli.interrupt();
        Out.create("CLI thread stopped").verbose().print();

        Out.create("Stopping GUI thread...").verbose().print();
        Main.gui.stop();
        Out.create("GUI thread stopped").verbose().print();

        Out.create("Writing data to file...").verbose().print();
        WireStorage.saveDataInFile();
        Out.create("Storage written to file").verbose().print();

        Out.create("Cleaning the Box").verbose().print();
        CryptoFactory.closeBox();
        Out.create("Box Clean").verbose().print();

        Out.create("Exiting program...").verbose().print();
        System.exit(0);
    }
}