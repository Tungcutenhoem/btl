package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

import static Controller.SceneApp.dataE_V;


public class DictionaryManagement {
    public static String keySearch = "";
    public static Map<String, Word> wordShow = new HashMap<>();

    private static final String DATA_FILE_PATH = "C:\\Transition\\E_V.txt";
    //private static final String FXML_FILE_PATH = "./src/main/resources/com/example/dictionary/dictionary-view.fxml";
    private static final String SPLITTING_CHARACTERS = "<html>";

    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;
    private ArrayList<Word> new_world;
    private Scene scene;

    public static void main(String[] args) throws IOException {
        Application.launch(args);

    }

//    public void loadWordList() {
//        this.listView.getItems().addAll(data.keySet());
//    }


    public void setNew_world(ArrayList<Word> newWorld) {
        this.new_world = newWorld;
    }

    public ArrayList<Word> getNew_world() {
        return this.new_world;
    }


//    public Word dictionaryLookup(String text) {
//        Word word = new Word(data.get(text).getWord_target(), data.get(text).getWord_explain());
//        return word;
//    }



}

