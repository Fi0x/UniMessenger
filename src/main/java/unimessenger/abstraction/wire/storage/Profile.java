package unimessenger.abstraction.wire.storage;

import unimessenger.abstraction.interfaces.storage.IProfile;

public class Profile implements IProfile
{
    private String email;
    private String phone;
    private String pw;
    private String userName;
    private String id;

    @Override
    public void setMail(String mail)
    {
        this.email = mail;
    }
    @Override
    public String getMail()
    {
        return email;
    }
    @Override
    public void setPhone(String number)
    {
        this.phone = number;
    }
    @Override
    public String getPhone()
    {
        return phone;
    }
    @Override
    public void setPW(String pw)
    {
        this.pw = pw;
    }
    @Override
    public String getPW()
    {
        return pw;
    }
    @Override
    public void setUsername(String userName)
    {
        this.userName = userName;
    }
    @Override
    public String getUsername()
    {
        return userName;
    }
    @Override
    public void setUserID(String userID)
    {
        this.id = userID;
    }
    @Override
    public String getUserID()
    {
        return id;
    }
}
