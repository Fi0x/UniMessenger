import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.communication.RequestBuilder;

import java.net.URI;
import java.net.http.HttpRequest;

public class RequestBuilderTests
{
    @Test
    void getRequest()
    {
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpRequest request = RequestBuilder.getGETRequest(URL.WIRE, headers);
        Assertions.assertEquals(URI.create(URL.WIRE), request.uri());
        Assertions.assertEquals("GET", request.method());
    }
    @Test
    void putRequest()
    {
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpRequest request = RequestBuilder.getPUTRequest(URL.WIRE, "", headers);
        Assertions.assertEquals(URI.create(URL.WIRE), request.uri());
        Assertions.assertEquals("PUT", request.method());
    }
    @Test
    void postRequest()
    {
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpRequest request = RequestBuilder.getPOSTRequest(URL.WIRE, "", headers);
        Assertions.assertEquals(URI.create(URL.WIRE), request.uri());
        Assertions.assertEquals("POST", request.method());
    }
    @Test
    void deleteRequest()
    {
        String[] headers = new String[]{
                Headers.ACCEPT, Headers.JSON};
        HttpRequest request = RequestBuilder.getDELETERequest(URL.WIRE, "", headers);
        Assertions.assertEquals(URI.create(URL.WIRE), request.uri());
        Assertions.assertEquals("DELETE", request.method());
    }
}
