package unimessenger.abstraction.encryption.WireCrypto;

import com.wire.bots.cryptobox.CryptoException;
import com.wire.bots.cryptobox.PreKey;

public class WireCryptoHandler {
    public static Prekey[] generatePreKeys(){
        Prekey [] Keys = new Prekey[51];
        Prekey lastPreKey;

        try {
            lastPreKey = new Prekey(CryptoFactory.getCryptoInstance().newLastPreKey());
            PreKey [] keyTemp = CryptoFactory.getCryptoInstance().newPreKeys(0, 50);
            for(int i = 1;i < keyTemp.length; i++){
                Keys[i+1] = new Prekey(keyTemp[i]);
            }
            Keys[0] = lastPreKey;

        } catch (CryptoException e) {
            e.printStackTrace();
        }

        return Keys;
    }

    public static void cleanUp(){
        CryptoFactory.closeBox();
    }
}
