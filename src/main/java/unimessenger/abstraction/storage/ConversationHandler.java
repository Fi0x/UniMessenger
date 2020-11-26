package unimessenger.abstraction.storage;

import java.io.*;
import java.util.LinkedList;

public class ConversationHandler implements Serializable {

    private static final String filepath="Chats";

    private static ConversationHandler cH;

    private LinkedList conversations;

    public ConversationHandler(){
        conversations = new LinkedList<Conversation>();
    }

    public LinkedList<Conversation> getConversations(){
        return conversations;
    }

    public void newConversation(){

    }

    //TODO test
    public static ConversationHandler getInstance(){
        if(cH == null){
            try (FileInputStream fis = new FileInputStream("Chats");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                // read object from file
                cH = (ConversationHandler) ois.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return cH;
    }

    public static void writeToDisc(){
        //TODO write the conversations to the disc

        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cH);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Deprecated
    public void Test(){
        ConversationHandler ch = new ConversationHandler();
    }
}
