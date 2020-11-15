package unimessenger.userinteraction.menu;

import unimessenger.apicommunication.HTTP;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Variables;

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
                String mail = "pechtl97@gmail.com";
                String pw = "Passwort1!";
                //TODO: Ask user for login information
                HTTP.sendRequest(Variables.URL_WIRE + "/login?persist=false", Variables.REQUESTTYPE.POST, "{\"email\":\"" + mail + "\",\"password\":\"" + pw + "\"}", "content-type", "application/json", "accept", "application/json");
                //TODO: Validate Wire account information and show either wire overview or login menu again
                CLI.currentMenu = CLI.MENU.WireOverview;
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
}