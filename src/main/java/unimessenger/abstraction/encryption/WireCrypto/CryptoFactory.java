package unimessenger.abstraction.encryption.WireCrypto;

import com.wire.bots.cryptobox.CryptoBox;

public class CryptoFactory {
    private static CryptoBox b;

    public static CryptoBox getCryptoInstance(){
        if (b == null){
            try {
                b = CryptoBox.open("TestDir");          //Todo Change to better Dire name if i know what it really does
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return b;
    }
}
