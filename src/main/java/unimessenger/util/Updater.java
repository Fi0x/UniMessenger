package unimessenger.util;

import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.ILoginOut;
import unimessenger.apicommunication.HTTP;
import unimessenger.userinteraction.Outputs;

import java.util.ArrayList;

public class Updater implements Runnable
{
    public static ArrayList<Variables.SERVICE> runningServices = new ArrayList<>();
    private static HTTP updateHTTP;

    @Override
    public void run()
    {
        updateHTTP = new HTTP();
        initializeServices();

        while(! runningServices.isEmpty())
        {
            for(Variables.SERVICE service : runningServices)
            {
                if(validateAccess(service))
                {
                    new APIAccess().getConversationInterface(service).requestAllConversations();//TODO: Refresh only changed conversations if possible
                    //TODO: Refresh messages
                }
            }
            try
            {
                Thread.sleep(2000);
            } catch(InterruptedException ignored)
            {
            }
        }
    }

    private boolean validateAccess(Variables.SERVICE service)
    {
        ILoginOut login = new APIAccess().getLoginInterface(service);
        switch(service)
        {
            case WIRE:
            case TELEGRAM:
                if(login.checkIfLoggedIn())
                {
                    if(login.needsRefresh())
                    {
                        return login.refresh();
                    }
                    else return true;
                }
                else if(login.refresh()) return true;
                return login.login();
            case NONE:
            default:
                Outputs.printError("Unknown service: " + service);
                break;
        }
        return true;
    }

    private static void initializeServices()
    {
        runningServices.add(Variables.SERVICE.WIRE);
        //TODO: Add more services
    }
}