package unimessenger.apicommunication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTP
{
    private static final String API_URL_EXAMPLE = "https://jsonplaceholder.typicode.com/todos/1";

    public static void sendGetRequest(String url)
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(url)).build();

        HttpResponse<String> response = null;
        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch(Exception ignored)
        {
        }
        assert response != null;
        System.out.println(response.body());
    }
}