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
                    for(Object pl : payload)
                    {
                        JSONObject load = (JSONObject) pl;
                        if(load.get("type").equals("conversation.otr-message-add"))
                        {

                            if(!receiveMessageText(load))
                                Outputs.create("Error receiving text of a notification").verbose().WARNING().print();
                        }
                    }
                }
            } catch(ParseException ignored)
            {
                Outputs.create("Something went wrong when parsing the HTTP response", this.getClass().getName()).debug().WARNING();
            }
            return true;
        } else
            Outputs.create("Response code was " + response.statusCode(), this.getClass().getName()).debug().WARNING().print();
        return false;
    }

    private boolean receiveMessageText(JSONObject payload)
    {
        String conversation;
        String senderUser;
        String time;
        String decryptedMsg;

        if(payload.containsKey("conversation"))
        {
            conversation = payload.get("conversation").toString();
        } else
        {
            Outputs.create("Conversation notification has no 'conversation' key", this.getClass().getName()).debug().WARNING().print();
            return false;
        }

        if(!payload.containsKey("data"))
        {
            Outputs.create("Conversation notification has no 'data' key", this.getClass().getName()).debug().WARNING().print();
            return false;
        }

        if(payload.containsKey("from"))
        {
            senderUser = payload.get("from").toString();
        } else Outputs.create("Conversation notification has no 'from' key").verbose().WARNING().print();

        if(payload.containsKey("time"))
        {
            time = payload.get("time").toString();
        } else Outputs.create("Conversation notification has no 'time' key").verbose().WARNING().print();

        JSONObject data = (JSONObject) payload.get("data");

        decryptedMsg = WireCryptoHandler.decrypt(UUID.fromString(payload.get("from").toString()), data.get("sender").toString(), data.get("text").toString());
        System.out.println("Decrypted Message: " + decryptedMsg);

        return true;
    }

    @Deprecated
    public void PrintNotifications()
    {
        String url = URL.WIRE + URL.WIRE_LAST_NOTIFICATION + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                "accept", "application/json",
                "accept", "text/html"};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        System.out.println(response.body());
        try
        {
            JSONObject temp = (JSONObject) new JSONParser().parse(response.body());
            String pl = temp.get("payload").toString();
            JSONArray payLArr = (JSONArray) new JSONParser().parse(pl);
            JSONObject payL = (JSONObject) new JSONParser().parse(payLArr.get(0).toString());

            if(!payL.get("type").toString().equals("conversation.otr-message-add")) return;

            JSONObject data = (JSONObject) new JSONParser().parse(payL.get("data").toString());
            String ret = WireCryptoHandler.decrypt(UUID.fromString(payL.get("from").toString()), data.get("sender").toString(), data.get("text").toString());
            System.out.println("TextInGut: " + ret);

        } catch(ParseException ignored)
        {
        }
    }
}