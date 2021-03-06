package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import unimessenger.abstraction.APIAccess;
import unimessenger.userinteraction.gui.MainWindow;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.Updater;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerController implements Initializable
{
    private TabController tabController;
    private String currentChatID;

    @FXML
    private VBox messengerOverview;
    @FXML
    private TabPane messengerCategories;
    @FXML
    private AnchorPane conversationListAnchor;
    @FXML
    private AnchorPane conversationAnchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        MainWindow mw = MainWindow.getInstance();
        messengerOverview.setPrefHeight(mw.getWindowHeight());
        mw.resize();
    }

    @FXML
    private void logout()
    {
        new APIAccess().getLoginInterface(tabController.getService()).logout();
        Updater.removeService(tabController.getService());
        tabController.closeTab();
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
            Out.newBuilder("Error loading chat list").d().WARNING().print();
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

            controller.load();
        } catch(IOException ignored)
        {
            Out.newBuilder("Chat could not be loaded").origin(this.getClass().getName()).d().WARNING().print();
            return;
        }

        conversationAnchor.getChildren().add(box);
    }
    public String getCurrentChatID()
    {
        return currentChatID;
    }
}
