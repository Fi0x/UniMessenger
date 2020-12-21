package unimessenger.userinteraction.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import unimessenger.userinteraction.gui.MainWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TabPane tpMain;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        MainWindow mw = MainWindow.getInstance();
        tpMain.setPrefWidth(mw.getWindowWidth());
        tpMain.setPrefHeight(mw.getWindowHeight());
        mw.resize();
    }
}