package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.api.IMessages;
import unimessenger.abstraction.interfaces.storage.IData;
import unimessenger.abstraction.interfaces.storage.IMessage;
import unimessenger.abstraction.wire.api.WireMessageSender;
import unimessenger.abstraction.wire.messages.MessageCreator;
import unimessenger.userinteraction.gui.MainWindow;
import unimessenger.util.enums.SERVICE;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConversationController implements Initializable
{
    private TabController tabController;
    private MessengerController messengerController;
    private boolean timed = false;

    @FXML
    private Label lblConversationName;
    @FXML
    private ScrollPane messageHistory;
    @FXML
    private VBox chatHistory;
    @FXML
    private TextField txtMessage;
    @FXML
    private Button btnSendMsg;
    @FXML
    private MenuItem temporaryMessage;
    @FXML
    private MenuItem pingMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        messageHistory.setMaxHeight(200);
        MainWindow.getInstance().resize();
    }

    @FXML
    private void sendText()
    {
        btnSendMsg.setDisable(true);

        if(!txtMessage.getText().isEmpty())
        {
            APIAccess access = new APIAccess();
            IMessages msgSender = access.getMessageInterface(tabController.getService());
            if(msgSender != null)
            {
                if(timed)
                {
                    if(msgSender.sendTimedText(messengerController.getCurrentChatID(), txtMessage.getText(), 300 * 1000))
                    {
                        ArrayList<IMessage> sentMsg = access.getDataInterface(tabController.getService()).getLastXMessagesFromConversation(messengerController.getCurrentChatID(), 1);
                        if(!sentMsg.isEmpty()) addChatMessage(sentMsg.get(0));
                    }
                } else
                {
                    if(msgSender.sendTextMessage(messengerController.getCurrentChatID(), txtMessage.getText()))
                    {
                        ArrayList<IMessage> sentMsg = access.getDataInterface(tabController.getService()).getLastXMessagesFromConversation(messengerController.getCurrentChatID(), 1);
                        if(!sentMsg.isEmpty()) addChatMessage(sentMsg.get(0));
                    }
                }
            }
        }

        txtMessage.clear();
        btnSendMsg.setDisable(false);
    }
    @FXML
    private void timed()
    {
        timed = !timed;

        if(timed)
        {
            txtMessage.setPromptText("Send a timed message");
            temporaryMessage.setText("Permanent Message");
        } else
        {
            txtMessage.setPromptText("Write a message");
            temporaryMessage.setText("Temporary Message");
        }
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

        loadHistory();

        if(tabController.getService() == SERVICE.WIRE)
        {
            temporaryMessage.setDisable(false);
            pingMessage.setDisable(false);
        }
    }

    private void loadHistory()
    {
        IData data = new APIAccess().getDataInterface(tabController.getService());
        if(data == null) return;
        ArrayList<IMessage> messages = data.getAllMessagesFromConversation(messengerController.getCurrentChatID());

        if(messages == null) return;

        for(IMessage message : messages)
        {
            addChatMessage(message);
        }
    }

    private void addChatMessage(IMessage message)
    {
        HBox messageBox = new HBox();

        String msgText = message.getText();
        String msgTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(message.getTime());
        String msgSender = message.getSender();

        Label text = new Label(msgText);
        Label time = new Label(msgTime);
        Label sender = new Label(msgSender);

        text.setId("messageText");
        time.setId("messageTime");
        sender.setId("messageSender");

        messageBox.getChildren().add(time);
        messageBox.getChildren().add(sender);
        messageBox.getChildren().add(text);

        messageBox.setId("displayedMessage");

        chatHistory.getChildren().add(messageBox);
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
