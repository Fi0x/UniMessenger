package unimessenger.abstraction.wire.storage;

import unimessenger.abstraction.interfaces.storage.IUser;

public class User implements IUser
{
    private String id;
    private String userName;

    @Override
    public void setUserID(String id)
    {
        this.id = id;
    }
    @Override
    public String getUserID()
    {
        return id;
    }
    @Override
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    @Override
    public String getUserName()
    {
        return userName;
    }
}
