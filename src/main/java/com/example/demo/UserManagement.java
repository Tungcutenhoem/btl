package com.example.demo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import Controller.SceneApp;
import com.example.demo.DictionaryManagement;
import com.example.demo.Word;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

//import static Controller.SceneApp.data;


public class UserManagement  {
    private static Map<String, Word> UserSaveWord = new HashMap();
    private static final String SPLITTING_CHARACTERS = "<html>";
    private String WordAdd="stibium";;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;
    DictionaryManagement dictionaryManagement = new DictionaryManagement();
    SceneApp sceneApp = new SceneApp();
    public String getSaveWordUser(String wordUser) {
        return wordUser;
    }

    public UserManagement() {
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
