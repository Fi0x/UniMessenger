package unimessenger.util;

import unimessenger.Main;
import unimessenger.abstraction.wire.crypto.CryptoFactory;
import unimessenger.abstraction.wire.storage.Storage;
import unimessenger.userinteraction.tui.Out;

public class Stop implements Runnable
{
    @Override
    public void run()
    {
        Out.newBuilder("Stopping update thread...").v().print();
        Main.updt.interrupt();
        Out.newBuilder("Update thread stopped").v().print();

        Out.newBuilder("Stopping CLI thread...").v().print();
        Main.cli.interrupt();
        Out.newBuilder("CLI thread stopped").v().print();

        Out.newBuilder("Stopping GUI thread...").v().print();
        Main.gui.stop();
        Out.newBuilder("GUI thread stopped").v().print();

        Out.newBuilder("Writing data to file...").v().print();
        Storage.getInstance().saveDataInFile();
        Out.newBuilder("Storage written to file").v().print();

        Out.newBuilder("Cleaning the Box").v().print();
        CryptoFactory.closeBox();
        Out.newBuilder("Box Clean").v().print();

        Out.newBuilder("Exiting program...").v().print();
        System.exit(0);
    }
}