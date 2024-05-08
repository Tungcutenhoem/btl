package Controller;

import Game.Main;
import Model.TextToSpeech;
import com.example.demo.Word;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static Controller.App.*;
import static Controller.App.dictionaryCurent;
import static Controller.SceneApp.*;
import static javafx.application.Application.launch;


public class SceneUser {
    private File filecurnt ;
    MainCommant mainCommant = new MainCommant();
    public static Map<String, Word> UserSaveWordE_V = new HashMap<>();
    public static Map<String, Word> UserSaveWordV_E = new HashMap<>();
    public static final String DATA_FILE_PATH_User_E_V = "C:\\tudienn\\src\\main\\java\\Transition\\UserSaveWord.txt";
    public static final String DATA_FILE_PATH_User_V_E = "C:\\tudienn\\src\\main\\java\\Transition\\UserSaveWordV_E.txt";

    private static final String SPLITTING_CHARACTERS = "<html>";
    protected static List<String> dictionary_User_V_E = new ArrayList<>();
    protected static List<String> dictionary_User_E_V = new ArrayList<>();
    @FXML
    private ListView<String> listView;
    private SceneApp sceneApp = new SceneApp();

    @FXML
    private WebView definitionView;
    @FXML
    private TextField SearchWord;
    private boolean initialized = false;
    public String getSaveWordUser(String wordUser) {
        return wordUser;
    }

