package unimessenger.userinteraction.menu;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.apicommunication.HTTP;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Commands;
import unimessenger.util.Parsers;
import unimessenger.util.Storage;
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
        //TODO: Add more login options (phone)
        String mail = Outputs.getStringAnswerFrom("Please enter your E-Mail");//TestAccount: pechtl97@gmail.com
        String pw = Outputs.getStringAnswerFrom("Please enter your password");//TestAccount: Passwort1!
        boolean persist = Outputs.getBoolAnswerFrom("Do you want to stay logged in?");

        String url = Variables.URL_WIRE + Commands.LOGIN;
        if(persist) url += Commands.PERSIST;

        JSONObject obj = new JSONObject();
        obj.put("email", "pechtl97@gmail.com");
        obj.put("password", "Passwort1!");
        String body = obj.toJSONString();

        String[] headers = new String[] {"content-type", "application/json", "accept", "application/json"};

        return handleResponse(HTTP.sendRequest(url, Variables.REQUESTTYPE.POST, body, headers));
    }
    private static boolean handleResponse(HttpResponse<String> response)
    {
        if(response == null || response.statusCode() != 200) return false;

        //TODO: Use the following example code
        System.out.println(response.headers());
//        Map h = response.headers().map();
//        System.out.println(h.get("connection"));

        JSONObject obj;
        try
        {
            obj = (JSONObject) new JSONParser().parse(response.body());
            Storage.wireUserID = obj.get("user").toString();
            Storage.wireBearerToken = obj.get("access_token").toString();
            System.out.println("Here1");
            Storage.accessCookie = Parsers.ParseCookie(response.headers().map().get("set-cookie").get(0));
            System.out.println("Here2");

            System.out.println("Token Type: " + obj.get("token_type"));
            System.out.println("Expires in: " + obj.get("expires_in"));
            System.out.println("Access Token: " + Storage.wireBearerToken);
            System.out.println("User: " + Storage.wireUserID);
            System.out.println("Cookie: " + Storage.accessCookie);
        } catch(ParseException ignored)
        {
            return false;
        }
        return true;
    }
}