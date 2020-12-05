package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import unimessenger.abstraction.APIAccess;
import unimessenger.userinteraction.gui.MainWindow;
import unimessenger.userinteraction.tui.Outputs;
import unimessenger.util.enums.SERVICE;

import java.io.IOException;

public class MessengerTabController
{
    @FXML
    private Tab tab;
    @FXML
    private AnchorPane anchor;

    @FXML
    private void wire()
    {
        tab.setText("Wire");
        while(anchor.getChildren().size() > 0)
        {
            anchor.getChildren().remove(0);
        }

        MainWindow.getInstance().addMessengerTab();

        APIAccess access = new APIAccess();
        if(access.getLoginInterface(SERVICE.WIRE).checkIfLoggedIn())
        {
//            if(!access.getUtilInterface(CLI.currentService).loadProfile()) Outputs.create("Could not load profile", "MenuLogin").verbose().debug().ERROR().print();
            loadMessenger();
        } else loadLogin();
    }
    @FXML
    private void telegram()
    {
        tab.setText("Telegram");
        while(anchor.getChildren().size() > 0)
        {
            anchor.getChildren().remove(0);
        }

        MainWindow.getInstance().addMessengerTab();

        //TODO: Add Telegram stuff
        loadLogin();
    }

    private void loadLogin()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        VBox login;
        try
        {
            login = loader.load();
        } catch(IOException ignored)
        {
            Outputs.create("Error loading login menu").debug().WARNING().print();
            return;
        }
        anchor.getChildren().add(login);
    }

    private void loadMessenger()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/messenger.fxml"));
        HBox box;

        try
        {
            box = loader.load();
        } catch(IOException ignored)
        {
            Outputs.create("Error loading messenger").debug().WARNING().print();
            return;
        }

        anchor.getChildren().add(box);
    }
}
