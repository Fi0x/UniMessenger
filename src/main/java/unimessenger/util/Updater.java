package unimessenger.util;

import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.api.ILoginOut;
import unimessenger.abstraction.wire.storage.Storage;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.enums.SERVICE;

import java.util.ArrayList;

public class Updater implements Runnable
{
    private static ArrayList<SERVICE> runningServices;

    @Override
    public void run()
    {
        runningServices = new ArrayList<>();
        int seconds = 0;

        while(!Thread.interrupted())
        {
            for(SERVICE service : runningServices)
            {
                if(validateAccess(service))
                {
                    APIAccess access = new APIAccess();
                    if(seconds % 10 == 0) access.getConversationInterface(service).requestAllConversations();//TODO: Refresh only changed conversations if possible
                    if(seconds % 2 == 0 && !access.getMessageInterface(service).receiveNewMessages())//TODO: Might need to change to /await
                    {
                        Out.newBuilder("Error receiving new messages").origin(this.getClass().getName()).d().WARNING().print();
                    }
                } else removeService(service);
            }
            try
            {
                Thread.sleep(1000);
            } catch(InterruptedException ignored)
            {
                return;
            }
            seconds++;
            if(seconds >= 60) seconds = 0;
        }
    }

    private boolean validateAccess(SERVICE service)
    {
        APIAccess access = new APIAccess();
        ILoginOut login = access.getLoginInterface(service);
        switch(service)
        {
            case WIRE:
            case TELEGRAM:
                if(login.checkIfLoggedIn())
                {
                    if(login.needsRefresh() && Storage.getInstance().getBearerToken() != null) return access.getUtilInterface(service).refreshSession();
                    else return true;
                } else if(Storage.getInstance().getBearerToken() != null && access.getUtilInterface(service).refreshSession()) return true;
                return login.login();
            case NONE:
            default:
                Out.newBuilder("Unknown service: " + service).origin(this.getClass().getName()).d().ERROR().print();
                break;
        }
        return true;
    }

    public static void addService(SERVICE service)
    {
        if(!runningServices.contains(service)) runningServices.add(service);
    }
    public static void removeService(SERVICE service)
    {
        runningServices.remove(service);
    }
}