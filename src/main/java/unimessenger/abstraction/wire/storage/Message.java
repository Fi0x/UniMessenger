package unimessenger.abstraction.wire.storage;

import unimessenger.abstraction.interfaces.storage.IMessage;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements IMessage, Serializable
{
    private String text;
    private Timestamp time;
    private String sender;
    private long expires;

    public Message(String text, Timestamp time, String sender)
    {
        this(text, time, sender, -1);
    }
    public Message(String text, Timestamp time, String sender, long millis)
    {
        setText(text);
        setTime(time);
        setSender(sender);

        if(millis < 0) setExpires(-1);
        else setExpires(time.getTime() + millis + (1000 * 60 * 60));
    }

    @Override
    public void setText(String text)
    {
        this.text = text;
    }
    @Override
    public String getText()
    {
        return text;
    }
    @Override
    public void setSender(String sender)
    {
        this.sender = sender;
    }
    @Override
    public String getSender()
    {
        return sender;
    }
    @Override
    public void setTime(Timestamp time)
    {
        this.time = time;
    }
    @Override
    public Timestamp getTime()
    {
        return time;
    }
    @Override
    public void setExpires(long millis)
    {
        expires = millis;
    }
    @Override
    public boolean isValid()
    {
        if(expires < 0) return true;
        return expires > System.currentTimeMillis();
    }
}
