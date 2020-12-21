package unimessenger.abstraction.wire.api;

import com.waz.model.Messages;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.wire.crypto.Prekey;
import unimessenger.abstraction.wire.crypto.WireCryptoHandler;
import unimessenger.abstraction.wire.storage.Storage;
import unimessenger.communication.HTTP;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.enums.REQUEST;

import java.lang.constant.Constable;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WireMessageSender
{
    public boolean sendMessage(String chatID, Messages.GenericMessage msg)
    {
        if(msg == null) return false;

        String url = URL.WIRE + URL.WIRE_CONVERSATIONS + "/" + chatID + URL.WIRE_OTR_MESSAGES + URL.wireBearerToken();
        String[] headers = new String[]{
                Headers.CONTENT, Headers.JSON,
                Headers.ACCEPT, Headers.JSON};
        String body = buildBody(chatID, msg);
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.POST, body, headers);

        if(response == null) Out.newBuilder("No response for sent message received").origin(this.getClass().getName()).d().WARNING().print();
        else if(response.statusCode() == 201)
        {
            Out.newBuilder("Message sent correctly").v().print();

            return true;
        } else Out.newBuilder("Response code was " + response.statusCode()).origin(this.getClass().getName()).d().WARNING().print();
        return false;
    }

    private String buildBody(String chatID, Messages.GenericMessage msg)
    {
        JSONObject obj = new JSONObject();

        obj.put("sender", Storage.clientID);
        obj.put("transient", true);

        ArrayList<String> members = new WireData().getConversationMembersFromID(chatID);

        JSONObject recipients = new JSONObject();

        for(String id : members)
        {
            Map<String, Constable> clientMap = new LinkedHashMap<>(members.size());
            ArrayList<String> userClients = getClientIDsFromUser(id);
            if(userClients != null)
            {
                while(!userClients.isEmpty())
                {
                    if(!(id.equals(Storage.getInstance().userID) && userClients.get(0).equals(Storage.clientID)))
                    {
                        Prekey pk = getPreKeyForClient(id, userClients.get(0));
                        clientMap.put(userClients.get(0), WireCryptoHandler.encrypt(id, userClients.get(0), pk, msg.toByteArray()));
                    }
                    userClients.remove(0);
                }
            }
            recipients.put(id, clientMap);
        }

        obj.put("recipients", recipients);

        return obj.toJSONString();
    }

    private ArrayList<String> getClientIDsFromUser(String userID)
    {
        String url = URL.WIRE + URL.WIRE_USERS + "/" + userID + URL.WIRE_CLIENTS + URL.wireBearerToken();
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        if(response == null) Out.newBuilder("No client response").origin("WireMessages").d().WARNING().print();
        else if(response.statusCode() == 200)
        {
            try
            {
                JSONArray arr = (JSONArray) new JSONParser().parse(response.body());
                ArrayList<String> ids = new ArrayList<>();
                while(!arr.isEmpty())
                {
                    JSONObject obj = (JSONObject) arr.get(0);
                    ids.add(obj.get("id").toString());
                    arr.remove(0);
                }
                return ids;
            } catch(ParseException ignored)
            {
            }
        } else Out.newBuilder("Response code is " + response.statusCode()).origin("WireMessages").d().WARNING().print();

        return null;
    }

    private Prekey getPreKeyForClient(String userID, String clientID)
    {
        String url = URL.WIRE + URL.WIRE_USERS + "/" + userID + URL.WIRE_PREKEY + "/" + clientID + URL.wireBearerToken();
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        if(response == null) Out.newBuilder("Could not get a prekey for a client").origin("WireMessages").d().WARNING().print();
        else if(response.statusCode() == 200)
        {
            try
            {
                JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
                JSONObject key = (JSONObject) new JSONParser().parse(obj.get("prekey").toString());
                int prekeyID = Integer.parseInt(key.get("id").toString());
                String prekeyKey = key.get("key").toString();
                return new Prekey(prekeyID, prekeyKey);
            } catch(ParseException ignored)
            {
                Out.newBuilder("Could not get a prekey").origin("WireMessages").d().WARNING().print();
            }
        } else Out.newBuilder("Response code was " + response.statusCode()).origin("WireMessages").d().WARNING().print();
        return null;
    }
}