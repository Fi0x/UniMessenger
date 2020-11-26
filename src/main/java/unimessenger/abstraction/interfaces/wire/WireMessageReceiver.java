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
            System.out.print("Body: " + response.body());
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
            System.out.println("PayLoad: " + pl);
            JSONArray payLArr = (JSONArray) new JSONParser().parse(pl);
            JSONObject payL = (JSONObject) new JSONParser().parse(payLArr.get(0).toString());
            System.out.println("From: " + payL.get("from").toString());
            JSONObject data = (JSONObject) new JSONParser().parse(payL.get("data").toString());
            System.out.println("Text: " + data.get("text"));
            System.out.println("Sender: " + data.get("sender"));
            String ret = WireCryptoHandler.decrypt(UUID.fromString(payL.get("from").toString()), data.get("sender").toString(), data.get("text").toString());
            System.out.println("TextInGut: " + ret);

        } catch(ParseException ignored)
        {
        }
    }
}