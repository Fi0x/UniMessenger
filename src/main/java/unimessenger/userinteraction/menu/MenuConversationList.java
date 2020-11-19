package unimessenger.userinteraction.menu;

import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.IData;
import unimessenger.abstraction.wire.WireMessages;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Updater;
import unimessenger.util.enums.MENU;
import unimessenger.util.enums.SERVICE;

import java.util.ArrayList;

public class MenuConversationList
{
    public static void showMenu()
    {
        System.out.println("1) List all '" + CLI.currentService + "' Chats");
        System.out.println("2) Open specific '" + CLI.currentService + "' Chat");
        System.out.println("3) Log out of '" + CLI.currentService + "'");
        System.out.println("4) Show Main Menu");
        System.out.println("5) Exit Program");
        System.out.println("10) Refresh Token");//TODO: Remove
        System.out.println("11) Print Notifications");//TODO: Remove
        System.out.println("12) Load Conversations");//TODO: Remove

        int userInput = Outputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        switch(userInput)
        {
            case 1:
                listAllConversations();
                break;
            case 2:
                selectChat();
                break;
            case 3:
                if(disconnect())
                {
                    Updater.removeService(CLI.currentService);
                } else break;
            case 4:
                CLI.currentService = SERVICE.NONE;
                CLI.currentMenu = MENU.MAIN;
                break;
            case 5:
                CLI.currentMenu = MENU.EXIT;
                break;
            case 10:
                if(new APIAccess().getUtilInterface(CLI.currentService).refreshSession())
                    System.out.println("Successfully refreshed your bearer token");
                break;
            case 11:
                WireMessages.PrintNotifications();
                break;
            case 12:
                if(new APIAccess().getConversationInterface(CLI.currentService).requestAllConversations())
                    System.out.println("Successfully loaded conversations");
                break;
            default:
                Outputs.cannotHandleUserInput();
                break;
        }
    }

    private static void listAllConversations()
    {
        ArrayList<String> names = new APIAccess().getDataInterface(CLI.currentService).getAllConversationNames();
        names.remove(null);

        System.out.println("List of all conversations in '" + CLI.currentService + "':");
        for(int i = 0; i < names.size(); i++)
        {
            System.out.println((i + 1) + ") " + names.get(i));
        }
    }

    private static void selectChat()
    {
        String userInput = Outputs.getStringAnswerFrom("Please type in the name of the person or group you would like to see the chat from");
        IData data = new APIAccess().getDataInterface(CLI.currentService);

        ArrayList<String> names = data.getAllConversationNames();
        names.remove(null);
        ArrayList<String> matches = new ArrayList<>();
        String selectedConversation = null;

        for(String name : names)
        {
            if(name.contains(userInput)) matches.add(name);
        }
        if(matches.size() == 0)
        {
            System.out.println("No conversation found that contains your choice");
            return;
        }
        if(matches.size() == 1)
        {
            selectedConversation = matches.get(0);
        } else if(matches.size() > 1)
        {
            System.out.println("The following chats match your choice:");
            for(int i = 0; i < matches.size(); i++)
            {
                System.out.println((i + 1) + ") " + matches.get(i));
            }
            int input = Outputs.getIntAnswerFrom("Select the number of the chat you would like to view");
            if(input > matches.size() || input <= 0)
            {
                System.out.println("Couldn't find that conversation");
                return;
            }
            selectedConversation = matches.get(input - 1);
        }

        Outputs.printDebug("Opening conversation '" + selectedConversation + "'...");
        //TODO: Open the selected conversation and show options
    }

    private static boolean disconnect()
    {
        if(new APIAccess().getLoginInterface(CLI.currentService).logout()) return true;

        System.out.println("There was a logout error");
        return false;
    }
}