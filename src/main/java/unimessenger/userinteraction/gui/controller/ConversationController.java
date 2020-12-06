package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.IData;
import unimessenger.abstraction.interfaces.IMessages;
import unimessenger.abstraction.interfaces.wire.WireMessageSender;
import unimessenger.abstraction.wire.crypto.MessageCreator;
import unimessenger.util.enums.SERVICE;

public class ConversationController
{
    private TabController tabController;
    private MessengerController messengerController;

    @FXML
    private Label lblConversationName;
    @FXML
    private TextField txtMessage;
    @FXML
    private Button btnSendMsg;
    @FXML
    private MenuItem temporaryMessage;
    @FXML
    private MenuItem pingMessage;

    @FXML
    private void sendText()
    {
        btnSendMsg.setDisable(true);

        if(!txtMessage.getText().isEmpty())
        {
            IMessages msgSender = new APIAccess().getMessageInterface(tabController.getService());
            if(msgSender != null) msgSender.sendTextMessage(messengerController.getCurrentChatID(), txtMessage.getText());
        }

        txtMessage.clear();
        btnSendMsg.setDisable(false);
    }
    @FXML
    private void ping()
    {
        new WireMessageSender().sendMessage(messengerController.getCurrentChatID(), MessageCreator.createGenericPingMessage());
    }

    public void load()
    {
        IData data = new APIAccess().getDataInterface(tabController.getService());
        lblConversationName.setText(data.getConversationNameFromID(messengerController.getCurrentChatID()));

        if(tabController.getService() == SERVICE.WIRE)
        {
            temporaryMessage.setDisable(false);
            pingMessage.setDisable(false);
        }
    }

    public void setTabController(TabController controller)
    {
        tabController = controller;
    }
    public void setMessengerController(MessengerController controller)
    {
        messengerController = controller;
    }
}
