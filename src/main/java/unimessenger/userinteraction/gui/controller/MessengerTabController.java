package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MessengerTabController
{
    @FXML
    private Tab tab;
    @FXML
    private AnchorPane anchor;
    @FXML
    private HBox messengerList;

    @FXML
    private void wire()
    {
        tab.setText("Wire");

        while(anchor.getChildren().size() > 0)
        {
            anchor.getChildren().remove(0);
        }
    }

    @FXML
    private void telegram()
    {
        tab.setText("Telegram");
        while(anchor.getChildren().size() > 0)
        {
            anchor.getChildren().remove(0);
        }
    }
}
