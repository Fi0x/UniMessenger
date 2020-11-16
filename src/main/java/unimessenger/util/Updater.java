package unimessenger.util;

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
                validateAccessToken(service);
                sendRequestToServer(service);
            }
        }
    }

    private void sendRequestToServer(Variables.SERVICE service)
    {
        //TODO: Send HTTPRequest to server of specified service and ask for new messages
    }
    private void validateAccessToken(Variables.SERVICE service)
    {
        switch(service)
        {
            case WIRE:
                if(!Storage.isWireBearerTokenStillValid())
                {
                    //TODO: Renew Wire bearer token
                }
                break;
            default:
                Outputs.printError("Unknown service: " + service);
                break;
        }
    }

    private static void initializeServices()
    {
        runningServices.add(Variables.SERVICE.WIRE);
    }
}