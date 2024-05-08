package Controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controller.SceneApp;

public class Dashboard implements Initializable {
    @FXML
    AnchorPane pane1, pane2;
    @FXML
    StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane1.setVisible(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();
    }

    public void exit1() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit notification");
        alert.setHeaderText("Do you want to exit?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        //ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            System.exit(0);
        }

    }

    public void menu() {
        pane1.setVisible(true);

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(0.5);
        fadeTransition1.play();

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(+600);
        translateTransition1.play();

        pane1.setOnMouseClicked(event -> {

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition.setFromValue(0.5);
            fadeTransition.setToValue(0);
            fadeTransition.play();

            fadeTransition.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition.setByX(-600);
            translateTransition.play();

        });
    }

    public void Dictionary(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/demo/SceneApp.fxml"));

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);


        pane1.setVisible(false);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(0);

        fadeTransition1.setOnFinished(event -> {
        });

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(-600);
        translateTransition1.play();
    }

    public void API(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/demo/SceneGGAPI.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

        pane1.setVisible(false);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(0);
        fadeTransition1.play();

        fadeTransition1.setOnFinished(event -> {
            pane1.setVisible(false);
        });

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(-600);
        translateTransition1.play();
    }

    public void user(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/demo/SceneUser.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);


        pane1.setVisible(false);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(0);

        fadeTransition1.setOnFinished(event -> {
        });

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(-600);
        translateTransition1.play();
    }

    public void Setting(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/SceneOutSide.fxml"));
        Parent fxml = fxmlLoader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

        pane1.setVisible(false);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(0);
        fadeTransition1.play();

        fadeTransition1.setOnFinished(event -> {
            pane1.setVisible(false);
        });

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(-600);
        translateTransition1.play();
    }

    public void Game(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/Start.fxml"));
        Parent fxml = fxmlLoader.load();

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

        // Thực hiện hiệu ứng thu gọn sidebar
        pane1.setVisible(false);

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(0);
        fadeTransition1.play();

        fadeTransition1.setOnFinished(event -> {
            pane1.setVisible(false);
        });

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(-600);
        translateTransition1.play();
    }

}
