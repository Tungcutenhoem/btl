package Controller;

import com.example.demo.Word;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

import static Controller.App.*;
//import static Controller.SceneUser.DATA_FILE_PATH_User;


public class SceneAddWord {
    @FXML
    private TextField Word;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField Defination;
    @FXML
    private TextField PartOfSpeech;
    @FXML
    private TextField TanscribePhonetically;
    @FXML
    private Button AddButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Button CW;
    @FXML
    private Button CD;
    @FXML
    private Button CTP;
    @FXML
    private Button CPOS;
    SceneUser sceneUser;
    private static final String SPLITTING_CHARACTERS = "<html>";

    protected AnchorPane root;
    protected  FXMLLoader fxmlLoaderSceneAddWord = new FXMLLoader(getClass().getResource("/com/example/demo/SceneAddWord.fxml"));

    public AnchorPane getRoot() throws IOException {
        root = fxmlLoaderSceneAddWord.load();
        return root;
    }

    public void setAddWord() throws IOException {


        Word = (TextField) fxmlLoaderSceneAddWord.getNamespace().get("Word");
        Defination = (TextField) fxmlLoaderSceneAddWord.getNamespace().get("Defination");
        PartOfSpeech = (TextField) fxmlLoaderSceneAddWord.getNamespace().get("PartOfSpeech");
        TanscribePhonetically = (TextField) fxmlLoaderSceneAddWord.getNamespace().get("TanscribePhonetically");


        AnchorPane root = fxmlLoaderSceneAddWord.load();
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
        SceneAddWord controller = fxmlLoaderSceneAddWord.getController();


        //primaryStage.show();


        if (AddButton != null) {
            AddButton.setOnAction(controller::handAddWord);
        }

        if (CancelButton != null) {
            CancelButton.setOnAction(controller::handCancelWord);
        }

        if(CW != null) {
            CW.setOnAction(controller::handCW);
        }

        if(CTP != null) {
            CTP.setOnAction(controller::handCTP);
        }
        if(CD != null) {

            CD.setOnAction(controller::handCD);
        }

        if(CPOS != null) {
            CPOS.setOnAction(controller::handCPOS);
        }
        btnCancel.requestFocus();

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void handAddWord(ActionEvent actionEvent) {
        FileReader fis = null;
        try {
            String AddWord = Word.getText().toLowerCase();
            String AddDefination = "<b> " + Defination.getText().toLowerCase() + "</b></font></li></ul></li></ul></html>";
            String AddPartOfSpeech = "<ul><li><b><i> " + PartOfSpeech.getText().toLowerCase() + "</i></b><ul><li><font color='#cc0000'>";
            String AddTanscribePhonetically = "<html><i>" + AddWord + "/" + TanscribePhonetically.getText().toLowerCase() + "/" + "</i><br/>";
            if (AddWord.equals("") || AddDefination.equals("") || AddPartOfSpeech.equals("") || AddTanscribePhonetically.equals("")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all the fields");
            } else {
                fis = new FileReader(DATA_FILE_PATH_USER_CURENT);
                BufferedReader rd = new BufferedReader(fis);
                String line;
                boolean check = true;
                while ((line = rd.readLine()) != null) {
                    String[] parts = line.split(SPLITTING_CHARACTERS);
                    String word = parts[0];
                    if (word.equals(AddWord)){
                        check=false;
                    }
                }
                if (check == false) {
                    showAlert(Alert.AlertType.ERROR, "Error", "This word has been saved before!");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Save word notification");
                    alert.setHeaderText("Do you want to save this word?");
                    ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeYes) {

                        WriteToFile(AddWord, AddDefination, AddPartOfSpeech, AddTanscribePhonetically);
                        //sceneUser.show(dictionaryUserCurent, UserDataCurent);
                        Word.setText("");
                        Defination.setText("");
                        PartOfSpeech.setText("");
                        TanscribePhonetically.setText("");
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.initModality(Modality.APPLICATION_MODAL);
                        alert1.setContentText("This word is saved Successfully");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
                        //sceneUser.setUser();
                        closeWindow();

                    } else if (result.get() == buttonTypeNo) {
                        Word.setText("");
                        Defination.setText("");
                        PartOfSpeech.setText("");
                        TanscribePhonetically.setText("");
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    public void WriteToFile(String AddWord, String AddDefination, String AddPartOfSpeech, String AddTanscribePhonetically) {
        try {
            FileWriter fis = new FileWriter(DATA_FILE_PATH_USER_CURENT, true);// chỉ cần sửa file nguồn thàng DATA_FILE_CURRENT khi demo là ok..
            BufferedWriter br = new BufferedWriter(fis);
            br.write(AddWord + AddTanscribePhonetically + AddPartOfSpeech + AddDefination);
            br.newLine();
            br.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handCancelWord(ActionEvent actionEvent) {
        Word.setText("");
        Defination.setText("");
        PartOfSpeech.setText("");
        TanscribePhonetically.setText("");
    }

    @FXML
    public void handCW(ActionEvent event) {
        Word.setText("");
    }

    @FXML
    public void handCTP(ActionEvent event) {
        TanscribePhonetically.setText("");
    }

    @FXML
    public void handCD(ActionEvent event) {
        Defination.setText("");
    }

    @FXML
    public void handCPOS(ActionEvent event) {
        PartOfSpeech.setText("");
    }
    @FXML
    private void cancel(ActionEvent event) {
        Word.setText("");
        Defination.setText("");
        PartOfSpeech.setText("");
        TanscribePhonetically.setText("");
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


}
