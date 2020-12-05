package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import unimessenger.userinteraction.gui.MainWindow;
import unimessenger.userinteraction.tui.Outputs;

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

        loadLogin();
    }
    @FXML
    private void telegram()
    {
        tab.setText("Telegram");

        loadLogin();
    }

    private void loadLogin()
    {
        while(anchor.getChildren().size() > 0)
        {
            anchor.getChildren().remove(0);
        }

        MainWindow.getInstance().addMessengerTab();

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
}
