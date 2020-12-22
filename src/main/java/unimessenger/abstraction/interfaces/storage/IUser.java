package unimessenger.abstraction.interfaces.storage;

import java.io.Serializable;

public interface IUser extends Serializable
{
    void setUserID(String id);
    String getUserID();
    void setUserName(String name);
    String getUserName();
}
