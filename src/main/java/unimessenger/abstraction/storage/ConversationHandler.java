package unimessenger.abstraction.storage;

import unimessenger.Main;
import unimessenger.abstraction.interfaces.storage.IConversation;
import unimessenger.userinteraction.tui.Out;

import java.io.*;
import java.util.ArrayList;

public class ConversationHandler implements Serializable
{
    private static ConversationHandler cH;
    private static final String FILEPATH = Main.storageDir + "/Chats";
    private ArrayList<IConversation> conversations;

    private ConversationHandler()
    {
        conversations = new ArrayList<>();
    }

    public static void clearFile()
    {
        new File(FILEPATH).delete();

    }

    public ArrayList<IConversation> getConversations()
    {
        return conversations;
    }

    public void newConversation(IConversation c)
    {
        conversations.add(c);
    }

    public void clearConvs()
    {
        conversations = new ArrayList<>();
    }

    public static ConversationHandler getInstance()
    {
        if(cH == null)
        {
            try(FileInputStream fis = new FileInputStream(FILEPATH); ObjectInputStream ois = new ObjectInputStream(fis))
            {
                cH = (ConversationHandler) ois.readObject();
            } catch(IOException | ClassNotFoundException ex)
            {
                Out.newBuilder("ConnectionHandler not on disc or not loaded, Generating new one").v();
                cH = new ConversationHandler();
                save();
            }
        }
        return cH;
    }

    public static void save()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(FILEPATH);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cH);
            objectOut.close();

        } catch(Exception ignored)
        {
            Out.newBuilder("Error when saving Conversations to file").origin("ConversationHandler").d().WARNING().print();
        }
    }
}
