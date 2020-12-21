package unimessenger.abstraction.wire.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.interfaces.api.IConversations;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.abstraction.wire.storage.User;
import unimessenger.abstraction.wire.structures.WireConversation;
import unimessenger.communication.HTTP;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.enums.CONVERSATIONTYPE;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;

public class WireConversations implements IConversations
{
    @Override
    public boolean requestAllConversations()
    {
        String url = URL.WIRE + URL.WIRE_CONVERSATIONS + URL.wireBearerToken();
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        if(response == null)
        {
            Out.newBuilder("Could not get a HTTP response").origin(this.getClass().getName()).d().WARNING().print();
            return false;
        } else if(response.statusCode() == 200)
        {
            //TODO: If "has more" key in body is true, ask for more conversations
            //TODO: Sort chats after most recent activity
            try
            {
                JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
                JSONArray conArr = (JSONArray) obj.get("conversations");
                for(Object o : conArr)
                {
                    WireConversation newConversation = getConversation((JSONObject) new JSONParser().parse(o.toString()));
                    if(newConversation.getConversationName() != null)
                    {
                        boolean exists = false;
                        for(WireConversation con : WireStorage.conversations)
                        {
                            if(con.id.equals(newConversation.id))
                            {
                                exists = true;
                                break;
                            }
                        }
                        if(!exists) WireStorage.conversations.add(newConversation);
                    }
                }
                Out.newBuilder("Successfully reloaded all conversations").print();
                return true;
            } catch(ParseException ignored)
            {
            }
            Out.newBuilder("Failed to reload all conversations").origin(this.getClass().getName()).d().WARNING().print();
            return false;
        } else
        {
            Out.newBuilder("Response code from reloading conversations is " + response.statusCode()).origin(this.getClass().getName()).d().WARNING().print();
            return false;
        }
    }

    private static WireConversation getConversation(JSONObject conObj) throws ParseException
    {
        WireConversation con = new WireConversation();

        JSONArray access = (JSONArray) conObj.get("access");
        for(Object a : access)
        {
            con.access.add(a.toString());
        }

        con.creatorID = conObj.get("creator").toString();
        con.accessRole = conObj.get("access_role").toString();
        con.setConversationType(Integer.parseInt(conObj.get("type").toString()));

        JSONObject members = (JSONObject) new JSONParser().parse(conObj.get("members").toString());
        con.members.add(getPerson((JSONObject) new JSONParser().parse(members.get("self").toString())));
        JSONArray memArr = (JSONArray) members.get("others");
        for(Object o : memArr)
        {
            con.members.add(getPerson((JSONObject) new JSONParser().parse(o.toString())));
        }

        if(con.conversationType == CONVERSATIONTYPE.NORMAL)
        {
            if(con.members.size() > 1 && con.members.get(1) != null)
            {
                String partnerID = con.members.get(1).getUserID();
                String conName = getNameFromUserID(partnerID);
                if(conName != null) con.setConversationName(conName);
            }
        } else if(conObj.get("name") != null) con.setConversationName(conObj.get("name").toString());
        if(conObj.get("team") != null) con.team = conObj.get("team").toString();
        con.id = conObj.get("id").toString();
        if(conObj.get("receipt_mode") != null) con.receipt_mode = conObj.get("receipt_mode").toString();
        con.last_event_time = conObj.get("last_event_time").toString();
        if(conObj.get("message_timer") != null) con.message_timer = conObj.get("message_timer").toString();
        con.last_event = conObj.get("last_event").toString();

        return con;
    }
    private static User getPerson(JSONObject personObj)
    {
        User person = new User();

        person.setUserID(personObj.get("id").toString());
        person.setUserName(getUsernameFromID(person.getUserID()));

        return person;
    }
    private static String getUsernameFromID(String userID)
    {
        String url = URL.WIRE + URL.WIRE_USERS + URL.wireBearerToken() + "&ids=" + userID;
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.GET, "", headers);

        if(response == null) Out.newBuilder("Could not get a username for userID '" + userID + "'").origin("WireConversations").d().WARNING().print();
        else if(response.statusCode() == 200)
        {
            JSONArray arr = null;
            try
            {
                arr = (JSONArray) new JSONParser().parse(response.body());
            } catch(ParseException ignored)
            {
            }
            if(arr != null && arr.size() > 0)
            {
                JSONObject user = (JSONObject) arr.get(0);
                return user.get("name").toString();
            } else Out.newBuilder("No user returned").origin("WireConversations").d().WARNING().print();
        } else Out.newBuilder("Response code of getting a username was " + response.statusCode()).d().WARNING().print();

        return userID;
    }

    @Deprecated
    public static String getNameFromUserID(String userID)
    {
        return getUsernameFromID(userID);
    }
}