package unimessenger.abstraction.encryption.WireCrypto;

public class Prekey {
    private int ID;
    private String key;

    Prekey (int ID, String key){
        this.ID = ID;
        this.key = key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public int getID(){
        return  ID;
    }

    public String getKey(){
        return key;
    }

}
