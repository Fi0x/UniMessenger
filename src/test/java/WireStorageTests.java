import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.storage.WireStorage;

public class WireStorageTests
{
    @Test
    void bearerToken()
    {
        WireStorage.setBearerToken("A", 60);
        Assertions.assertEquals("A", WireStorage.getBearerToken());
        Assertions.assertTrue(WireStorage.isBearerTokenStillValid());
    }
}
