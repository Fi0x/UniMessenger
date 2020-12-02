package unimessenger.abstraction.storage;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class StorageCrypto {
    SecretKey key;
    Cipher cipher;

    public StorageCrypto(SecretKey key, String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.key = key;
        this.cipher = Cipher.getInstance(transformation);
    }

    public void encrypt(String content, String file) throws InvalidKeyException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] iv = cipher.getIV();

        try (FileOutputStream fileOut = new FileOutputStream(file);
             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
            fileOut.write(iv);
            cipherOut.write(content.getBytes());
        }
    }

    public String decrypt(String file) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException {
        String content;

        try (FileInputStream fileIn = new FileInputStream(file)) {
            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(fileIv));

            try (
                    CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                    InputStreamReader inputReader = new InputStreamReader(cipherIn);
                    BufferedReader reader = new BufferedReader(inputReader)
            ) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
            }

        }
        return content;
    }
}
