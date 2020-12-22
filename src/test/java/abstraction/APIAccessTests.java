package abstraction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.api.IConversations;
import unimessenger.abstraction.interfaces.api.ILoginOut;
import unimessenger.abstraction.interfaces.api.IMessages;
import unimessenger.abstraction.interfaces.api.IUtil;
import unimessenger.abstraction.interfaces.storage.IData;
import unimessenger.abstraction.wire.api.*;
import unimessenger.util.enums.SERVICE;

public class APIAccessTests
{
    @Test
    void conversationInterface()
    {
        IConversations conI = new APIAccess().getConversationInterface(SERVICE.NONE);
        Assertions.assertNull(conI);

        conI = new APIAccess().getConversationInterface(SERVICE.TELEGRAM);
        Assertions.assertNull(conI);

        conI = new APIAccess().getConversationInterface(SERVICE.WIRE);
        Assertions.assertEquals(WireConversations.class, conI.getClass());
    }
    @Test
    void loginInterface()
    {
        ILoginOut loginI = new APIAccess().getLoginInterface(SERVICE.NONE);
        Assertions.assertNull(loginI);

        loginI = new APIAccess().getLoginInterface(SERVICE.TELEGRAM);
        Assertions.assertNull(loginI);

        loginI = new APIAccess().getLoginInterface(SERVICE.WIRE);
        Assertions.assertEquals(WireLogin.class, loginI.getClass());
    }
    @Test
    void messageInterface()
    {
        IMessages mesI = new APIAccess().getMessageInterface(SERVICE.NONE);
        Assertions.assertNull(mesI);

        mesI = new APIAccess().getMessageInterface(SERVICE.TELEGRAM);
        Assertions.assertNull(mesI);

        mesI = new APIAccess().getMessageInterface(SERVICE.WIRE);
        Assertions.assertEquals(WireMessages.class, mesI.getClass());
    }
    @Test
    void utilInterface()
    {
        IUtil utilI = new APIAccess().getUtilInterface(SERVICE.NONE);
        Assertions.assertNull(utilI);

        utilI = new APIAccess().getUtilInterface(SERVICE.TELEGRAM);
        Assertions.assertNull(utilI);

        utilI = new APIAccess().getUtilInterface(SERVICE.WIRE);
        Assertions.assertEquals(WireUtil.class, utilI.getClass());
    }
    @Test
    void dataInterface()
    {
        IData dataI = new APIAccess().getDataInterface(SERVICE.NONE);
        Assertions.assertNull(dataI);

        dataI = new APIAccess().getDataInterface(SERVICE.TELEGRAM);
        Assertions.assertNull(dataI);

        dataI = new APIAccess().getDataInterface(SERVICE.WIRE);
        Assertions.assertEquals(WireData.class, dataI.getClass());
    }
}
