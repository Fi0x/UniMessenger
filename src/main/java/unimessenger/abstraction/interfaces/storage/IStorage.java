package unimessenger.abstraction.interfaces.storage;

import java.util.ArrayList;

public interface IStorage
{
    void setProfile(IProfile profile);
    IProfile getProfile();

    void addConversation(IConversation conversation);
    ArrayList<IConversation> getConversations();
    IConversation getConversationByID(String conversationID);
    boolean removeConversation(int index);
    boolean removeConversation(IConversation conversation);
}
