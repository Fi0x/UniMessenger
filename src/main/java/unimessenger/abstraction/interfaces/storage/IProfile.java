package unimessenger.abstraction.interfaces.storage;

import java.io.Serializable;

public interface IProfile extends Serializable
{
    void setMail(String mail);
    String getMail();
    void setPhone(String number);
    String getPhone();
    void setPW(String pw);
    String getPW();

    void setUsername(String userName);
    String getUsername();
    void setUserID(String userID);
    String getUserID();
}
