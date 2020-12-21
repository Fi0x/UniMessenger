package unimessenger.abstraction.wire.storage;

import unimessenger.abstraction.interfaces.storage.IConversation;
import unimessenger.abstraction.interfaces.storage.IMessage;
import unimessenger.abstraction.interfaces.storage.IUser;
import unimessenger.util.enums.CONVERSATIONTYPE;

import java.util.ArrayList;

public class Conversation implements IConversation
{
    public ArrayList<String> access = new ArrayList<>();
    public ArrayList<IUser> members = new ArrayList<>();
    private String conversationName;
    private String id;
    public CONVERSATIONTYPE conversationType = CONVERSATIONTYPE.UNKNOWN;
    private final ArrayList<IMessage> newMessages = new ArrayList<>();
    private final ArrayList<IMessage> messages = new ArrayList<>();

    @Override
    public void setConversationID(String id)
    {
        this.id = id;
    }
    @Override
    public String getConversationID()
    {
        return id;
    }
    @Override
    public void setConversationName(String name)
    {
        this.conversationName = name;
    }
    @Override
    public String getConversationName()
    {
        return conversationName;
    }
    @Override
    public void setConversationType(CONVERSATIONTYPE type)
    {
        this.conversationType = type;
    }
    @Override
    public CONVERSATIONTYPE getConversationType()
    {
        return conversationType;
    }

    @Override
    public void addMember(IUser user)
    {
        members.add(user);
    }
    @Override
    public ArrayList<IUser> getMembers()
    {
        return members;
    }
    @Override
    public boolean removeMember(int index)
    {
        if(members.size() <= index) return false;
        members.remove(index);
        return true;
    }
    @Override
    public boolean removeMember(String id)
    {
        for(IUser user : members)
        {
            if(user.getUserID().equals(id))
            {
                members.remove(user);
                return true;
            }
        }
        return false;
    }
    @Override
    public void addMessage(IMessage msg)
    {
        newMessages.add(msg);
    }
    @Override
    public ArrayList<IMessage> getMessages()
    {
        if(newMessages.size() > 0) getNewMessages();

        messages.removeIf(message -> !message.isValid());

        return messages;
    }
    @Override
    public ArrayList<IMessage> getNewMessages()
    {
        ArrayList<IMessage> msgs = new ArrayList<>();

        while(!newMessages.isEmpty())
        {
            messages.add(newMessages.get(0));
            msgs.add(newMessages.get(0));
            newMessages.remove(0);
        }
        return msgs;
    }
}
