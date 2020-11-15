package unimessenger.userinteraction;

import unimessenger.util.Variables;

import java.util.Scanner;

public class Outputs
{
    private static final String RESET = "\u001B[0m";
    private static final String INFO = "\u001B[37m[INFO]";
    private static final String DEBUG = "\u001B[33m[DEBUG]";
    private static final String ERROR = "\u001B[31m[ERROR]";
    private static final Scanner sc = new Scanner(System.in);

    public static void printInfo(String text)
    {
        if(Variables.verbose) System.out.println(INFO + text + RESET);
    }
    public static void printDebug(String text)
    {
        if(Variables.debug) System.out.println(DEBUG + text + RESET);
    }
    public static void printError(String text)
    {
        if(Variables.verbose || Variables.debug) System.out.println(ERROR + text + RESET);
    }

    public static int getIntAnswerFrom(String question)
    {
        System.out.println(question);
        System.out.print("Input: ");

        printDebug("Waiting for user-input...");

        int ret = sc.nextInt();
        //TODO: Clear scanner
        return ret;
    }
    public static String getStringAnswerFrom(String question)
    {
        System.out.println(question);
        System.out.print("Input: ");

        printDebug("Waiting for user-input...");

        String ret = sc.next();
        //TODO: Clear scanner
        return ret;
    }
    public static void cannotHandleUserInput()
    {
        System.out.println("Invalid Option.");
    }
}