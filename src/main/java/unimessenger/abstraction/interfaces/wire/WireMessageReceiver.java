package unimessenger.abstraction.interfaces.wire;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.encryption.WireCrypto.WireCryptoHandler;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.communication.HTTP;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;
import java.util.Set;
import java.util.UUID;

public class WireMessageReceiver
{
    public boolean receiveNewMessages()
    {
        String specifications = "?since=2020-11-26T16:30:00.000Z";//TODO: Fix string
        String url = URL.WIRE + URL.WIRE_NOTIFICATIONS + specifications + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                Headers.CONTENT_JSON[0], Headers.CONTENT_JSON[1],
                Headers.ACCEPT_JSON[0], Headers.ACCEPT_JSON[1]};

        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        if(response == null)
            Outputs.create("No response received", this.getClass().getName()).debug().WARNING().print();
        else if(response.statusCode() == 200 || response.statusCode() == 404)
        {
            try
            {
                JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
                JSONArray arr = (JSONArray) obj.get("notifications");
                for(Object o : arr)
                {
                    JSONObject note = (JSONObject) o;
                    JSONArray payload = (JSONArray) note.get("payload");
                    for(Object pl : payload)//Contains: TYPE, client, data, from, time, conversation, connection, user, value, key
                    {
                        JSONObject load = (JSONObject) pl;
                        Set keys = load.keySet();
                        if(keys.contains("type"))
                        {
//                            if(keys.contains("client")) System.out.println("Client: " + load.get("type"));
//                            if(keys.contains("data")) System.out.println("Data: " + load.get("type"));
//                            if(keys.contains("from")) System.out.println("From: " + load.get("type"));
//                            if(keys.contains("time")) System.out.println("Time: " + load.get("type"));
//                            if(keys.contains("conversation")) System.out.println("Conversation: " + load.get("type"));
//                            if(keys.contains("connection")) System.out.println("Connection: " + load.get("type"));
//                            if(keys.contains("user")) System.out.println("User: " + load.get("type"));
//                            if(keys.contains("value")) System.out.println("Value: " + load.get("type"));
//                            if(keys.contains("key")) System.out.println("Key: " + load.get("type"));
                        }
                    }
                }
            } catch(ParseException ignored)
            {
                Outputs.create("Something went wrong when parsing the HTTP response", this.getClass().getName()).debug().WARNING();
            }
            return true;
        } else Outputs.create("Response code was " + response.statusCode(), this.getClass().getName()).debug().WARNING().print();
        return false;
    }

    @Deprecated
    public void PrintNotifications()
    {
        String url = URL.WIRE + URL.WIRE_LAST_NOTIFICATION + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                "accept", "application/json",
                "accept", "text/html"};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        try
        {
            JSONObject temp = (JSONObject) new JSONParser().parse(response.body());
            String pl = temp.get("payload").toString();
            JSONArray payLArr = (JSONArray) new JSONParser().parse(pl);
            JSONObject payL = (JSONObject) new JSONParser().parse(payLArr.get(0).toString());
            JSONObject data = (JSONObject) new JSONParser().parse(payL.get("data").toString());
            String ret = WireCryptoHandler.decrypt(UUID.fromString(payL.get("from").toString()), data.get("sender").toString(), data.get("text").toString());
            System.out.println("TextInGut: " + ret);

        } catch(ParseException ignored)
        {
        }
    }
}