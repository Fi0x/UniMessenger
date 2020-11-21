package unimessenger.userinteraction.menu;

import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.IData;
import unimessenger.abstraction.wire.WireMessages;
import unimessenger.abstraction.wire.WireUtil;
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
        System.out.println("10) Load Profile");//TODO: Remove
        System.out.println("11) Print Notifications");//TODO: Remove

        int userInput = Outputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        switch(userInput)
        {
            case 1:
                listAllConversations();
                break;
            case 2:
                if(selectChat()) CLI.currentMenu = MENU.CHAT;
                break;
            case 3:
                if(disconnect()) Updater.removeService(CLI.currentService);
                else break;
            case 4:
                CLI.currentService = SERVICE.NONE;
                CLI.currentMenu = MENU.MAIN;
                break;
            case 5:
                CLI.currentMenu = MENU.EXIT;
                break;
            case 10:
                new WireUtil().loadProfile();
                break;
            case 11:
                WireMessages.PrintNotifications();
                break;
            default:
                Outputs.cannotHandleUserInput();
                break;
        }
    }

    private static void listAllConversations()
    {
        IData data = new APIAccess().getDataInterface(CLI.currentService);
        ArrayList<String> ids = data.getAllConversationIDs();
        ids.remove(null);

        System.out.println("List of all conversations in '" + CLI.currentService + "':");
        for(int i = 0; i < ids.size(); i++)
        {
            System.out.println((i + 1) + ") " + data.getConversationNameFromID(ids.get(i)));
        }
    }

    private static boolean selectChat()
    {
        String userInput = Outputs.getStringAnswerFrom("Please type in the name of the person or group you would like to see the chat from");
        IData data = new APIAccess().getDataInterface(CLI.currentService);

        ArrayList<String> ids = data.getAllConversationIDs();
        ids.remove(null);
        ArrayList<String> names = new ArrayList<>();
        for(String id : ids)
        {
            names.add(data.getConversationNameFromID(id));
        }

        ArrayList<Integer> matches = new ArrayList<>();
        int selectedConversation;
        for(int i = 0; i < names.size(); i++)
        {
            if(names.get(i) == null) continue;
            if(names.get(i).contains(userInput)) matches.add(i);
        }
        if(matches.size() == 0)
        {
            System.out.println("No conversation found that contains your choice");
            return false;
        }
        if(matches.size() == 1)
        {
            selectedConversation = matches.get(0);
        } else
        {
            System.out.println("The following chats match your choice:");
            for(int i = 0; i < matches.size(); i++)
            {
                System.out.println((i + 1) + ") " + names.get(matches.get(i)));
            }
            int input = Outputs.getIntAnswerFrom("Select the number of the chat you would like to view");
            if(input > matches.size() || input <= 0)
            {
                System.out.println("Couldn't find that conversation");
                return false;
            }
            selectedConversation = matches.get(input - 1);
        }

        Outputs.printDebug("Opening conversation '" + selectedConversation + "'...");
        CLI.currentChatID = ids.get(selectedConversation);
        return true;
    }

    private static boolean disconnect()
    {
        if(new APIAccess().getLoginInterface(CLI.currentService).logout()) return true;

        System.out.println("There was a logout error");
        return false;
    }
}