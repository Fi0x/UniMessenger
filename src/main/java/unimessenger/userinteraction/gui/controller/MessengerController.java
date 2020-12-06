package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import unimessenger.userinteraction.tui.Outputs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerController implements Initializable
{
    private TabController tabController;
    private String currentChatID;

    @FXML
    private AnchorPane conversationListAnchor;
    @FXML
    private AnchorPane conversationAnchor;

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
            controller.setMessengerController(this);

            controller.loadChats(tabController.getService());
        } catch(IOException ignored)
        {
            Outputs.create("Error loading chat list").debug().WARNING().print();
            return;
        }

        conversationListAnchor.getChildren().add(pane);
    }

    public void setTabController(TabController controller)
    {
        tabController = controller;
    }
    public void openChat(String chatID)
    {
        currentChatID = chatID;
        while(conversationAnchor.getChildren().size() > 0)
        {
            conversationAnchor.getChildren().remove(0);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/conversation.fxml"));
        VBox box;

        try
        {
            box = loader.load();

            ConversationController controller = loader.getController();
            controller.setTabController(tabController);
            controller.setMessengerController(this);
        } catch(IOException ignored)
        {
            Outputs.create("Chat could not be loaded", this.getClass().getName()).debug().WARNING().print();
            return;
        }

        conversationAnchor.getChildren().add(box);
    }
    public String getCurrentChatID()
    {
        return currentChatID;
    }
}
