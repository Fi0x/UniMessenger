package unimessenger.abstraction.wire.crypto;

import com.waz.model.Messages;
import unimessenger.abstraction.wire.messages.FileAsset;
import unimessenger.abstraction.wire.messages.FileAssetPreview;
import unimessenger.abstraction.wire.messages.MessageText;
import unimessenger.abstraction.wire.messages.Ping;
import unimessenger.userinteraction.Outputs;

import java.io.File;
import java.util.UUID;

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

    public static Messages.GenericMessage createGenericFilePreviewMessage(File file, UUID id)
    {
        FileAssetPreview preview = new FileAssetPreview(file.getName(), "image/png", file.length(), id);
        return preview.createGenericMsg();
    }
    public static Messages.GenericMessage createGenericFileMessage(File file, UUID id)
    {
        try
        {
            FileAsset asset = new FileAsset(file, "image/png", id);//TODO: Set correct mime type
            asset.setAssetKey("Keine Ahnung was hier rein muss");//TODO: Set correct asset key
            asset.setAssetToken("Genau gleich wenig Ahnung");//TODO: Set correct asset token
            return asset.createGenericMsg();
        } catch(Exception ignored)
        {
            Outputs.create("Could not create FileAsset", "MessageCreator").verbose().WARNING().print();
        }
        return null;
    }
}