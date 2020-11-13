public class Outputs
{
    private static final String INFO = "[INFO]";
    private static final String DEBUG = "[DEBUG]";
    private static final String ERROR = "[ERROR]";

    public static void printInfo(String text)
    {
        if(Main.verbose) System.out.println(INFO + text);
    }

    public static void printDebug(String text)
    {
        if(Main.debug) System.out.println(DEBUG + text);
    }

    public static void printError(String text)
    {
        System.out.println(ERROR + text);
    }
}