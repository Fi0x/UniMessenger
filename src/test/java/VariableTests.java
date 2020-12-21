import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;

public class VariableTests
{
    @Test
    void URLValues()
    {
        Assertions.assertEquals("https://prod-nginz-https.wire.com", URL.WIRE);
        Assertions.assertEquals("/login", URL.WIRE_LOGIN);
        Assertions.assertEquals("?persist=true", URL.WIRE_PERSIST);
        Assertions.assertEquals("/access", URL.WIRE_ACCESS);
        Assertions.assertEquals("/access/logout", URL.WIRE_LOGOUT);
        Assertions.assertEquals("/prekeys", URL.WIRE_PREKEY);
        Assertions.assertEquals("/self", URL.WIRE_SELF);
        Assertions.assertEquals("/users", URL.WIRE_USERS);
        Assertions.assertEquals("/clients", URL.WIRE_CLIENTS);
        Assertions.assertEquals("/conversations", URL.WIRE_CONVERSATIONS);
        Assertions.assertEquals("/otr/messages", URL.WIRE_OTR_MESSAGES);
        Assertions.assertEquals("/notifications", URL.WIRE_NOTIFICATIONS);
        Assertions.assertEquals("/assets/v3", URL.WIRE_ASSETS);
    }
    @Test
    void URLBearer()
    {
        String bearerParameter = URL.wireBearerToken();
        String[] parts = bearerParameter.split("=");
        Assertions.assertEquals("?access_token", parts[0]);
    }

    @Test
    void HeadersValues()
    {
        Assertions.assertEquals("accept", Headers.ACCEPT);
        Assertions.assertEquals("content-type", Headers.CONTENT);
        Assertions.assertEquals("application/json", Headers.JSON);
        Assertions.assertEquals("multipart/mixed", Headers.MIXED);
    }
}
