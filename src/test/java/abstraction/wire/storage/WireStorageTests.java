package abstraction.wire.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.storage.Conversation;
import unimessenger.abstraction.wire.storage.Profile;
import unimessenger.abstraction.wire.storage.Storage;

public class WireStorageTests
{
    @Test
    void bearerToken()
    {
        Storage.getInstance().setBearerToken("A", 60);
        Assertions.assertEquals("A", Storage.getInstance().getBearerToken());
        Assertions.assertTrue(Storage.getInstance().isBearerTokenStillValid());
    }

    @Test
    void profile()
    {
        Profile p = new Profile();

        Assertions.assertNull(Storage.getInstance().getProfile());
        Storage.getInstance().init();
        Assertions.assertNotNull(Storage.getInstance().getProfile());
        Storage.getInstance().setProfile(p);
        Assertions.assertEquals(p, Storage.getInstance().getProfile());
    }

    @Test
    void conversations()
    {
        Conversation con1 = new Conversation();
        Conversation con2 = new Conversation();
        con1.setConversationID("A");

        Assertions.assertNotNull(Storage.getInstance().getConversations());
        Storage.getInstance().addConversation(con1);
        Assertions.assertEquals(con1, Storage.getInstance().getConversations().get(0));
        Storage.getInstance().addConversation(con2);
        Assertions.assertEquals(con2, Storage.getInstance().getConversations().get(1));
        Assertions.assertEquals(con1, Storage.getInstance().getConversationByID("A"));
        Assertions.assertFalse(Storage.getInstance().removeConversation(2));
        Assertions.assertTrue(Storage.getInstance().removeConversation(1));
        Assertions.assertEquals(1, Storage.getInstance().getConversations().size());
        Assertions.assertTrue(Storage.getInstance().removeConversation(con1));
        Assertions.assertTrue(Storage.getInstance().getConversations().isEmpty());
    }
}
