package Game.Scrambled;

import Game.StartScene;
import Game.Wordle.GameScene;
import Game.Wordle.WordleStartScene;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScrambledStartScene implements Initializable {
    static ScrambledGameScene scrambledGameScene;
    static StartScene startScene ;
    public static void setScrambledGameScene(ScrambledGameScene scrambledGameScene) {
        ScrambledStartScene.scrambledGameScene = scrambledGameScene;
    }
    public static void setStartScene(StartScene startScene) {
        ScrambledStartScene.startScene = startScene;

    }
    String option_sound = getClass().getResource("/com/example/demo/sound/option.mp3").toExternalForm();
    Media option_media = new Media(option_sound);
    MediaPlayer optionSound = new MediaPlayer(option_media);

    @FXML
    private Pane rootPane;
    @FXML
    private Pane memStartPane;
    @FXML
    private AnchorPane scrambledPane;
    public void makeClearTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optionSound.play();
        optionSound.stop();
        rootPane.setOpacity(0);
        try {
            StackPane tmpPane = (StackPane) FXMLLoader.load(getClass().getResource("/com/example/demo/fxml/Scrambled/ScrambledGame.fxml"));
            String css = this.getClass().getResource("/com/example/demo/css/Scrambled/ScrambledGame.css").toExternalForm();
            tmpPane.getStylesheets().add(css);
            memStartPane.getChildren().add(tmpPane);
            hidememStartPane();

            ScrambledGameScene.setScrambledStartScene(this);
            StartScene.setScrambledStartScene(this);
        } catch (IOException ex) {
            Logger.getLogger(WordleStartScene.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    private void nextToGame(ActionEvent event) throws IOException {
        optionSound.setVolume(1.0);
        optionSound.seek(Duration.ZERO);
        optionSound.play();
//        scrambledGame.startGame();
        showmemStartPane();
    }
    public void showmemStartPane() {
        scrambledPane.setVisible(false);
        memStartPane.setVisible(true);
        memStartPane.toFront();
        scrambledGameScene.makeClearTransition();
    }

    @FXML
    public void backToStartScene() throws IOException {
        optionSound.setVolume(0.7);
        optionSound.seek(Duration.ZERO);
        optionSound.play();
        startScene.hideScrambledPane();

    }
    public void hidememStartPane() {
        scrambledPane.setVisible(true);
        memStartPane.setVisible(false);
        memStartPane.toBack();
        makeClearTransition();
    }


}
