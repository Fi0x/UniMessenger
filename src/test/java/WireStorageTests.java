import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.storage.Storage;

public class WireStorageTests
{
    @Test
    void bearerToken()
    {
        Storage.getInstance().setBearerToken("A", 60);
        Assertions.assertEquals("A", Storage.getInstance().getBearerToken());
        Assertions.assertTrue(Storage.getInstance().isBearerTokenStillValid());
    }
}
