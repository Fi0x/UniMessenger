package unimessenger.communication;

import unimessenger.util.enums.REQUEST;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HTTP
{
    static HttpClient client = HttpClient.newHttpClient();

    public CompletableFuture<HttpResponse<String>> sendAsyncRequest(String url, REQUEST type, String body, String... headers)
    {
        HttpRequest request = getRequest(url, type, body, headers);
        CompletableFuture<HttpResponse<String>> response = null;

        if(request != null)
        {
            try
            {
                response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            } catch(Exception ignored)
            {
            }
        }
        return response;
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