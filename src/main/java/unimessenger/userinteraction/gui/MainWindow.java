package unimessenger.userinteraction.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import unimessenger.Main;
import unimessenger.userinteraction.tui.Outputs;

public class MainWindow extends Application
{
    private FXMLLoader mainLoader;
    private FXMLLoader tabLoader;

    @Override
    public void start(Stage primaryStage)
    {
        mainLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        tabLoader = new FXMLLoader(getClass().getResource("/fxml/messengerTab.fxml"));
        Parent root;
        Tab messengerTab;
        try
        {
            root = mainLoader.load();
            messengerTab = tabLoader.load();
        } catch(Exception ignored)
        {
            Outputs.create("Could not load GUI. Shutting down").always().WARNING().print();
            Main.stp.start();
            return;
        }

        messengerTab.setText("New Messenger");
        addMessengerTab(messengerTab);

        primaryStage.setTitle("UniMessenger");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void addMessengerTab(Tab tab)
    {
        TabPane pane = (TabPane) mainLoader.getNamespace().get("tpMain");
        pane.getTabs().add(tab);
    }

    @Override
    public void stop()
    {
        Outputs.create("GUI closed. Shutting down").always().WARNING().print();
        Main.stp.start();
    }

    public void runGUI()
    {
        launch();
    }
}