    public void writeUserSaveWord(String s, String DATA_FILE_PATH_Write, List<String> dictionary, Map<String, Word> data, String DATA_FILE_PATH, Map<String, Word> UserSaveWord) {
        try {
            sceneApp.readData(DATA_FILE_PATH, data, dictionary);
            FileWriter fis = new FileWriter(DATA_FILE_PATH_Write, true);
            BufferedWriter br = new BufferedWriter(fis);
            for (String key : data.keySet()) {
                if (s.toLowerCase().equals(key.toLowerCase()) && !UserSaveWord.containsKey(s.toLowerCase())) {
                    Word word = data.get(s.toLowerCase());
                    br.write(String.valueOf(data.get(s).getWord_target()));
                    br.write(String.valueOf(data.get(key).getWord_explain()));
                    br.newLine();
                    UserSaveWord.put(key, (Word) data.get(s));
                    break;
                }
            }
            br.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadWordList(Map<String, Word> data) {
        this.listView.getItems().addAll(data.keySet());
    }

    @FXML
    private void initialize() {
        if (!initialized) {
            loadData();
            initialized = true;
        }
    }

    private void loadData() {
        readData(DATA_FILE_PATH_USER_CURENT, UserDataCurent, dictionaryUserCurent);
        showData();
        initComponents(UserDataCurent);
    }

    private void showData() {
        ObservableList<String> allWords = FXCollections.observableArrayList(dictionaryUserCurent);
        listView.setItems(allWords);

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        Word selectedWord = UserDataCurent.get(newValue1.trim());
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
                for (String word : dictionaryUserCurent) {
                    if (word.toLowerCase().startsWith(input)) {
                        filteredList.add(word);
                    }
                }
                listView.setItems(filteredList);
            }
        });
    }
    public void initComponents(Map<String, Word> data) {
//        this.definitionView = (WebView) scene.lookup("#definitionView_0");
//        this.listView = (ListView<String>) scene.lookup("#listView_0");
        SceneUser context = this;
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = data.get(newValue.trim());
                    String definition = selectedWord.getWord_explain();
                    context.definitionView.getEngine().loadContent(definition, "text/html");
                }
        );
    }
    public void readData(String DATA_FILE_PATH, Map<String, Word> data, List<String> dictionary) {
        data.clear();
        dictionary.clear();
        FileReader fis = null;
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

    public void show(List<String> dictionary, Map<String, Word> data) {
        ObservableList<String> allWords = FXCollections.observableArrayList(dictionary);
        SearchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            String input = SearchWord.getText().toLowerCase();
            ObservableList<String> filteredList = FXCollections.observableArrayList();

            if (input.isEmpty()) {
                // Nếu ô tìm kiếm trống, hiển thị lại tất cả các từ
                listView.setItems(allWords);
                this.listView.getSelectionModel().selectedItemProperty().addListener(
                        (observable1, oldValue1, newValue1) -> {
                            if (newValue1 != null) { // Kiểm tra newValue1 có null hay không
                                Word selectedWord = data.get(newValue1.trim());
                                String definition = selectedWord.getWord_explain();
                                this.definitionView.getEngine().loadContent(definition, "text/html");
                            }
                        }
                );
            } else {
                // Lọc danh sách từ theo chuỗi nhập vào
                for (String word : dictionary) {
                    if (word.toLowerCase().startsWith(input)) {
                        filteredList.add(word);
                    }
                }
                listView.setItems(filteredList);
                this.listView.getSelectionModel().selectedItemProperty().addListener(
                        (observable1, oldValue1, newValue1) -> {
                            Word selectedWord = data.get(newValue1.trim());
                            String definition = selectedWord.getWord_explain();
                            this.definitionView.getEngine().loadContent(definition, "text/html");
                        }
                );
            }
        });
        loadWordList(data);
    }


    public void removeWordFromFile(String wordToRemove, String filePath) {
        File tempFile = new File("C:\\tudienn\\src\\main\\java\\Transition\\tempUserSaveWord.txt");
        File filecurent = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filecurent));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(wordToRemove)) {
                    writer.write(line + System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(filecurent)) {
            // Ghi một chuỗi rỗng để xóa hết dữ liệu trong tệp
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        // Xóa file cũ
//
//        filecurent.delete();
//        // Đổi tên file tạm thành tên file gốc
//        tempFile.renameTo(filecurent);
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filecurent))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(wordToRemove)) {
                    writer.write(line + System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(tempFile)) {
            // Ghi một chuỗi rỗng để xóa hết dữ liệu trong tệp
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void reload(){
        reload_all();

    }
    @FXML
    public void handleDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete word notification");
        alert.setHeaderText("Do you want to delete this word?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No",ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==buttonTypeYes) {
            deleteWord(UserDataCurent, dictionaryUserCurent, DATA_FILE_PATH_USER_CURENT);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("This word is deleted!");
            alert1.showAndWait();
        }
    }

    public void deleteWord(Map<String, Word> UserSaveWord, List<String> dictionary, String filepath) {
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        if (selectedWord != null && !selectedWord.isEmpty()) {
            listView.getItems().remove(selectedWord);
            dictionary.remove(selectedWord);
            UserSaveWord.remove(selectedWord);
            removeWordFromFile(selectedWord, filepath);
            if (listView.getItems().isEmpty()) {
                // Nếu danh sách trống, hiển thị nội dung trống trên WebView
                definitionView.getEngine().loadContent("");
            } else {
                String firstWord = listView.getItems().get(1);
                Word firstSelectedWord = UserSaveWord.get(firstWord);
                String definition = firstSelectedWord.getWord_explain();
                definitionView.getEngine().loadContent(definition, "text/html");
            }
        }
    }

    public void Arraylis(Map<String, Word> data, List<String> dictionary) {
        for (String key : data.keySet()) {
            dictionary.add(key);
        }
    }

    @FXML
    public void setonMouseClickedTexField() {
        ShowWordTextFiled(UserDataCurent);
    }

    public void ShowWordTextFiled(Map<String, Word> data) {
        listView.setOnMouseClicked(event -> {
            // Lấy từ được chọn
            String selectedWord = listView.getSelectionModel().getSelectedItem();
            // Gắn từ được chọn vào TextField
            SearchWord.setText(selectedWord);
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
    public void handleButtonLoud(ActionEvent actionEvent) {
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
    public void handleAddButton(ActionEvent actionEvent) throws IOException {
        AddWord();
        reload_all();
    }
    private void uploadUserListView() {
        listView.getItems().clear();


    }

    public void AddWord() throws IOException {

        SceneAddWord sceneAddWord = new SceneAddWord();
        AnchorPane root1 = sceneAddWord.getRoot();// t sửa  như này
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.setTitle("Add Word");
        stage.showAndWait();


    }
    public void reload_all()
    {
        dictionaryUserCurent.clear();
        UserDataCurent.clear();
        listView.getItems().clear();
        definitionView.getEngine().loadContent("");

        // Tải danh sách từ vựng mới từ scene mới
        readData(DATA_FILE_PATH_USER_CURENT, UserDataCurent, dictionaryUserCurent);
        loadWordList(UserDataCurent);
        showData();
        initComponents(UserDataCurent);
    }

}

