package Game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Game.Scrambled.ScrambledStartScene;
import Game.Wordle.WordleStartScene;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class StartScene implements Initializable {
    static WordleStartScene wordleStartScene;
    static ScrambledStartScene scrambledStartScene;


    public static void setWordleStartScene(WordleStartScene wordleStartScene) {
        StartScene.wordleStartScene = wordleStartScene;
    }
    public static void setScrambledStartScene(ScrambledStartScene scrambledScene)
    {
        StartScene.scrambledStartScene = scrambledScene;
    }

    @FXML
    private Pane rootPane;

    @FXML
    private AnchorPane chooseGamePane;

    @FXML
    private Pane wordlePane;

    @FXML
    private Pane scrambledPane;

    @FXML
    private Button wordleButton;

    @FXML
    TextAnimator textAnimator;

    String option_sound = getClass().getResource("/com/example/demo/sound/option.mp3").toExternalForm();
    Media option_media = new Media(option_sound);
    MediaPlayer optionSound = new MediaPlayer(option_media);

    @FXML
    private Text text;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.setOpacity(0);
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader(WordleStartScene.class.getResource("/com/example/demo/fxml/Wordle/WordleStartScene.fxml"));
            String css1 = this.getClass().getResource("/com/example/demo/css/Wordle/Start.css").toExternalForm();
            Pane rootWordle = fxmlLoader1.load();
            rootWordle.getStylesheets().add(css1);
            wordlePane.getChildren().add(rootWordle);
            hideWordlePane();

            FXMLLoader fxmlLoader2 = new FXMLLoader(ScrambledStartScene.class.getResource("/com/example/demo/fxml/Scrambled/ScrambledStartScene.fxml"));
            String css2 = this.getClass().getResource("/com/example/demo/css/Scrambled/Start.css").toExternalForm();

            Pane rootMemory = fxmlLoader2.load();
            rootMemory.getStylesheets().add(css2);

            scrambledPane.getChildren().add(rootMemory);
            hideScrambledPane();

            ScrambledStartScene.setStartScene(this);
            WordleStartScene.setStartScene(this);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        optionSound.play();
        optionSound.stop();
        TextOutput textOutput = new TextOutput() {
            @Override
            public void writeText(String textOut) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(textOut);
                    }
                });
            }
        };

        textAnimator = new TextAnimator("Welcome To My Show!",
                100, textOutput);

        Thread thread = new Thread(textAnimator);
        thread.start();

    }

    public void hideWordlePane() {
        chooseGamePane.setVisible(true);
        wordlePane.setVisible(false);
        wordlePane.toBack();
        makeClearTransition();
    }

    public void hideScrambledPane() {
        chooseGamePane.setVisible(true);
        scrambledPane.setVisible(false);
        scrambledPane.toBack();
        makeClearTransition();

    }

    public void showWordlePane() {
        chooseGamePane.setVisible(false);
        wordlePane.setVisible(true);
        wordlePane.toFront();
        wordleStartScene.makeClearTransition();

    }

    public void showScrambledPane() {
        chooseGamePane.setVisible(false);
        scrambledPane.setVisible(true);
        scrambledPane.toFront();
        scrambledStartScene.makeClearTransition();
    }


    // LOAD WORDLE
    @FXML
    private void nextToScrambledGame(ActionEvent event) throws IOException {
        optionSound.setVolume(0.7);
        optionSound.seek(Duration.ZERO);
        optionSound.play();
        showScrambledPane();
    }

    // LOAD MemoryGame
    @FXML
    private void nextToWordle(ActionEvent event) throws IOException {
        optionSound.setVolume(0.7);
        optionSound.seek(Duration.ZERO);
        optionSound.play();
        showWordlePane();
    }

    private void makeClearTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}