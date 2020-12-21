import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unimessenger.userinteraction.gui.MainWindow;

public class MainWindowTests
{
    @Test
    void checkInstance()
    {
        Assertions.assertEquals(MainWindow.getInstance(), MainWindow.getInstance());
    }
}
