package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import unimessenger.userinteraction.tui.Outputs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerController implements Initializable
{
    private MessengerTabController tabController;

    @FXML
    public AnchorPane conversationAnchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    public void loadChats()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chatList.fxml"));
        ScrollPane pane;

        try
        {
            pane = loader.load();
            ChatListController controller = loader.getController();
            controller.setTabController(tabController);
            controller.loadChats(tabController.getService());
        } catch(IOException ignored)
        {
            Outputs.create("Error loading chat list").debug().WARNING().print();
            return;
        }

        conversationAnchor.getChildren().add(pane);
    }

    public void setTabController(MessengerTabController controller)
    {
        tabController = controller;
    }
}
