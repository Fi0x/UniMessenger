import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.storage.Message;

import java.sql.Timestamp;

public class MessageTests
{
    Timestamp current = new Timestamp(System.currentTimeMillis());
    Message m1 = new Message("Text1", current, "Sender1");
    Message m2 = new Message("Text2", current, "Sender2", 60);
    @Test
    void text()
    {
        Assertions.assertEquals("Text1", m1.getText());
        Assertions.assertEquals("Text2", m2.getText());
    }
    @Test
    void time()
    {
        Assertions.assertEquals(current, m1.getTime());
        Assertions.assertEquals(current, m2.getTime());
    }
    @Test
    void expires()
    {
        Assertions.assertTrue(m1.isValid());
        Assertions.assertTrue(m2.isValid());
    }
    @Test
    void sender()
    {
        Assertions.assertEquals("Sender1", m1.getSender());
        Assertions.assertEquals("Sender2", m2.getSender());
    }
}
