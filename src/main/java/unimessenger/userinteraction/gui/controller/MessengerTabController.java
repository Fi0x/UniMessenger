package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import unimessenger.userinteraction.gui.MainWindow;

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
    }
}
