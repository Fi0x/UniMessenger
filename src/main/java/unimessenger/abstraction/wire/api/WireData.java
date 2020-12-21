package unimessenger.abstraction.wire.api;

import unimessenger.abstraction.interfaces.storage.IConversation;
import unimessenger.abstraction.interfaces.storage.IData;
import unimessenger.abstraction.interfaces.storage.IMessage;
import unimessenger.abstraction.interfaces.storage.IUser;
import unimessenger.abstraction.wire.storage.Storage;
import unimessenger.userinteraction.tui.Out;

import java.util.ArrayList;

public class WireData implements IData
{
    @Override
    public ArrayList<String> getAllConversationIDs()
    {
        ArrayList<String> ids = new ArrayList<>();

        for(IConversation con : Storage.conversations)
        {
            ids.add(con.getConversationID());
        }

        Out.newBuilder("Returning" + ids.size() + " conversation IDs").vv().print();
        return ids;
    }

    @Override
    public String getConversationNameFromID(String id)
    {
        for(IConversation con : Storage.conversations)
        {
            if(con.getConversationID().equals(id))
            {
                return con.getConversationName();
            }
        }
        return null;
    }

    @Override
    public ArrayList<String> getConversationMembersFromID(String id)
    {
        IConversation conversation = null;
        for(IConversation con : Storage.conversations)
        {
            if(con.getConversationID().equals(id)) conversation = con;
        }

        ArrayList<String> members = new ArrayList<>();
        if(conversation != null)
        {
            for(IUser mem : conversation.getMembers())
            {
                members.add(mem.getUserID());
            }
        }

        return members;
    }
    @Override
    public ArrayList<IMessage> getNewMessagesFromConversation(String conversationID)
    {
        IConversation con = Storage.getInstance().getConversationByID(conversationID);
        if(con == null) return null;
        return con.getNewMessages();
    }
    @Override
    public ArrayList<IMessage> getAllMessagesFromConversation(String conversationID)
    {
        IConversation con = Storage.getInstance().getConversationByID(conversationID);
        if(con == null || con.getMessages().isEmpty()) return null;
        return con.getMessages();
    }
    @Override
    public ArrayList<IMessage> getLastXMessagesFromConversation(String conversationID, int messages)
    {
        IConversation con = Storage.getInstance().getConversationByID(conversationID);
        if(con == null || con.getMessages().isEmpty()) return null;
        ArrayList<IMessage> msgs = con.getMessages();
        while(msgs.size() > messages)
        {
            msgs.remove(0);
        }
        return msgs;
    }
}