package unimessenger.abstraction.wire.crypto;

import com.waz.model.Messages;
import unimessenger.abstraction.wire.messages.FileAsset;
import unimessenger.abstraction.wire.messages.MessageText;
import unimessenger.abstraction.wire.messages.Ping;
import unimessenger.userinteraction.Outputs;

import java.io.File;

public class MessageCreator
{
    public static Messages.GenericMessage createGenericTextMessage(String text)
    {
        MessageText msg = new MessageText(text);
        return msg.createGenericMsg();
    }

    public static Messages.GenericMessage createGenericPingMessage()
    {
        Ping msg = new Ping();
        return msg.createGenericMsg();
    }

    public static Messages.GenericMessage createGenericFileMessage(File file)
    {
        try
        {
            FileAsset asset = new FileAsset(file, "");
            return asset.createGenericMsg();
        } catch(Exception ignored)
        {
            Outputs.create("Could not create FileAsset", "MessageCreator").verbose().WARNING().print();
        }
        return null;
    }
}