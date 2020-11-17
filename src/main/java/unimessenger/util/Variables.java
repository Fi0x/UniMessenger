package unimessenger.util;

public class Variables
{
    public static boolean debug = false;
    public static boolean verbose = false;

    public static Thread cli;
    public static Thread updt;

    public static final String URL_WIRE = "https://prod-nginz-https.wire.com";

    public enum SERVICE
    {
        NONE,
        WIRE,
        TELEGRAM
    }

    public enum REQUESTTYPE
    {
        GET,
        POST,
        PUT
    }
}