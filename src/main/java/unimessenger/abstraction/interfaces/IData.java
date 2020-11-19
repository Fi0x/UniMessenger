package unimessenger.abstraction.interfaces;

import java.util.ArrayList;

public interface IData
{
    ArrayList<String> getAllConversationIDs();
    String getNameFromID(String id);
}