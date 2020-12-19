package unimessenger.userinteraction.tui.menu;

import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.ILoginOut;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.userinteraction.tui.CLI;
import unimessenger.userinteraction.tui.Inputs;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.Updater;
import unimessenger.util.enums.MENU;
import unimessenger.util.enums.SERVICE;

public class MenuLogin
{
    public static void showMenu()
    {
        System.out.println("1) '" + CLI.currentService + "' login");
        System.out.println("2) Show Main Menu");
        System.out.println("3) Exit Program");

        int userInput = Inputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        switch(userInput)
        {
            case 1:
                if(connectUser())
                {
                    Updater.addService(CLI.currentService);
                    CLI.currentMenu = MENU.CONVERSATION_LIST;
                }
                break;
            case 2:
                CLI.currentService = SERVICE.NONE;
                CLI.currentMenu = MENU.MAIN;
                break;
            case 3:
                CLI.currentMenu = MENU.EXIT;
                break;
            default:
                Out.newBuilder("Invalid option").a().WARNING().print();
                break;
        }
    }

    private static boolean connectUser()
    {
        APIAccess access = new APIAccess();
        ILoginOut login = access.getLoginInterface(CLI.currentService);
        boolean loggedIn = false;

        if(login.checkIfLoggedIn()) loggedIn = true;
        else if(WireStorage.getBearerToken() != null && access.getUtilInterface(CLI.currentService).refreshSession()) loggedIn = true;
        else if(login.login()) loggedIn = true;
        if(loggedIn)
        {
            if(!access.getUtilInterface(CLI.currentService).loadProfile()) Out.newBuilder("Could not load profile").origin("MenuLogin").v().d().ERROR().print();
            return true;
        } else
        {
            System.out.println("Failed to log in");
            return false;
        }
    }
}