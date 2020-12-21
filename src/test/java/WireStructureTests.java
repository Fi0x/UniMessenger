import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.storage.Conversation;
import unimessenger.abstraction.wire.storage.Message;
import unimessenger.util.enums.CONVERSATIONTYPE;

import java.sql.Timestamp;

public class WireStructureTests
{
    @Test
    void conversationType()
    {
        Conversation con = new Conversation();
        Assertions.assertEquals(CONVERSATIONTYPE.UNKNOWN, con.conversationType);

        con.setConversationType(CONVERSATIONTYPE.GROUP);
        Assertions.assertEquals(CONVERSATIONTYPE.GROUP, con.conversationType);

        con.setConversationType(CONVERSATIONTYPE.OTHER);
        Assertions.assertEquals(CONVERSATIONTYPE.OTHER, con.conversationType);

        con.setConversationType(CONVERSATIONTYPE.NORMAL);
        Assertions.assertEquals(CONVERSATIONTYPE.NORMAL, con.conversationType);

        con.setConversationType(CONVERSATIONTYPE.UNKNOWN);
        Assertions.assertEquals(CONVERSATIONTYPE.UNKNOWN, con.conversationType);
    }

    @Test
    void conversationMessage()
    {
        Conversation con = new Conversation();
        Message msg = new Message("Text", new Timestamp(0), "Sender");
        con.addMessage(msg);

        Assertions.assertEquals(msg, con.getNewMessages().get(0));
        Assertions.assertTrue(con.getNewMessages().isEmpty());
        Assertions.assertEquals(msg, con.getMessages().get(0));
    }
}
