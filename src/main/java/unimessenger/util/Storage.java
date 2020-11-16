package unimessenger.util;

import java.sql.Timestamp;

public class Storage
{
    public static String wireUserID;
    public static String wireBearerToken;
    private static Timestamp wireBearerTokenExpiringTime;

    public static void storeWireAccessToken(String user, String accessToken)
    {
        //TODO: Store token in file
    }
    public static void setWireBearerTime(int expiresInSec)
    {
        wireBearerTokenExpiringTime = new Timestamp(System.currentTimeMillis() + (expiresInSec * 900));
    }
    public static boolean isWireBearerTokenStillValid()
    {
        return wireBearerTokenExpiringTime != null && wireBearerTokenExpiringTime.getTime() > System.currentTimeMillis();
    }
    public static void clearUserData(Variables.SERVICE service)
    {
        switch(service)
        {
            case WIRE:
                wireUserID = null;
                wireBearerToken = null;
                wireBearerTokenExpiringTime = null;
                //TODO: Delete wire User file
                break;
            default:
                break;
        }
    }

    public static void writeDataToFile()
    {
        //TODO: Write variables to external file
    }
}