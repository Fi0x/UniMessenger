package unimessenger.userinteraction.menu;

import org.json.simple.JSONObject;
import unimessenger.apicommunication.HTTP;
import unimessenger.apicommunication.RequestBuilder;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Storage;
import unimessenger.util.Variables;
import unimessenger.util.Commands;
import unimessenger.util.Variables;

import java.net.Authenticator;

public class MenuWireOverview
{
    public static void showMenu()
    {
        System.out.println("1) List all Chats");
        System.out.println("2) Open specific Chat");
        System.out.println("3) Show Main Menu");
        System.out.println("4) Log out of Wire");
        System.out.println("5) Refresh Token");
        System.out.println("6) Log Out");
        System.out.println("7) Exit Program");

        int userInput = Outputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        switch(userInput)
        {
            case 1:
                showConversationList();
                break;
            case 2:
                chatSelection();
                break;
            case 3:
                CLI.currentMenu = CLI.MENU.MainMenu;
                break;
            case 4:
                Storage.clearUserData(Variables.SERVICE.WIRE);
                CLI.currentMenu = CLI.MENU.WireLogin;
                break;
            case 5:
                //Todo refresh access token
                String url = Variables.URL_WIRE + Commands.ACCESS;
                JSONObject obj = new JSONObject();
                //obj.put("access_token", mail);
                //obj.put("cookie", pw);
                String body = obj.toJSONString();
                //String req = RequestBuilder.getPOSTRequest();
                break;
            case 6:
                //TODO log user out
                break;
            case 7:
                CLI.currentMenu = CLI.MENU.EXIT;
                break;
            default:
                Outputs.cannotHandleUserInput();
                break;
        }
    }
    private static void showConversationList()
    {
        System.out.println("List of all conversations in Wire:");
        //TODO: Show a list of all Wire-conversations
    }
    private static void chatSelection()
    {
        String userInput = Outputs.getStringAnswerFrom("Please type in the name of the person or group you would like to see the chat from");
        //TODO: Check if named conversation exists in Wire and open it if true
    }
}