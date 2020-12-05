package unimessenger.userinteraction.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unimessenger.Main;
import unimessenger.userinteraction.tui.Outputs;

public class MainWindow extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Parent root;
        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        } catch(Exception ignored)
        {
            Outputs.create("Could not load GUI. Shutting down").always().WARNING().print();
            Main.stp.start();
            return;
        }

        primaryStage.setTitle("UniMessenger");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
