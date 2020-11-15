package unimessenger.userinteraction.menu;

import unimessenger.apicommunication.HTTP;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Variables;

import java.net.http.HttpResponse;

public class MenuWireLogin
{
    public static void showMenu()
    {
        System.out.println("1) Enter Login Information");
        System.out.println("2) Show Main Menu");
        System.out.println("3) Exit Program");

        int userInput = Outputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        switch(userInput)
        {
            case 1:
                if(handleUserLogin()) CLI.currentMenu = CLI.MENU.WireOverview;
                else System.out.println("Failed to log in");
                break;
            case 2:
                CLI.currentMenu = CLI.MENU.MainMenu;
                break;
            case 3:
                CLI.currentMenu = CLI.MENU.EXIT;
                break;
            default:
                Outputs.cannotHandleUserInput();
                break;
        }
    }

    private static boolean handleUserLogin()
    {
//        String mail = "pechtl97@gmail.com";
//        String pw = "Passwort1!";
        //TODO: Add more login options
        String mail = Outputs.getStringAnswerFrom("Please enter your E-Mail");
        String pw = Outputs.getStringAnswerFrom("Please enter your password");

        String url = Variables.URL_WIRE + "/login?persist=false";
        String body = "{\"email\":\"" + mail + "\",\"password\":\"" + pw + "\"}";
        String[] headers = new String[] {"content-type", "application/json", "accept", "application/json"};

        HttpResponse<String> response = HTTP.sendRequest(url, Variables.REQUESTTYPE.POST, body, headers);

        return response.statusCode() == 200;
    }
}