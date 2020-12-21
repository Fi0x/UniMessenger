package unimessenger.abstraction.wire.storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import unimessenger.Main;
import unimessenger.abstraction.interfaces.storage.IConversation;
import unimessenger.abstraction.interfaces.storage.IProfile;
import unimessenger.abstraction.interfaces.storage.IStorage;
import unimessenger.abstraction.storage.ConversationHandler;
import unimessenger.userinteraction.tui.Out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Storage implements IStorage
{
    private static Storage instance;

    public String userID;
    public String clientID;
    public boolean persistent;
    private String bearerToken;
    public String cookie;
    private Timestamp bearerExpiringTime;
    public Timestamp lastNotification;
    private IProfile selfProfile;
    private ArrayList<IConversation> conversations;

    private CryptoStorage cryptoStorage;

    private String storageFile;
    private ConversationHandler convH;

    private Storage()
    {
    }
    public static Storage getInstance()
    {
        if(instance == null) instance = new Storage();
        return instance;
    }

    public void init()
    {
        persistent = false;
        selfProfile = new Profile();
        conversations = new ArrayList<>();

        Main.storageDir = System.getProperty("user.dir");
        if(Main.storageDir == null) Main.storageDir = "../DataStorage";
        else Main.storageDir = Main.storageDir.replace("\\", "/") + "/DataStorage";
        storageFile = Main.storageDir + "/access.json";

        if(new File(Main.storageDir).mkdirs()) Out.newBuilder("Storage folder successfully created").v().print();
        else Out.newBuilder("Storage folder not created").origin("WireStorage").d().WARNING().print();

        convH = ConversationHandler.getInstance();

        //Loading the messages and cons into conversations list
        conversations = convH.getConversations();
    }

    public void saveDataInFile()
    {
        if(cookie == null) clearFile();
        else
        {
            JSONObject obj = new JSONObject();
            obj.put("accessCookie", cookie);
            obj.put("bearerToken", bearerToken);
            obj.put("bearerTime", bearerExpiringTime.getTime());
            obj.put("clientID", clientID);
            if(lastNotification != null) obj.put("lastNotification", lastNotification.getTime());

            try
            {
                cryptoStorage.encrypt(obj.toJSONString());
                Out.newBuilder("Successfully wrote to Wire file").v().print();
            } catch(Exception ignored)
            {
                Out.newBuilder("Could not write to Wire file").origin("WireStorage").d().WARNING().print();
            }
        }

        convH.clearConvs();
        for(IConversation c : conversations)
        {
            convH.newConversation(c);
        }
        ConversationHandler.save();
        Out.newBuilder("Conversations stored on disk").v().print();
    }

    public void setBearerToken(String token, int ttl)
    {
        bearerToken = token;
        bearerExpiringTime = new Timestamp(System.currentTimeMillis() + (ttl * 900L));
    }

    public String getBearerToken()
    {
        return bearerToken;
    }

    public boolean isBearerTokenStillValid()
    {
        if(bearerToken == null) Out.newBuilder("Bearer token is null").d().WARNING().print();
        else if(bearerExpiringTime == null) Out.newBuilder("Bearer token has no expiring time").d().WARNING().print();
        else if(bearerExpiringTime.getTime() <= System.currentTimeMillis()) Out.newBuilder("Bearer token expired").print();
        else return true;
        return false;
    }

    public void clearUserData()
    {
        userID = null;
        bearerToken = null;
        cookie = null;
        bearerExpiringTime = null;
        lastNotification = null;
        clearFile();
        ConversationHandler.clearFile();
    }

    public void readDataFromFiles()
    {
        try
        {
            cryptoStorage = new CryptoStorage();

            JSONObject obj = (JSONObject) new JSONParser().parse(cryptoStorage.decrypt());
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

    public void clearFile()
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

    @Override
    public void setProfile(IProfile profile)
    {
        selfProfile = profile;
    }
    @Override
    public IProfile getProfile()
    {
        return selfProfile;
    }
    @Override
    public void addConversation(IConversation conversation)
    {
        conversations.add(conversation);
    }
    @Override
    public ArrayList<IConversation> getConversations()
    {
        return conversations;
    }
    public IConversation getConversationByID(String conversationID)
    {
        for(IConversation c : conversations)
        {
            if(c.getConversationID().equals(conversationID)) return c;
        }
        return null;
    }
    @Override
    public boolean removeConversation(int index)
    {
        if(index >= conversations.size()) return false;
        conversations.remove(index);
        return true;
    }
    @Override
    public boolean removeConversation(IConversation conversation)
    {
        if(conversations.contains(conversation)) conversations.remove(conversation);
        else return false;
        return true;
    }
}