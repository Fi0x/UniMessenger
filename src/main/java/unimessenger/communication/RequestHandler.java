package unimessenger.communication;

import java.util.ArrayList;

public class RequestHandler implements Runnable
{
    private static RequestHandler instance;
    private Thread thread;

    private final HTTP http;
    private final ArrayList<Request> requestQueue;

    private RequestHandler()
    {
        thread = new Thread(this);
        requestQueue = new ArrayList<>();
        http = new HTTP();
    }
    public static RequestHandler getInstance()
    {
        if(instance == null) instance = new RequestHandler();
        return instance;
    }

    @Override
    public void run()
    {
        while(!Thread.interrupted() && !requestQueue.isEmpty())
        {
            http.sendRequest(requestQueue.get(0));
            requestQueue.remove(0);
        }
    }

    public void addRequest(Request request)
    {
        requestQueue.add(request);
        if(!thread.isAlive())
        {
            thread = new Thread(this);
            thread.start();
        }
    }
}
