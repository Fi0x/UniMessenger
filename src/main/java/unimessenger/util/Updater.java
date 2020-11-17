package unimessenger.util;

import unimessenger.abstraction.APIAccess;
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

        while(!runningServices.isEmpty())
        {
            for(Variables.SERVICE service : runningServices)
            {
                validateAccess(service);
                //TODO: Refresh conversation list
                //TODO: Refresh messages
            }
            try
            {
                Thread.sleep(2000);
            } catch(InterruptedException ignored)
            {
            }
        }
    }

    private boolean validateAccess(Variables.SERVICE service)//TODO: Rework
    {
        switch(service)
        {
            case WIRE:
                if(!Storage.isWireBearerTokenStillValid())
                {
                    if(Storage.wireAccessCookie == null || !(new APIAccess().getUtilInterface(service).refreshSession())) return false;
                }
                break;
            default:
                Outputs.printError("Unknown service: " + service);
                break;
        }
        return true;
    }

    private static void initializeServices()
    {
        runningServices.add(Variables.SERVICE.WIRE);
    }
}