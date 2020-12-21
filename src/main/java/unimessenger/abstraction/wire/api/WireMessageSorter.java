package unimessenger.abstraction.wire.api;

import com.waz.model.Messages;
import unimessenger.abstraction.wire.storage.Message;
import unimessenger.abstraction.wire.structures.WireConversation;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.enums.MESSAGETYPE;

import java.sql.Timestamp;


public class WireMessageSorter
{
    public static boolean handleReceivedMessage(Messages.GenericMessage message, WireConversation conversation, Timestamp time, String senderUser)
    {
        Message msg;
        switch(getMessageType(message))//TODO: Make more differences between types
        {
            case PICTURE:
                msg = new Message("IMAGE", time, senderUser);
                conversation.addMessage(msg);
                break;
            case PRE_VIDEO:
            case PRE_VIDEO_2:
            case PRE_VOICE_MESSAGE:
            case PRE_FILE:
            case GIF_TEXT:
                break;
            case VIDEO:
                msg = new Message("VIDEO", time, senderUser);
                conversation.addMessage(msg);
                break;
            case VOICE_MESSAGE:
                msg = new Message("VOICE MESSAGE", time, senderUser);
                conversation.addMessage(msg);
                break;
            case FILE:
                msg = new Message("FILE", time, senderUser);
                conversation.addMessage(msg);
                break;
            case GIF_FILE:
                //TODO: Store gifs differently
            case TEXT:
                msg = new Message(message.getText().getContent(), time, senderUser);
                conversation.addMessage(msg);
                break;
            case PING:
                Out.newBuilder("You have been pinged in: '" + conversation.getConversationName() + "'").a().ALERT().print();
                break;
            case CALL:
                //TODO: Find out the difference between start and end of a call
                Out.newBuilder("Sommeone is calling you, please accept the call on a different client").a().ALERT().print();
                break;
            case CONFIRMATION:
                Out.newBuilder("Your message has been received").v().print();
                break;
            case DELETED:
                Out.newBuilder("Message deletion request received").v().print();
                //TODO: Delete the deleted message on local storage
                break;
            case EDITED:
                Out.newBuilder("Message editing request received").v().print();
                //TODO: Update the edited message on storage
                break;
            case TIMED:
                Out.newBuilder("Timed message received").v().print();
                msg = new Message(message.getEphemeral().getText().getContent(), time, senderUser, message.getEphemeral().getExpireAfterMillis());
                conversation.addMessage(msg);
                break;
            case LOCATION:
                //TODO: Give more information about the location
                Out.newBuilder("Location has been shared").a().print();
                break;
            case UNKNOWN:
                Out.newBuilder("Unknown message type received").v().print();
                return false;
            default:
                Out.newBuilder("Error in detecting the received message type").origin("WireMessageSorter").d().ERROR().print();
                return false;
        }
        return true;
    }

    private static MESSAGETYPE getMessageType(Messages.GenericMessage message)
    {
        if(message.hasKnock()) return MESSAGETYPE.PING;
        else if(message.hasText())
        {
            //TODO: Filter gifs
            return MESSAGETYPE.TEXT;
        } else if(message.hasAsset()) return getAssetMessageType(message.getAsset());
        else if(message.hasCalling()) return MESSAGETYPE.CALL;
        else if(message.hasConfirmation()) return MESSAGETYPE.CONFIRMATION;
        else if(message.hasDeleted()) return MESSAGETYPE.DELETED;
        else if(message.hasEdited()) return MESSAGETYPE.EDITED;
        else if(message.hasEphemeral()) return MESSAGETYPE.TIMED;
        else if(message.hasLocation()) return MESSAGETYPE.LOCATION;
        else return MESSAGETYPE.UNKNOWN;
    }
    private static MESSAGETYPE getAssetMessageType(Messages.Asset msg)
    {
        if(msg.hasExpectsReadConfirmation() && msg.hasLegalHoldStatus())
        {
            if(msg.hasOriginal()) return MESSAGETYPE.PRE_FILE;
            else if(msg.hasUploaded()) return MESSAGETYPE.FILE;
        } else if(msg.hasUploaded())
        {
            if(msg.hasPreview()) return MESSAGETYPE.VIDEO;
            else if(msg.getOriginal().hasAudio()) return MESSAGETYPE.VOICE_MESSAGE;
            else if(msg.getOriginal().getMimeType().equals("image/gif")) return MESSAGETYPE.GIF_FILE;
            else if(msg.getOriginal().getMimeType().equals("image/jpeg")) return MESSAGETYPE.PICTURE;
        } else if(msg.hasPreview())
        {
            return MESSAGETYPE.PRE_VIDEO_2;
        }
        else if(msg.hasOriginal())
        {
            if(msg.getOriginal().hasAudio()) return MESSAGETYPE.PRE_VOICE_MESSAGE;
            else if(msg.getOriginal().hasVideo()) return MESSAGETYPE.PRE_VIDEO;
        }
        return MESSAGETYPE.UNKNOWN;
    }
}