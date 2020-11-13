package unimessenger.apicommunication;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class HTTP
{
    public static void sendRequest() throws URISyntaxException
    {
        HttpRequest.newBuilder().uri(new URI("https://postman-echo.com/get"));
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://postman-echo.com/get")).version(HttpClient.Version.HTTP_2).GET().build();
    }
}