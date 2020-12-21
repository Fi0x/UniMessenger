package unimessenger.abstraction.interfaces.storage;

import unimessenger.util.enums.CONVERSATIONTYPE;

import java.io.Serializable;
import java.util.ArrayList;

public interface IConversation extends Serializable
{
    void setConversationID(String id);
    String getConversationID();
    void setConversationName(String name);
    String getConversationName();
    void setConversationType(CONVERSATIONTYPE type);
    CONVERSATIONTYPE getConversationType();

    void addMember(IUser user);
    ArrayList<IUser> getMembers();
    boolean removeMember(int index);
    boolean removeMember(String id);

    void addMessage(IMessage msg);
    ArrayList<IMessage> getMessages();
    ArrayList<IMessage> getNewMessages();
}
