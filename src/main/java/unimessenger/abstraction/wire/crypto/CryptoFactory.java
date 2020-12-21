package unimessenger.abstraction.wire.crypto;

import com.wire.bots.cryptobox.CryptoBox;
import unimessenger.Main;

public class CryptoFactory
{
    private static CryptoBox b;

    public static CryptoBox getCryptoInstance()
    {
        if(b == null)
        {
            try
            {
                b = CryptoBox.open(Main.storageDir + "/Box");
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static void closeBox()
    {
        if(b != null && !b.isClosed()) b.close();
    }
}
