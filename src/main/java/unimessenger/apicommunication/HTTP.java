package unimessenger.apicommunication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTP
{
    static HttpClient client = HttpClient.newHttpClient();

    public static void sendRequest(String url, REQUESTTYPE type, String body, String... headers)
    {
        HttpRequest request = null;
        HttpResponse<String> response = null;

        switch(type)
        {
            case GET:
                request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(url)).build();
                break;
            case PUT:
                //TODO: Add PUT request
                break;
            case POST:
                request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(body)).header(headers[0], headers[1]).header(headers[2], headers[3]).uri(URI.create(url)).build();
                break;
            default:
                break;
        }

        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch(Exception ignored)
        {
        }
        assert response != null;
        System.out.println(response.body());
    }

    public enum REQUESTTYPE
    {
        GET,
        POST,
        PUT
    }
}