import com.waz.model.Messages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.crypto.Prekey;
import unimessenger.abstraction.wire.messages.MessageCreator;

public class WireCryptoTests
{
    @Test
    void createMessages()
    {
        Messages.GenericMessage msg = MessageCreator.createGenericTextMessage("Text");
        Assertions.assertEquals("Text", msg.getText().getContent());

        msg = MessageCreator.createGenericTimedMessage("Text", 60);
        Assertions.assertEquals("Text", msg.getEphemeral().getText().getContent());
        Assertions.assertEquals(60, msg.getEphemeral().getExpireAfterMillis());

        msg = MessageCreator.createGenericPingMessage();
        Assertions.assertTrue(msg.hasKnock());
    }

    @Test
    void prekey()
    {
        Prekey pk = new Prekey(0, "A");
        Assertions.assertEquals(0, pk.getID());
        Assertions.assertEquals("A", pk.getKey());
    }
}
