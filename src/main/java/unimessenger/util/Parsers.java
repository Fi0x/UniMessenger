package unimessenger.util;

public class Parsers
{
    public static String parseCookieID(String raw)
    {
        String[] arr = raw.split("zuid=");
        if(arr.length > 1) return arr[1].split(";")[0];
        else return null;
    }
}
