package Game.Scrambled;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class GameResult {
    @FXML
    private Label Answer;
    @FXML
    private Button backButton;
    @FXML
    private Label YourScore;
    @FXML
    private Label HighestScore;
    int HS = 0;
    String option_sound = getClass().getResource("/com/example/demo/sound/option.mp3").toExternalForm();
    Media option_media = new Media(option_sound);
    MediaPlayer optionSound = new MediaPlayer(option_media);

    @FXML
    public void initialize() throws Exception {
        YourScore.setText("Your Score: " + ScrambledGameScene.scores);
        HighestScore();
        HighestScore.setText("Highest Score: " + HS);
        Answer.setText("Answer: " + ScrambledGameScene.AnswerCorrect);
    }

    private void HighestScore() {
        if (HS < ScrambledGameScene.scores) {
            HS = ScrambledGameScene.scores;
        }
    }

    @FXML
    private void backToGameScene(ActionEvent event) throws IOException {
        optionSound.setVolume(0.7);
        optionSound.seek(Duration.ZERO);
        optionSound.play();
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
