package unimessenger.abstraction.wire;

import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.interfaces.IMessages;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.apicommunication.HTTP;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;

public class WireMessages implements IMessages
{
    @Override
    public boolean sendMessage(String chatID, String text)//TODO: Make this method work
    {
        String url = URL.WIRE + URL.WIRE_CONVERSATIONS + "/" + chatID + URL.WIRE_OTR_MESSAGES;
        String[] headers = new String[]{
                Headers.CONTENT_JSON[0], Headers.CONTENT_JSON[1],
                Headers.ACCEPT_JSON[0], Headers.ACCEPT_JSON[1]};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.POST, "{}", headers);

        System.out.println("Response code: " + response.statusCode());
        System.out.println("Headers: " + response.headers());
        System.out.println("Body: " + response.body());
        return false;
    }

    @Deprecated
    public static void PrintNotifications()
    {
        HTTP msgSender = new HTTP();
        System.out.println("List of all conversations in Wire:");
        //TODO: Show a list of all Wire-conversations
        String url = URL.WIRE + URL.WIRE_LAST_NOTIFICATION + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                "accept", "application/json",
                "accept", "text/html"};
        HttpResponse<String> response = msgSender.sendRequest(url, REQUEST.GET, "", headers);
        System.out.println("Response code: " + response.statusCode());
        System.out.println("Headers:" + response.headers());
        System.out.println("Body: " + response.body());
    }
}