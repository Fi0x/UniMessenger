package unimessenger.userinteraction.menu;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.apicommunication.HTTP;
import unimessenger.apicommunication.StringBuilder;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Variables;
import unimessenger.wire.Commands;

import java.net.http.HttpResponse;
import java.util.Map;

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
        //TODO: Add more login options (phone)
        String mail = Outputs.getStringAnswerFrom("Please enter your E-Mail");//TestAccount: pechtl97@gmail.com
        String pw = Outputs.getStringAnswerFrom("Please enter your password");//TestAccount: Passwort1!
        boolean persist = Outputs.getBoolAnswerFrom("Do you want to stay logged in?");

        String url = Variables.URL_WIRE;
        if(persist) url += Commands.PERSIST_LOGIN;
        else url += Commands.LOGIN;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.addStringEntry("email", mail);
        stringBuilder.addStringEntry("password", pw);
        String body = stringBuilder.getJSONString();

        String[] headers = new String[] {"content-type", "application/json", "accept", "application/json"};

        HttpResponse<String> response = HTTP.sendRequest(url, Variables.REQUESTTYPE.POST, body, headers);
        if(response == null || response.statusCode() != 200) return false;

        //TODO: Use the following example code in a better way
        System.out.println(response.headers());
        Map h = response.headers().map();
        System.out.println(h.get("connection"));

        JSONObject obj;
        try
        {
            obj = (JSONObject) new JSONParser().parse(response.body());
            System.out.println("TTL: " + obj.get("expires_in"));
            System.out.println("Access Token: " + obj.get("access_token"));
            System.out.println("User: " + obj.get("user"));
            System.out.println("Token Type: " + obj.get("token_type"));
        } catch(ParseException ignored)
        {
        }
        return true;
    }
}