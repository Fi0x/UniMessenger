import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.storage.ConversationHandler;

public class ConversationHandlerTests
{
    @Test
    void instance()
    {
        Assertions.assertEquals(ConversationHandler.getInstance(), ConversationHandler.getInstance());
    }
}
