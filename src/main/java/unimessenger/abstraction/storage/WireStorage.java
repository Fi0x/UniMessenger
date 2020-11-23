package unimessenger.abstraction.storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import unimessenger.abstraction.storage.MessengerStructure.WireConversation;
import unimessenger.abstraction.storage.MessengerStructure.WireProfile;
import unimessenger.userinteraction.Outputs;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class WireStorage
{
    public static String userID;
    public static String clientID = "7ec6cfc08fc9db51";
    public static boolean persistent = false;
    private static String bearerToken;
    public static String cookie;
    private static Timestamp bearerExpiringTime;
    public static final String storageFile = "../dataWire.json";

    public static WireProfile selfProfile = new WireProfile();
    public static ArrayList<WireConversation> conversations = new ArrayList<>();

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

            try
            {
                FileWriter fw = new FileWriter(storageFile);
                fw.write(obj.toJSONString());
                fw.close();
                Outputs.printDebug("Successfully wrote to Wire file");
            } catch(IOException ignored)
            {
                Outputs.printError("Couldn't write to Wire file");
            }
        }
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
        return bearerToken != null && bearerExpiringTime != null && bearerExpiringTime.getTime() > System.currentTimeMillis();
    }

    public static void clearUserData()
    {
        userID = null;
        bearerToken = null;
        cookie = null;
        bearerExpiringTime = null;
        clearFile();
    }

    public static void readDataFromFiles()
    {
        try
        {
            JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(storageFile));
            cookie = obj.get("accessCookie").toString();
            bearerToken = obj.get("bearerToken").toString();
            bearerExpiringTime = new Timestamp((long) obj.get("bearerTime"));
            clientID = obj.get("clientID").toString();
        } catch(Exception ignored)
        {
            Outputs.printError("Failed to load Wire file");
        }
    }

    public static void clearFile()
    {
        try
        {
            FileWriter fw = new FileWriter(storageFile);
            fw.write("{}");
            fw.close();
            Outputs.printDebug("Successfully cleared Wire file");
        } catch(IOException ignored)
        {
            Outputs.printError("Couldn't clear Wire file");
        }
    }
}