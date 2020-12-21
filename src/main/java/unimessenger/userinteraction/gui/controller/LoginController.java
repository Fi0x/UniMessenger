package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import unimessenger.abstraction.APIAccess;
import unimessenger.abstraction.interfaces.api.ILoginOut;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.userinteraction.gui.MainWindow;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.Updater;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    private TabController tabController;

    @FXML
    private VBox loginContainer;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtPW;
    @FXML
    private CheckBox cbPermanent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        MainWindow mw = MainWindow.getInstance();
        loginContainer.setPrefWidth(mw.getWindowWidth());
        loginContainer.setPrefHeight(mw.getWindowHeight());
        txtMail.setPrefWidth(mw.getWindowWidth() / 2);
        txtPW.setPrefWidth(mw.getWindowWidth() / 2);
        MainWindow.getInstance().resize();
    }

    @FXML
    private void login()
    {
        String mail = txtMail.getText();
        String pw = txtPW.getText();
        if(cbPermanent.isSelected()) WireStorage.persistent = true;

        ILoginOut login = new APIAccess().getLoginInterface(tabController.getService());
        if(login == null)
        {
            Out.newBuilder("Unable to obtain login interface").origin(this.getClass().getName()).d().WARNING().print();
            return;
        }

        if(login.login(mail, pw))
        {
            Updater.addService(tabController.getService());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/messenger.fxml"));
            HBox box;

            try
            {
                box = loader.load();
                MessengerController controller = loader.getController();
                controller.setTabController(tabController);
                controller.loadChats();

                tabController.clearTab();
                tabController.addToTab(box);
            } catch(IOException ignored)
            {
                Out.newBuilder("Error loading messenger").origin(this.getClass().getName()).d().WARNING().print();
            }
        }
    }

    @FXML
    private void cancel()
    {
        tabController.closeTab();
    }

    public void setTabController(TabController controller)
    {
        tabController = controller;
    }
}
