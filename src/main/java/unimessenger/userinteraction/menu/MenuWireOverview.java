package unimessenger.userinteraction.menu;

import org.json.simple.JSONObject;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Commands;
import unimessenger.util.Storage;
import unimessenger.util.Variables;

import java.net.http.HttpResponse;

public class MenuWireOverview
{
    public static void showMenu()
    {
        System.out.println("1) List all Chats");
        System.out.println("2) Open specific Chat");
        System.out.println("3) Show Main Menu");
        System.out.println("4) Log out of Wire");
        System.out.println("5) Refresh Token");
        System.out.println("6) Exit Program");

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
                refreshAccess();
                break;
            case 6:
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
    private static void refreshAccess(){
        String url = Variables.URL_WIRE + Commands.ACCESS;
        JSONObject obj = new JSONObject();
        obj.put("access_token", Storage.wireBearerToken);
        obj.put("cookie", Storage.wireAccessCookie);
        String body = obj.toJSONString();
        String[] headers = new String[] {"content-type", "application/json"};
        HttpResponse<String> response = CLI.userHTTP.sendRequest(url, Variables.REQUESTTYPE.POST, body, headers);

        if(response == null || response.statusCode() != 200) {
            System.out.println("NOPE:");
            System.out.println("THIS:" + response.statusCode());
        }
        else System.out.println("YaYY");
    }
}