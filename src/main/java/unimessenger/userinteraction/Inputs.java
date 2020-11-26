package unimessenger.userinteraction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Inputs
{
    private static final Scanner sc = new Scanner(System.in);
    private static final BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

    public static int getIntAnswerFrom(String question)
    {
        System.out.println(question);
        System.out.print("Input: ");

        int ret = -1;
        try
        {
            String in = sc.next();
            ret = Integer.parseInt(in);
        } catch(Exception ignored)
        {
            Outputs.printError("Input was not an integer");
        }
        return ret;
    }
    public static String getStringAnswerFrom(String question)
    {
        System.out.println(question);
        System.out.print("Input: ");

        String ret = null;
        try
        {
            ret = sc.next();
        } catch(Exception ignored)
        {
        }
        return ret;
    }
    public static boolean getBoolAnswerFrom(String question)
    {
        System.out.println(question);
        while(true)
        {
            System.out.print("Input(Yes/No): ");
            Outputs.printDebug("Waiting for user-input...");

            String answer = sc.next();

            if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) return true;
            if(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n")) return false;

            Outputs.printDebug("Invalid user-input");
            System.out.println("Invalid input. Options are 'yes' or 'no'");
        }
    }
    public static String getTextAnswerFrom(String question)
    {
        System.out.println(question);
        System.out.println("Empty line to finish text input");

        String ret = "";
        while(true)
        {
            String line = "";
            try
            {
                line = rd.readLine();
            } catch(Exception ignored)
            {
                Outputs.printError("Problem with reading a line");
            }
            if(line.equals("")) break;
            ret += System.lineSeparator();
            ret += line;
        }

        return ret;
    }
}
