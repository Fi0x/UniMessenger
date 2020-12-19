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
        Out.create("Stopping update thread...").v().print();
        Main.updt.interrupt();
        Out.create("Update thread stopped").v().print();

        Out.create("Stopping CLI thread...").v().print();
        Main.cli.interrupt();
        Out.create("CLI thread stopped").v().print();

        Out.create("Stopping GUI thread...").v().print();
        Main.gui.stop();
        Out.create("GUI thread stopped").v().print();

        Out.create("Writing data to file...").v().print();
        WireStorage.saveDataInFile();
        Out.create("Storage written to file").v().print();

        Out.create("Cleaning the Box").v().print();
        CryptoFactory.closeBox();
        Out.create("Box Clean").v().print();

        Out.create("Exiting program...").v().print();
        System.exit(0);
    }
}