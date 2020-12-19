package unimessenger.abstraction.storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import unimessenger.abstraction.wire.structures.WireConversation;
import unimessenger.abstraction.wire.structures.WireProfile;
import unimessenger.userinteraction.tui.Out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class WireStorage
{
    public static String userID;
    public static String clientID;
    public static boolean persistent;
    private static String bearerToken;
    public static String cookie;
    private static Timestamp bearerExpiringTime;
    public static Timestamp lastNotification;
    public static WireProfile selfProfile;
    public static ArrayList<WireConversation> conversations;

    private static StorageCrypto storageCrypto;

    public static String storageDirectory;
    private static String storageFile;
    public static ConversationHandler convH;

    public static void init()
    {
        userID = null;
        clientID = null;
        persistent = false;
        bearerToken = null;
        cookie = null;
        bearerExpiringTime = null;
        lastNotification = null;
        selfProfile = new WireProfile();
        conversations = new ArrayList<>();

        storageDirectory = System.getProperty("user.dir");
        if(storageDirectory == null) storageDirectory = "../DataStorage";
        else storageDirectory = storageDirectory.replace("\\", "/") + "/DataStorage";
        storageFile = storageDirectory + "/access.json";

        if(new File(storageDirectory).mkdirs()) Out.newBuilder("Storage folder successfully created").v().print();
        else Out.newBuilder("Storage folder not created").origin("WireStorage").d().WARNING().print();

        convH = ConversationHandler.getInstance();

        //Loading the messages and cons into conversations list
        conversations = convH.getConversations();
    }

    public static void saveDataInFile(String accessCookie)
    {
        if(accessCookie == null) clearFile();
        else
        {
            cookie = accessCookie;
            JSONObject obj = new JSONObject();
            obj.put("accessCookie", accessCookie);
            obj.put("bearerToken", bearerToken);
            obj.put("bearerTime", bearerExpiringTime.getTime());
            obj.put("clientID", clientID);
            if(lastNotification != null) obj.put("lastNotification", lastNotification.getTime());

            try
            {
                storageCrypto.encrypt(obj.toJSONString());
                Out.newBuilder("Successfully wrote to Wire file").v().print();
            } catch(Exception ignored)
            {
                Out.newBuilder("Could not write to Wire file").origin("WireStorage").d().WARNING().print();
            }
        }

        convH.clearConvs();
        for(WireConversation c : conversations)
        {
            convH.newConversation(c);
        }
        ConversationHandler.save();
        Out.newBuilder("Conversations stored on disk").v().print();
    }

    public static void saveDataInFile()
    {
        saveDataInFile(cookie);
    }

    public static void setBearerToken(String token, int ttl)
    {
        bearerToken = token;
        bearerExpiringTime = new Timestamp(System.currentTimeMillis() + (ttl * 900));
    }

    public static String getBearerToken()
    {
        return bearerToken;
    }

    public static boolean isBearerTokenStillValid()
    {
        if(bearerToken == null) Out.newBuilder("Bearer token is null").v().print();
        else if(bearerExpiringTime == null) Out.newBuilder("Bearer token has no expiring time").v().print();
        else if(bearerExpiringTime.getTime() <= System.currentTimeMillis()) Out.newBuilder("Bearer token expired").v().print();
        else return true;
        return false;
    }

    public static void clearUserData()
    {
        userID = null;
        bearerToken = null;
        cookie = null;
        bearerExpiringTime = null;
        lastNotification = null;
        clearFile();
        ConversationHandler.clearFile();
    }

    public static void readDataFromFiles()
    {
        try
        {
            storageCrypto = new StorageCrypto();

            JSONObject obj = (JSONObject) new JSONParser().parse(storageCrypto.decrypt());
            cookie = obj.get("accessCookie").toString();
            bearerToken = obj.get("bearerToken").toString();
            bearerExpiringTime = new Timestamp((long) obj.get("bearerTime"));
            clientID = obj.get("clientID").toString();
            lastNotification = new Timestamp((long) obj.get("lastNotification"));


            //Loading the messages and cons into conversations list
            conversations = convH.getConversations();

        } catch(Exception ignored)
        {
            Out.newBuilder("Failed to load Wire file").origin("WireStorage").d().WARNING().print();
        }
    }

    public static void clearFile()
    {
        try
        {
            FileWriter fw = new FileWriter(storageFile);
            fw.write("{}");
            fw.close();
            Out.newBuilder("Successfully cleared Wire file").v().print();
            if(new File(storageFile).delete()) Out.newBuilder("Successfully deleted Wire file").v().print();
        } catch(IOException ignored)
        {
            Out.newBuilder("Could not clear Wire file").origin("Wire Storage").d().WARNING().print();
        }
    }

    public static WireConversation getConversationByID(String conversationID)
    {
        for(WireConversation c : conversations)
        {
            if(c.id.equals(conversationID)) return c;
        }
        return null;
    }
}