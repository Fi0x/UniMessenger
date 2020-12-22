package unimessenger.abstraction.interfaces.storage;

import java.util.ArrayList;

public interface IData
{
    ArrayList<String> getAllConversationIDs();
    String getConversationNameFromID(String id);
    ArrayList<String> getConversationMembersFromID(String id);
    ArrayList<IMessage> getNewMessagesFromConversation(String conversationID);
    ArrayList<IMessage> getAllMessagesFromConversation(String conversationID);
    ArrayList<IMessage> getLastXMessagesFromConversation(String conversationID, int messages);
}