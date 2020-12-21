package unimessenger.abstraction.wire.api;

import com.google.protobuf.InvalidProtocolBufferException;
import com.waz.model.Messages;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.abstraction.wire.crypto.WireCryptoHandler;
import unimessenger.abstraction.wire.structures.WireConversation;
import unimessenger.communication.HTTP;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.UUID;

public class WireMessageReceiver
{
    public boolean receiveNewMessages()
    {
        String client = "?client=" + WireStorage.clientID;
        String since = "&since=2020-11-27T10:47:39.941Z";//TODO: Fix string
        String token = URL.wireBearerToken();
        String url = URL.WIRE + URL.WIRE_NOTIFICATIONS + client + since + token;
        String[] headers = new String[]{
                Headers.CONTENT, Headers.JSON,
                Headers.ACCEPT, Headers.JSON};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        if(response == null)
            Out.newBuilder("No response received").origin(this.getClass().getName()).d().WARNING().print();
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
                            if(!handleMessage(load)) Out.newBuilder("Error receiving text of a notification").d().WARNING().print();
                        }
                    }
                }
                if(obj.containsKey("has_more") && Boolean.getBoolean(obj.get("has_more").toString()))
                {
                    if(!receiveNewMessages()) return false;//TODO: Find out if it works
                } else WireStorage.saveDataInFile();
            } catch(ParseException ignored)
            {
                Out.newBuilder("Something went wrong when parsing the HTTP response of new messages").origin(this.getClass().getName()).d().WARNING();
            }
            return true;
        } else Out.newBuilder("Response code from message receiving was " + response.statusCode()).origin(this.getClass().getName()).d().WARNING().print();
        return false;
    }

    private boolean handleMessage(JSONObject payload)
    {
        String conversationID;
        String senderUser = null;
        Timestamp time = null;
        Messages.GenericMessage message;

        if(payload.containsKey("conversation")) conversationID = payload.get("conversation").toString();
        else
        {
            Out.newBuilder("Conversation notification has no 'conversation' key").origin(this.getClass().getName()).d().WARNING().print();
            return false;
        }

        if(!payload.containsKey("data"))
        {
            Out.newBuilder("Conversation notification has no 'data' key").origin(this.getClass().getName()).d().WARNING().print();
            return false;
        }

        if(payload.containsKey("from")) senderUser = payload.get("from").toString();
        else Out.newBuilder("Conversation notification has no 'from' key").v().WARNING().print();

        if(payload.containsKey("time"))
        {
            time = Timestamp.valueOf(payload.get("time").toString().replace("T", " ").replace("Z", ""));
            if(WireStorage.lastNotification != null && time.getTime() <= WireStorage.lastNotification.getTime())
            {
                Out.newBuilder("Notification filtered because of timestamp").v().print();
                return false;
            } else WireStorage.lastNotification = time;
        } else Out.newBuilder("Conversation notification has no 'time' key").v().WARNING().print();

        JSONObject data = (JSONObject) payload.get("data");

        if(!data.get("recipient").toString().equals(WireStorage.clientID))
        {
            Out.newBuilder("Message is not for this client").v().print();
            return false;
        }

        byte[] decrypted = WireCryptoHandler.decrypt(UUID.fromString(payload.get("from").toString()), data.get("sender").toString(), data.get("text").toString());
        try
        {
            message = Messages.GenericMessage.parseFrom(decrypted);
        } catch(InvalidProtocolBufferException e)
        {
            Out.newBuilder("Unable to parse to a generic message").origin(this.getClass().getName()).d().WARNING().print();
            return false;
        }

        WireConversation conversation = WireStorage.getConversationByID(conversationID);
        if(conversation == null)
        {
            Out.newBuilder("ConversationID not found").origin(this.getClass().getName()).d().WARNING().print();
            return false;
        }

        senderUser = WireConversations.getNameFromUserID(senderUser);
        return WireMessageSorter.handleReceivedMessage(message, conversation, time, senderUser);
    }
}