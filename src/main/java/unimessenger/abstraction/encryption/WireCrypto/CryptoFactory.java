package unimessenger.abstraction.encryption.WireCrypto;

import com.wire.bots.cryptobox.CryptoBox;

public class CryptoFactory { //TODO IMPORTANT make sure this is ALWAYS closed on program exit data leak is security relevant
    private static CryptoBox b;

    public static CryptoBox getCryptoInstance(){
        if (b == null){
            try {
                b = CryptoBox.open("DataStorage/Box");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return b;
    }

    public static boolean closeBox(){
        b.close();
        return true;
    }
}
