package abstraction.wire.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.abstraction.wire.storage.Profile;
import unimessenger.abstraction.wire.storage.User;

public class WireProfileTests
{
    Profile p = new Profile();
    User u = new User();

    @Test
    void testMail()
    {
        Assertions.assertNull(p.getMail());
        p.setMail("Mail");
        Assertions.assertEquals("Mail", p.getMail());
    }
    @Test
    void testPhone()
    {
        Assertions.assertNull(p.getPhone());
        p.setPhone("Phone");
        Assertions.assertEquals("Phone", p.getPhone());
    }
    @Test
    void testPW()
    {
        Assertions.assertNull(p.getPW());
        p.setPW("PW");
        Assertions.assertEquals("PW", p.getPW());
    }
    @Test
    void testUserName()
    {
        Assertions.assertNull(p.getUsername());
        p.setUsername("Username");
        Assertions.assertEquals("Username", p.getUsername());
    }
    @Test
    void testID()
    {
        Assertions.assertNull(p.getUserID());
        p.setUserID("ID");
        Assertions.assertEquals("ID", p.getUserID());
    }

    @Test
    void userID()
    {
        Assertions.assertNull(u.getUserID());
        u.setUserID("ID");
        Assertions.assertEquals("ID", u.getUserID());
    }
    @Test
    void userName()
    {
        Assertions.assertNull(u.getUserName());
        u.setUserName("Name");
        Assertions.assertEquals("Name", u.getUserName());
    }
}
