package Controller;

import Model.TextToSpeech;
import com.example.demo.DictionaryManagement;

import com.example.demo.Word;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.*;
import java.util.*;

import static Controller.App.*;
import static Controller.SceneUser.*;
import static com.example.demo.DictionaryManagement.keySearch;
import static com.example.demo.DictionaryManagement.wordShow;
import static javafx.application.Application.launch;

public class SceneApp {
    private Scene scene;
    @FXML
    private TextField SearchWord;
    public static List<String> dictionaryE_V = new ArrayList<>();
    public static List<String> dictionaryV_E = new ArrayList<>();
    private static final String SPLITTING_CHARACTERS = "<html>";
    public static Map<String, Word> dataE_V = new HashMap<>();
    public static Map<String, Word> dataV_E = new HashMap<>();
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;
    private javafx.scene.web.WebView WebView;
    private boolean initialized = false;

    @FXML
    private void initialize() {
        if (!initialized) {
            loadData();
            initialized = true;
        }
        else
        {
            reload();
        }
    }

    private void loadData() {
        if (dictionaryCurent.isEmpty()) {
            readData(DATA_FILE_PATHE_V, dataE_V, dictionaryE_V);
            DATA_FILE_PATH_USER_CURENT = DATA_FILE_PATH_User_E_V;
        } else {
            readData(DATA_FILE_PATH_CURENT, dataCurent, dictionaryCurent);
        }
        showData();
        initComponents(dataCurent);
    }

