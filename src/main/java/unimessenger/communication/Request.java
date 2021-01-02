package unimessenger.communication;

import unimessenger.util.enums.METHOD;
import unimessenger.util.enums.REQUEST;
import unimessenger.util.enums.SERVICE;

import java.util.ArrayList;
import java.util.Arrays;

public class Request
{
    public final SERVICE responseService;
    public final METHOD responseMethod;
    public final String url;
    public final REQUEST type;
    public String body;
    public final ArrayList<String> headers;

    public Request(SERVICE responseService, METHOD responseMethod, String url, REQUEST type)
    {
        this(responseService, responseMethod, url, type, null, (String) null);
    }
    public Request(SERVICE responseService, METHOD responseMethod, String url, REQUEST type, String body)
    {
        this(responseService, responseMethod, url, type, body, (String) null);
    }
    public Request(SERVICE responseService, METHOD responseMethod, String url, REQUEST type, String body, String... headers)
    {
        this.responseService = responseService;
        this.responseMethod = responseMethod;
        this.url = url;
        this.type = type;
        this.body = body;
        this.headers = new ArrayList<>();
        this.headers.addAll(Arrays.asList(headers));
    }
}
