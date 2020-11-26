package unimessenger.userinteraction;

import unimessenger.Main;

public class Outputs
{
    private static final String RESET = "\u001B[0m";
    private static final String WHITE = "\u001B[37m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    private String color = "";
    private String creatorClass = "UNKNOWN";
    private String text = "";
    private boolean canPrint = false;

    @Deprecated
    public static void printDebug(String text)
    {
        if(Main.debug) System.out.println(YELLOW + text + RESET);
    }
    @Deprecated
    public static void printError(String text)
    {
        if(Main.verbose || Main.debug) System.out.println(RED + text + RESET);
    }

    public static Outputs create(String message)
    {
        Outputs o = new Outputs();
        o.text = message;
        return o;
    }
    public static Outputs create(String message, String className)
    {
        Outputs o = create(message);
        o.creatorClass = className;
        return o;
    }

    public Outputs always()
    {
        canPrint = true;
        return this;
    }
    public Outputs debug()
    {
        if(Main.debug) canPrint = true;
        return this;
    }
    public Outputs verbose()
    {
        if(Main.verbose) canPrint = true;
        return this;
    }

    public Outputs INFO()
    {
        color = WHITE;
        return this;
    }
    public Outputs WARNING()
    {
        color = YELLOW;
        return this;
    }
    public Outputs ERROR()
    {
        color = RED;
        return this;
    }

    public void print()
    {
        if(canPrint) System.out.println(color + creatorClass + ":" + text + RESET);
    }
}