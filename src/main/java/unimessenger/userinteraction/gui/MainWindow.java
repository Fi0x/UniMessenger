package unimessenger.userinteraction.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import unimessenger.Main;
import unimessenger.userinteraction.tui.Out;

import java.io.IOException;

public class MainWindow extends Application
{
    private static MainWindow instance;
    private FXMLLoader mainLoader;
    private Stage stage;

    public MainWindow()
    {
        instance = this;
    }
    public static MainWindow getInstance()
    {
        if(instance == null) instance = new MainWindow();
        return instance;
    }

    @Override
    public void start(Stage primaryStage)
    {
        stage = primaryStage;
        stage.setWidth(960);
        stage.setHeight(540);
        mainLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));

        Parent root;
        try
        {
            root = mainLoader.load();
        } catch(Exception ignored)
        {
            Out.newBuilder("Could not load GUI. Shutting down").a().WARNING().print();
            Main.stp.start();
            return;
        }

        addMessengerTab();

        primaryStage.setTitle("UniMessenger");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        resize();
    }
    @Override
    public void stop()
    {
        Out.newBuilder("GUI closed. Shutting down").a().WARNING().print();
        Main.stp.start();
    }

    public void addMessengerTab()
    {
        FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("/fxml/tab.fxml"));
        Tab messengerTab;

        try
        {
            messengerTab = tabLoader.load();
        } catch(IOException ignored)
        {
            Out.newBuilder("Error loading Tab").d().WARNING().print();
            return;
        }
        messengerTab.setText("New Messenger");
        messengerTab.setClosable(false);

        TabPane pane = (TabPane) mainLoader.getNamespace().get("tpMain");
        pane.getTabs().add(messengerTab);
    }

    public void resize()
    {
        //TODO: Adjust size of all elements to current size
    }
    public double getWindowWidth()
    {
        return stage.getWidth();
    }
    public double getWindowHeight()
    {
        return stage.getHeight() - 20;
    }
}