    private void showData() {
        ObservableList<String> allWords = FXCollections.observableArrayList(dictionaryCurent);
        listView.setItems(allWords);

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        Word selectedWord = dataCurent.get(newValue1.trim());
                        String definition = selectedWord.getWord_explain();
                        this.definitionView.getEngine().loadContent(definition, "text/html");
                    }
                }
        );

        SearchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            String input = newValue.toLowerCase();
            ObservableList<String> filteredList = FXCollections.observableArrayList();

            if (input.isEmpty()) {
                listView.setItems(allWords);
            } else {
                for (String word : dictionaryCurent) {
                    if (word.toLowerCase().startsWith(input)) {
                        filteredList.add(word);
                    }
                }
                listView.setItems(filteredList);
            }
        });
    }

    private void initComponents(Map<String, Word> data) {
        SceneApp context = this;
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = data.get(newValue.trim());
                    String definition = selectedWord.getWord_explain();
                    context.definitionView.getEngine().loadContent(definition, "text/html");
                }
        );
    }


    public void loadWordList(Map<String, Word> data) {

        this.listView.getItems().addAll(data.keySet());
    }

    public void readData(String DATA_FILE_PATH, Map<String, Word> data, List<String> dictionary) {
        data.clear();
        dictionary.clear();
        FileReader fis = null;
        DATA_FILE_PATH_CURENT = DATA_FILE_PATH;
        dataCurent = data;
        dictionaryCurent = dictionary;
        try {
            fis = new FileReader(DATA_FILE_PATH);

            BufferedReader br = new BufferedReader(fis);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(SPLITTING_CHARACTERS);
                String word = parts[0];
                String definition = SPLITTING_CHARACTERS + parts[1];
                Word wordObj = new Word(word, definition);
                data.put(word, wordObj);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Arraylis(data, dictionary);
    }

    public void Arraylis(Map<String, Word> data, List<String> dictionary) {
        for (String key : data.keySet()) {
            dictionary.add(key);
        }
    }

    @FXML
    public void setonMouseClickedTexField() {
        ShowWordTextFiled(dataCurent);
    }

    public void ShowWordTextFiled(Map<String, Word> data) {
        listView.setOnMouseClicked(event -> {
            // Lấy từ được chọn
            String selectedWord = listView.getSelectionModel().getSelectedItem();
            // Gắn từ được chọn vào TextField
//            SearchWord.setText(selectedWord);
            if (selectedWord != null) {
                Word word = data.get(selectedWord);
                if (word != null) {
                    String definition = word.getWord_explain();
                    definitionView.getEngine().loadContent(definition, "text/html");

                }
            }
        });
    }

    @FXML
    public void handleSaveButtonAction(ActionEvent event) {
        FileReader fis = null;
        try {
            if (listView != null) {
                String selectedWord = listView.getSelectionModel().getSelectedItem();
                fis = new FileReader(DATA_FILE_PATH_USER_CURENT);
                BufferedReader rd = new BufferedReader(fis);
                String line;
                boolean check = true;
                while ((line = rd.readLine()) != null) {
                    String[] parts = line.split(SPLITTING_CHARACTERS);
                    String word = parts[0];
                    if (word.equals(selectedWord)) {
                        check = false;
                    }
                }
                if (check == true) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Add word notification");
                    alert.setHeaderText("Do you want to save this word");
                    ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                    //ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeYes) {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.initModality(Modality.APPLICATION_MODAL);
                        alert1.setContentText("This word is saved Successfully");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
                        if (DATA_FILE_PATH_CURENT.equals(DATA_FILE_PATHE_V)) {
                            UserAddWord(DATA_FILE_PATHE_V, DATA_FILE_PATH_USER_CURENT, dataE_V, dictionaryE_V, UserSaveWordE_V);
                        } else {
                            UserAddWord(DATA_FILE_PATHV_E, DATA_FILE_PATH_USER_CURENT, dataV_E, dictionaryV_E, UserSaveWordV_E);
                        }
                        reload();
                    }
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.initModality(Modality.APPLICATION_MODAL);
                    alert1.setContentText("This word has been saved before");
                    alert1.setHeaderText("");
                    alert1.showAndWait();
                    ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
                    check = true;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UserAddWord(String DATA_FILE_PATH, String DATA_FILE_PATH_USer, Map<String, Word> data, List<String> dictionary, Map<String, Word> UserSaveWord) {
        SceneUser sceneUser = new SceneUser();
        if (listView != null) {
            String selectedWord = listView.getSelectionModel().getSelectedItem();
            if (selectedWord != null && !selectedWord.isEmpty()) {
                // Thêm từ đã chọn vào danh sách từ đã chọn
                sceneUser.writeUserSaveWord(selectedWord, DATA_FILE_PATH_USer, dictionary, data, DATA_FILE_PATH, UserSaveWord);
            }
        }
    }

    @FXML
    void handleButtonLoud(ActionEvent actionEvent) {
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        try {
            if (DATA_FILE_PATH_CURENT.equals(DATA_FILE_PATHV_E)) {
                TextToSpeech.ttsTextFiled(selectedWord, "vi-vn", "Vietnamese");
            } else {
                TextToSpeech.ttsTextFiled(selectedWord, "en-us", "English (US)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonTranslate(ActionEvent actionEvent) {
        if (DATA_FILE_PATH_CURENT.equals(DATA_FILE_PATHE_V)) {
            TranslateDictionary(DATA_FILE_PATHE_V);
        } else {
            TranslateDictionary(DATA_FILE_PATHV_E);
        }
        reload();
    }

    public void TranslateDictionary(String DATA_FILE_PATH) {
        if (DATA_FILE_PATH.equals("C:\\tudienn\\src\\main\\java\\Transition\\E_V.txt")) {
            String languagecurent = "en-vi";
            listView.getItems().clear();
            definitionView.getEngine().loadContent("");
            SearchWord.setText("");
            readData(DATA_FILE_PATHV_E, dataV_E, dictionaryV_E);
            loadWordList(dataV_E);
            showData();
            initComponents(dataV_E);
            DATA_FILE_PATH_USER_CURENT = DATA_FILE_PATH_User_V_E;
            UserDataCurent = UserSaveWordV_E;
            dictionaryUserCurent = dictionary_User_V_E;
        } else {
            String languagecurent = "vi-en";
            listView.getItems().clear();
            definitionView.getEngine().loadContent("");
            SearchWord.setText("");
            readData(DATA_FILE_PATHE_V, dataE_V, dictionaryE_V);
            loadWordList(dataE_V);
            showData();
            initComponents(dataE_V);
            DATA_FILE_PATH_USER_CURENT = DATA_FILE_PATH_User_E_V;
            UserDataCurent = UserSaveWordE_V;
            dictionaryUserCurent = dictionary_User_E_V;
        }
    }

    public void reload() {
        dictionaryCurent = new ArrayList<>();
        dataCurent = new HashMap<>();
        listView.getItems().clear();
        definitionView.getEngine().loadContent("");
        readData(DATA_FILE_PATH_CURENT, dataCurent, dictionaryCurent);
        loadWordList(dataCurent);
        showData();
        initComponents(dataCurent);
    }


}