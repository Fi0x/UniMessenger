package unimessenger.abstraction.storage;

import java.util.LinkedList;

public class ConversationHandler {

    private static ConversationHandler cH;

    private LinkedList conversations;

    ConversationHandler(){
        conversations = new LinkedList<Conversation>();
    }

    public static ConversationHandler getInstance(){
        if(cH == null){
            //TODO Load from disc or generate if not loadable
        }
        return cH;
    }
}
