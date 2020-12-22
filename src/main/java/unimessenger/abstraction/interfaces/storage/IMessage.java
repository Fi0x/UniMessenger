package unimessenger.abstraction.interfaces.storage;

import java.io.Serializable;
import java.sql.Timestamp;

public interface IMessage extends Serializable
{
    void setText(String text);
    String getText();

    void setSender(String sender);
    String getSender();

    void setTime(Timestamp time);
    Timestamp getTime();

    void setExpires(long millis);
    boolean isValid();
}
