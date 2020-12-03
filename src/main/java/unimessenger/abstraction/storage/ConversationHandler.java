package unimessenger.abstraction.storage;

import unimessenger.abstraction.wire.structures.WireConversation;
import unimessenger.userinteraction.Outputs;

import java.io.*;
import java.util.ArrayList;

public class ConversationHandler implements Serializable
{
    private static ConversationHandler cH;
    private static final String FILEPATH = WireStorage.storageDirectory + "/Chats";
    private ArrayList<WireConversation> conversations;

    public ConversationHandler()
    {
        conversations = new ArrayList<>();
    }

    public static void clearFile()
    {
        //TODO: Delete or overwrite file

    }

    public ArrayList<WireConversation> getConversations()
    {
        return conversations;
    }

    public void newConversation(WireConversation c)
    {
        conversations.add(c);
    }

    public void clearConvs()
    {
        conversations = new ArrayList<>();
    }

    public static ConversationHandler getInstance()
    {
        if(cH == null)
        {
            try(FileInputStream fis = new FileInputStream(FILEPATH); ObjectInputStream ois = new ObjectInputStream(fis))
            {
                cH = (ConversationHandler) ois.readObject();
            } catch(IOException | ClassNotFoundException ex)
            {
                Outputs.create("ConnectionHandler not on disc or not loaded, Generating new one");
                cH = new ConversationHandler();
                save();
            }
        }
        return cH;
    }

    public static void save()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(FILEPATH);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cH);
            objectOut.close();

        } catch(Exception ignored)
        {
            Outputs.create("Error when saving Conversations to file", "ConversationHandler").debug().WARNING().print();
        }
    }

    public static void test(String password){
        String originalContent = "foobar";
        /*SecretKey secretKey = null;
        char[] pwdArr = password.toCharArray();
        KeyStore ks = null;

        //Init keystore
        try (FileOutputStream fos = new FileOutputStream(FILEPATH + "KeyStoreTest")){
            ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, pwdArr);
            ks.store(fos, pwdArr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Init secretKey
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Store/Load secretKey
        KeyStore.SecretKeyEntry secret
                = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter pwenc
                = new KeyStore.PasswordProtection(pwdArr);
        try {
            ks.setEntry("KeyUserCookie", secret, pwenc);

            secretKey = (SecretKey) ks.getKey("KeyUserCookie", pwdArr);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //EncryptDecryptStuff
        StorageCrypto fileEncrypterDecrypter
                = null;
        try {
            fileEncrypterDecrypter = new StorageCrypto("passwort1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileEncrypterDecrypter.encrypt(originalContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String decryptedContent = null;
        try {
            decryptedContent = fileEncrypterDecrypter.decrypt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Text: "+ decryptedContent);


    }
}
