package unimessenger.abstraction.interfaces.wire;

import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.communication.HTTP;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;

public class WireMessageReceiver
{
    public boolean receiveNewMessages(String chatID)
    {
        String specifications = "?client=" + WireStorage.clientID + "&since=" + 0;
        String url = URL.WIRE + URL.WIRE_NOTIFICATIONS + specifications + URL.WIRE_TOKEN + WireStorage.getBearerToken();
        String[] headers = new String[]{
                Headers.CONTENT_JSON[0], Headers.CONTENT_JSON[1],
                Headers.ACCEPT_JSON[0], Headers.ACCEPT_JSON[1]};

        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);
        return false;
    }
}