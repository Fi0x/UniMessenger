package unimessenger.abstraction.storage;

import unimessenger.userinteraction.Outputs;

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

    public void newConversation(Conversation c){
        conversations.add(c);
    }

    public Conversation getConvByID(){


        return null;
    }

    //TODO test
    public static ConversationHandler getInstance(){
        if(cH == null){
            try (FileInputStream fis = new FileInputStream("DataStorage/Chats");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                // read object from file
                cH = (ConversationHandler) ois.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                Outputs.create("ConnectionHandler not on disc or not loaded, Generating new one");
                cH = new ConversationHandler();
            }
        }
        return cH;
    }

    public static void save(){
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
    public static void Test(){
        ConversationHandler ch = ConversationHandler.getInstance();
        String PartnerReadable = "Partner";
        ch.newConversation(new Conversation("1234", "sadf", PartnerReadable));
        ConversationHandler.save();
        cH = null;
        ch = null;
        ch = ConversationHandler.getInstance();
        assert ch.getConversations().get(0).getPartnerReadable()==PartnerReadable;
        System.out.println("Here Assert complete");
    }
}
