package unimessenger.userinteraction;

public class CLI implements Runnable
{
    private static MENU currentMenu;

    @Override
    public void run()
    {
        startCLI();
    }

    public static void startCLI()
    {
        currentMenu = MENU.MainMenu;
        while(currentMenu != MENU.EXIT)
        {
            System.out.println("Current Menu: " + currentMenu);
            System.out.println("Options:");
            switch (currentMenu) {
                case MainMenu:
                    showMainMenu();
                    break;
                case WireLogin:
                    showMenuWireLogin();
                    break;
                case WireOverview:
                    showMenuWireMain();
                    break;
                case WireChat:
                    showMenuWireChat();
                    break;
                default:
                    Outputs.printError("Unknown menu state");
                    Outputs.printDebug("Switching to main menu...");
                    currentMenu = MENU.MainMenu;
                    break;
            }
        }
        Outputs.printInfo("Exiting program...");
    }

    private static void showMainMenu()
    {
        System.out.println("1) Wire");
        System.out.println("2) Exit Program");

        int userInput = Outputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        switch(userInput)
        {
            case 1:
                //TODO: Validate Wire account information and either log user in or show login menu
                currentMenu = MENU.WireLogin;
                currentMenu = MENU.WireOverview;
                break;
            case 2:
                currentMenu = MENU.EXIT;
                break;
            default:
                Outputs.cannotHandleUserInput();
                break;
        }
    }
    private static void showMenuWireLogin()
    {
        System.out.println("1) Enter Login Information");
        System.out.println("2) Show Main Menu");
        System.out.println("3) Exit Program");

        int userInput = Outputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        switch(userInput)
        {
            case 1:
                //TODO: Ask user for login information
                //TODO: Validate Wire account information and show either wire overview or login menu again
                currentMenu = MENU.WireOverview;
                break;
            case 2:
                currentMenu = MENU.MainMenu;
                break;
            case 3:
                currentMenu = MENU.EXIT;
                break;
            default:
                Outputs.cannotHandleUserInput();
                break;
        }
    }
    private static void showMenuWireMain()
    {
        //TODO Show wire main menu and wait for user input
    }
    private static void showMenuWireChat()
    {
        //TODO: Show wire chat and wait for user input
    }

    public enum MENU
    {
        MainMenu,
        WireLogin,
        WireOverview,
        WireChat,
        EXIT
    }
}