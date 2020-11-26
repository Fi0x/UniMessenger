package unimessenger.abstraction.interfaces.wire;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.encryption.WireCrypto.WireCryptoHandler;
import unimessenger.abstraction.interfaces.IMessages;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.communication.HTTP;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;
import java.util.UUID;

public class WireMessages implements IMessages
{
    private final WireMessageSender sender = new WireMessageSender();
    private final WireMessageReceiver receiver = new WireMessageReceiver();

    @Override
    public boolean sendMessage(String chatID, String text)
    {
        return sender.sendMessage(chatID, text);
    }

    @Override
    public boolean receiveNewMessages(String chatID)
    {
        return receiver.receiveNewMessages(chatID);
    }

    @Deprecated
    public static void PrintNotifications()
    {
        HTTP msgSender = new HTTP();
        System.out.println("List of all conversations in Wire:");
        String url = URL.WIRE + URL.WIRE_LAST_NOTIFICATION + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                "accept", "application/json",
                "accept", "text/html"};
        HttpResponse<String> response = msgSender.sendRequest(url, REQUEST.GET, "", headers);
        System.out.println("Response code: " + response.statusCode());
        System.out.println("Headers:" + response.headers());
        System.out.println("Body: " + response.body());
        //TODO MAKE WORK!!!
        try {
            JSONObject temp = (JSONObject) new JSONParser().parse(response.body());

            String pl = temp.get("payload").toString();

            System.out.println("PayLoad: " + pl);

            JSONArray payLArr = (JSONArray) new JSONParser().parse(pl);

            JSONObject payL = (JSONObject) new JSONParser().parse(payLArr.get(0).toString());

            System.out.println("From: "+ payL.get("from").toString());

            JSONObject data = (JSONObject) new JSONParser().parse(payL.get("data").toString());

            System.out.println("Text: "+ data.get("text"));
            System.out.println("Sender: "+ data.get("sender"));

            String ret = WireCryptoHandler.decrypt(UUID.fromString(payL.get("from").toString()), data.get("sender").toString(), data.get("text").toString());

            System.out.println("TextInGut: " + ret);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}