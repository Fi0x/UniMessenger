package unimessenger.abstraction.wire;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.interfaces.IUtil;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.apicommunication.HTTP;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;
import java.util.ArrayList;

public class WireUtil implements IUtil
{
    @Override
    public boolean refreshSession()
    {
        String url = URL.WIRE + URL.WIRE_ACCESS + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                "cookie", WireStorage.cookie,
                Headers.CONTENT_JSON[0], Headers.CONTENT_JSON[1],
                Headers.ACCEPT_JSON[0], Headers.ACCEPT_JSON[1]};

        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.POST, "", headers);

        if(response == null)
        {
            Outputs.printError("Couldn't get a HTTP response");
        } else if(response.statusCode() == 200)
        {
            JSONObject obj;
            try
            {
                assert false;
                obj = (JSONObject) new JSONParser().parse(response.body());
                WireStorage.setBearerToken(obj.get("access_token").toString(), Integer.parseInt(obj.get("expires_in").toString()));
                WireStorage.userID = obj.get("user").toString();
                Outputs.printDebug("Successfully refreshed token");
                return true;
            } catch(ParseException ignored)
            {
                Outputs.printError("Failed refreshing token");
            }
        } else
        {
            Outputs.printDebug("Response code is " + response.statusCode() + ". Deleting Wire access cookie...");
            WireStorage.cookie = null;
            WireStorage.clearFile();
        }
        return false;
    }

    @Override
    public boolean loadProfile()
    {
        String url = URL.WIRE + URL.WIRE_SELF + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                Headers.CONTENT_JSON[0], Headers.CONTENT_JSON[1],
                Headers.ACCEPT_JSON[0], Headers.ACCEPT_JSON[1]};

        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        if(response == null) Outputs.printError("No response received");
        else if(response.statusCode() == 200)
        {
            try
            {
                JSONObject obj = (JSONObject) new JSONParser().parse(response.body());

                if(obj.containsKey("email")) WireStorage.selfProfile.email = obj.get("email").toString();
                if(obj.containsKey("phone")) WireStorage.selfProfile.phone = obj.get("phone").toString();
                if(obj.containsKey("handle")) WireStorage.selfProfile.handle = obj.get("handle").toString();
                WireStorage.selfProfile.locale = obj.get("locale").toString();
                if(obj.containsKey("managed_by")) WireStorage.selfProfile.managed_by = obj.get("managed_by").toString();
                if(obj.containsKey("accent_id")) WireStorage.selfProfile.accent_id = Integer.parseInt(obj.get("accent_id").toString());
                WireStorage.selfProfile.userName = obj.get("name").toString();
                WireStorage.selfProfile.id = obj.get("id").toString();
                if(obj.containsKey("deleted")) WireStorage.selfProfile.deleted = Boolean.getBoolean(obj.get("deleted").toString());
                WireStorage.selfProfile.userAssets = getUserAssets(obj.get("assets").toString());
                return true;
            } catch(ParseException ignored)
            {
                Outputs.printError("Json parsing error");
            }
        } else Outputs.printError("Http response was " + response.statusCode());

        WireStorage.clientID = getClientID();
        return false;
    }

    private static ArrayList<String> getUserAssets(String str)
    {
        //TODO: Implement
        return null;
    }
    private static String getClientID()
    {
        ArrayList<String> clientIDs = getAllClientIDs();
        for(String id : clientIDs)
        {
            if(compareCookie(id)) return id;
        }

        //TODO: Register client
        String clientID = null;
        return clientID;
    }
    private static ArrayList<String> getAllClientIDs()
    {
        ArrayList<String> ids = new ArrayList<>();
        //TODO: Get all clients and store id of each in list

        return ids;
    }
    private static boolean compareCookie(String clientID)
    {
        //TODO: get the /clients/{client} information
        //TODO: Compare cookies with stored cookie
        return false;
    }
}
