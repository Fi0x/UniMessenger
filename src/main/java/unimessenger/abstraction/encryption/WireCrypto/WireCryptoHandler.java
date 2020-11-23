package unimessenger.abstraction.encryption.WireCrypto;

import com.wire.bots.cryptobox.CryptoException;
import com.wire.bots.cryptobox.PreKey;

public class WireCryptoHandler {
    public static Prekey[] generatePreKeys(){
        Prekey [] Keys = new Prekey[5];
        Prekey lastPreKey;

        try {
            lastPreKey = new Prekey(CryptoFactory.getCryptoInstance().newLastPreKey());
            PreKey [] keyTemp = CryptoFactory.getCryptoInstance().newPreKeys(0, Keys.length-1);
            for(int i = 0;i < keyTemp.length; i++){
                Keys[i] = new Prekey(keyTemp[i]);
            }
            Keys[Keys.length-1] = lastPreKey;

        } catch (CryptoException e) {
            e.printStackTrace();
            cleanUp();
        }

        return Keys;
    }

    public static void cleanUp(){
        CryptoFactory.closeBox();
    }
}
