import java.util.ArrayList;
import java.util.Arrays;

public class Main
{
    public static boolean debug = false;
    public static boolean verbose = false;

    public static void main(String[] args)
    {
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));

        if(arguments.contains("-d")) debug = true;
        if(arguments.contains("-v")) verbose = true;

        Outputs.printInfo("Uni-Messenger starting...");
        Outputs.printDebug("Loading login files...");
        //TODO: Load files with login data
        Outputs.printDebug("Login files loaded");

        //TODO: Either ask user to select a messenger service, or show all conversations of all messenger services in 1 list

        Outputs.printDebug("Loading stored messages...");
        //TODO: Load files with previously saved messages of selected messenger or all messengers
        Outputs.printDebug("Stored messages loaded");

        Outputs.printInfo("Uni-Messenger started");
    }
}