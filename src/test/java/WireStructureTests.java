import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.storage.Message;
import unimessenger.abstraction.wire.structures.WireConversation;
import unimessenger.abstraction.wire.structures.WirePerson;
import unimessenger.util.enums.CONVERSATIONTYPE;

import java.sql.Timestamp;

public class WireStructureTests
{
    @Test
    void person()
    {
        WirePerson p = new WirePerson();

        Assertions.assertNull(p.hidden_ref);
        Assertions.assertEquals(-1, p.status);
        Assertions.assertNull(p.service);
        Assertions.assertNull(p.otr_muted_ref);
        Assertions.assertNull(p.conversation_role);
        Assertions.assertNull(p.status_time);
        Assertions.assertFalse(p.hidden);
        Assertions.assertNull(p.status_ref);
        Assertions.assertNull(p.id);
        Assertions.assertFalse(p.otr_archived);
        Assertions.assertNull(p.otr_muted_status);
        Assertions.assertFalse(p.otr_muted);
        Assertions.assertNull(p.otr_archived_ref);
    }

    @Test
    void conversation()
    {
        WireConversation con = new WireConversation();

        Assertions.assertTrue(con.access.isEmpty());
        Assertions.assertNull(con.creatorID);
        Assertions.assertNull(con.accessRole);
        Assertions.assertTrue(con.members.isEmpty());
        Assertions.assertNull(con.team);
        Assertions.assertNull(con.id);
        Assertions.assertEquals(CONVERSATIONTYPE.UNKNOWN, con.conversationType);
        Assertions.assertNull(con.receipt_mode);
        Assertions.assertNull(con.last_event_time);
        Assertions.assertNull(con.message_timer);
        Assertions.assertNull(con.last_event);

        Assertions.assertNull(con.getConversationName());
        con.setConversationName("Conversation");
        Assertions.assertEquals("Conversation", con.getConversationName());
    }

    @Test
    void conversationType()
    {
        WireConversation con = new WireConversation();
        Assertions.assertEquals(CONVERSATIONTYPE.UNKNOWN, con.conversationType);

        con.setConversationType(0);
        Assertions.assertEquals(CONVERSATIONTYPE.GROUP, con.conversationType);

        con.setConversationType(1);
        Assertions.assertEquals(CONVERSATIONTYPE.OTHER, con.conversationType);

        con.setConversationType(2);
        Assertions.assertEquals(CONVERSATIONTYPE.NORMAL, con.conversationType);

        con.setConversationType(3);
        Assertions.assertEquals(CONVERSATIONTYPE.UNKNOWN, con.conversationType);
    }

    @Test
    void conversationMessage()
    {
        WireConversation con = new WireConversation();
        Message msg = new Message("Text", new Timestamp(0), "Sender");
        con.addMessage(msg);

        Assertions.assertEquals(msg, con.getNewMessages().get(0));
        Assertions.assertTrue(con.getNewMessages().isEmpty());
        Assertions.assertEquals(msg, con.getMessages().get(0));
    }
}
