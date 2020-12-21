import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.api.WireMessageSorter;
import unimessenger.abstraction.wire.messages.MessageCreator;
import unimessenger.abstraction.wire.structures.WireConversation;

import java.sql.Timestamp;

public class WireMessageSorterTests
{
    @Test
    void testPicture()
    {
    }

    @Test
    void testPreVideo()
    {
    }

    @Test
    void testPreVideo2()
    {
    }

    @Test
    void testPreVoiceMessage()
    {
    }

    @Test
    void testPreFile()
    {
    }

    @Test
    void testGifText()
    {
    }

    @Test
    void testVideo()
    {
    }

    @Test
    void testVoiceMessage()
    {
    }

    @Test
    void testFile()
    {
    }

    @Test
    void testGifFile()
    {
    }

    @Test
    void testText()
    {
        WireConversation con = new WireConversation();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        boolean res = WireMessageSorter.handleReceivedMessage(MessageCreator.createGenericTextMessage("Text"), con, time, "Sender");

        Assertions.assertTrue(res);
        Assertions.assertFalse(con.getMessages().isEmpty());
        Assertions.assertEquals("Text", con.getMessages().get(0).getText());
        Assertions.assertEquals("Sender", con.getMessages().get(0).getSender());
        Assertions.assertEquals(time, con.getMessages().get(0).getTime());
    }

    @Test
    void testPing()
    {
        WireConversation con = new WireConversation();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        boolean res = WireMessageSorter.handleReceivedMessage(MessageCreator.createGenericPingMessage(), con, time, "Sender");

        Assertions.assertTrue(res);
        Assertions.assertTrue(con.getMessages().isEmpty());
    }

    @Test
    void testCall()
    {
    }

    @Test
    void testConfirmation()
    {
    }

    @Test
    void testDeleted()
    {
    }

    @Test
    void testEdited()
    {
    }

    @Test
    void testTimed()
    {
        WireConversation con = new WireConversation();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        boolean res = WireMessageSorter.handleReceivedMessage(MessageCreator.createGenericTimedMessage("Text", 60), con, time, "Sender");

        Assertions.assertTrue(res);
        Assertions.assertFalse(con.getMessages().isEmpty());
        Assertions.assertEquals("Text", con.getMessages().get(0).getText());
        Assertions.assertEquals("Sender", con.getMessages().get(0).getSender());
        Assertions.assertEquals(time, con.getMessages().get(0).getTime());
        Assertions.assertTrue(con.getMessages().get(0).isValid());
    }

    @Test
    void testLocation()
    {
    }

    @Test
    void testUnknown()
    {
    }
}
