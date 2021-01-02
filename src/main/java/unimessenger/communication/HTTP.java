package unimessenger.communication;

import unimessenger.abstraction.APIAccess;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTP
{
    static HttpClient client = HttpClient.newHttpClient();

    public void sendRequest(Request request)
    {
        HttpResponse<String> response = sendRequest(request.url, request.type, request.body, request.headers.toArray(new String[0]));

        switch(request.responseMethod)
        {
            case ALL_CONVERSATIONS:
                new APIAccess().getConversationInterface(request.responseService).handleConversationResponse(response);
                break;
            case NEW_MESSAGES:
                //TODO: Store messages
                break;
            //TODO: Add more cases
        }
    }
    public HttpResponse<String> sendRequest(String url, REQUEST type, String body, String... headers)
    {
        HttpRequest request = getRequest(url, type, body, headers);
        HttpResponse<String> response = null;

        if(request != null)
        {
            try
            {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch(Exception ignored)
            {
            }
        }
        return response;
    }

    private HttpRequest getRequest(String url, REQUEST type, String body, String... headers)
    {
        switch(type)
        {
            case GET:
                return RequestBuilder.getGETRequest(url, headers);
            case PUT:
                return RequestBuilder.getPUTRequest(url, body, headers);
            case POST:
                return RequestBuilder.getPOSTRequest(url, body, headers);
            case DELETE:
                return RequestBuilder.getDELETERequest(url, body, headers);
            default:
                return null;
        }
    }
}