package unimessenger.userinteraction.tui;

public class Out
{
    private static final String RESET = "\u001B[0m";
    private static final String WHITE = "\u001B[37m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    public static boolean d;
    public static boolean v;
    public static boolean vv;
    public static boolean vvv;

    private boolean always;
    private boolean debug;
    private boolean verbose;
    private boolean vVerbose;

    private String text;
    private String origin;
    private String color;

    public static Out newBuilder(String message)
    {
        Out o = new Out();
        o.text = message;
        o.origin = null;
        o.color = WHITE;
        return o;
    }
    public Out origin(String className)
    {
        origin = className;
        return this;
    }

    public static Out create(String message)
    {
        Out o = new Out();
        o.text = message;
        return o;
    }

    public Out a()
    {
        always = true;
        return this;
    }
    public Out d()
    {
        debug = true;
        return this;
    }
    public Out v()
    {
        verbose = true;
        return this;
    }
    public Out vv()
    {
        vVerbose = true;
        return this;
    }

    public Out WARNING()
    {
        color = YELLOW;
        return this;
    }
    public Out ERROR()
    {
        color = RED;
        return this;
    }
    public Out ALERT()
    {
        color = GREEN;
        return this;
    }

    public void print()
    {
        boolean allowed = false;

        if(always) allowed = true;
        if(debug && d) allowed = true;
        if(verbose && v) allowed = true;
        if(vVerbose && vv) allowed = true;
        if(vvv) allowed = true;

        if(allowed)
        {
            if(origin == null) System.out.print(color + text + RESET);
            else System.out.println(origin + ":\t\t" + color + text + RESET);
        }
    }
}