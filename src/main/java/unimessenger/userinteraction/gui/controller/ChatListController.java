package unimessenger.userinteraction.gui.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatListController implements Initializable
{
    private MessengerTabController tabController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    public void setTabController(MessengerTabController controller)
    {
        tabController = controller;
    }
}
