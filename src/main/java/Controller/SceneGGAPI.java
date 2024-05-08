package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.ImageTextTranslator;
import Model.SelectLanguage;
import Model.TextToSpeech;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneGGAPI {

    @FXML
    private TextArea txtGG;

    @FXML
    private TextArea txtGGtrans;

    @FXML
    private ComboBox<String> toCountry;

    @FXML
    private ComboBox<String> fromCountry;

    @FXML
    private Button btnSwap;

    @FXML
    private Button pronounceTo;

    @FXML
    private Button pronounceFrom;

    // ==============================================================================================================================

    List<String> list_lang = new ArrayList<String>();
    List<String> list_lang_code = new ArrayList<String>();
    List<String> list_toCountry = new ArrayList<String>();
    List<String> list_fromCountry = new ArrayList<String>();
    List<String> list_speak_code = new ArrayList<String>();
    List<String> list_speak_name = new ArrayList<String>();
    List<String> list_speakCodeTo = new ArrayList<String>();
    List<String> list_speakCodeFrom = new ArrayList<String>();
    List<String> list_speakNameTo = new ArrayList<String>();
    List<String> list_speakNameFrom = new ArrayList<String>();

    String translated = "";
    String speakCode = "";
    String speakName = "";

    SelectLanguage sl;
    int index_to = 0;
    int index_from = 0;

    private final int MAX_WORDS_PER_LINE = 40;

    @FXML
    private void ggPictureToText(ActionEvent actionEvent) {
        ImageTextTranslator imageTextTranslator = new ImageTextTranslator();
        String newtext = formatText(imageTextTranslator.ImageTextToText());
        txtGG.setText(newtext);
    }

    // ==============================================================================================================================

    @FXML
    private void ggTranslate(ActionEvent event) throws Exception {
        translate(txtGG.getText());
    }

    @FXML
    private void pronounceTo(ActionEvent event) throws Exception {
        TextToSpeech.languageName = list_speakNameTo.get(index_to);
        TextToSpeech.languageCode = list_speakCodeTo.get(index_to);
        TextToSpeech.ttsTextArea(translated);
    }

    // error
    @FXML
    private void pronounceFrom(ActionEvent event) throws Exception {
        TextToSpeech.languageName = list_speakNameFrom.get(index_from);
        TextToSpeech.languageCode = list_speakCodeFrom.get(index_from);
        TextToSpeech.ttsTextArea(txtGG.getText());
    }

    @FXML
    private void ggSwap(ActionEvent event) throws Exception {
        int indexTo = toCountry.getSelectionModel().getSelectedIndex();
        int indexFrom = fromCountry.getSelectionModel().getSelectedIndex();

        fromCountry.getSelectionModel().select(indexTo);
        toCountry.getSelectionModel().select(indexFrom);

        txtGG.setText(txtGGtrans.getText());
        txtGGtrans.setText("");

        ggTranslate(event);
    }

    private void setOff() {
        toCountry.setDisable(true);
        fromCountry.setDisable(true);
        btnSwap.setDisable(true);
        txtGG.setDisable(true);
    }

    private void setOn() {
        toCountry.setDisable(false);
        fromCountry.setDisable(false);
        btnSwap.setDisable(false);
        txtGG.setDisable(false);
    }

    // ================================================================================================================================

    private void translate(String text) throws IOException {

        setOff();

        Service<Void> serviceVoid = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        int indexTo = toCountry.getSelectionModel().getSelectedIndex();
                        int indexFrom = fromCountry.getSelectionModel().getSelectedIndex();
//                        checkGoogleAPI();
                        String langTo = list_toCountry.get(indexTo);
                        String langFrom = list_fromCountry.get(indexFrom);

                        index_to = indexTo;
                        index_from = indexFrom;

                        translated = sl.translator(langFrom, langTo, text);
                        translated = formatText(translated);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                txtGGtrans.setText(translated);
                                toCountry.setDisable(false);
                                txtGG.setDisable(false);
                                fromCountry.setDisable(false);
                                btnSwap.setDisable(false);
                            }
                        });
                        return null;
                    }
                };
            }
        };

        serviceVoid.start();
        Platform.runLater(() -> {
            txtGGtrans.setText(translated);
            toCountry.setDisable(false);
            txtGG.setDisable(false);
            fromCountry.setDisable(false);
            btnSwap.setDisable(false);
        });
    }

    @FXML
    private void initialize() {
        translated = "";
        sl = new SelectLanguage();

        setOff();

        Service<Void> serviceVoid = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws IOException {
                        list_lang = sl.getLanguageName();
                        list_lang_code = sl.getLanguageCode();
//                        checkGoogleAPI();
                        list_speak_code = sl.getSpeakCode();
                        list_speak_name = sl.getSpeakName();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!list_lang.isEmpty()) {
                                    for (String s : list_lang) {
                                        toCountry.getItems().add(s);
                                        fromCountry.getItems().add(s);
                                    }

                                    for (String s : list_lang_code) {
                                        list_toCountry.add(s);
                                        list_fromCountry.add(s);
                                    }

                                    for (String s : list_speak_code) {
                                        list_speakCodeTo.add(s);
                                        list_speakCodeFrom.add(s);
                                    }

                                    for (String s : list_speak_name) {
                                        list_speakNameTo.add(s);
                                        list_speakNameFrom.add(s);
                                    }

                                    setOn();
                                }
                            }
                        });
                        return null;
                    }
                };
            }
        };
        serviceVoid.start();
    }
    private String formatText(String text) {
        String[] words = text.split("\\s+"); // Tách văn bản thành các từ
        String formattedText = "";

        int wordCount = 0;
        for (String word : words) {
            formattedText += word;
            formattedText +=" ";
            wordCount++;

            if (wordCount >= 10) { // Nếu số từ trên dòng vượt quá 30
                formattedText+="\n"; // Xuống dòng
                wordCount = 0; // Đặt lại số từ trên dòng
            }
        }
        return formattedText;
    }

}
