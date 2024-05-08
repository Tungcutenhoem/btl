package Game.Scrambled;

import Game.Game;
import Game.Wordle.WordleStartScene;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ScrambledGameScene extends Game implements Initializable {
    static ScrambledStartScene scrambledStartScene ;
    public static void setScrambledStartScene(ScrambledStartScene scrambledStartScene)
    {
        ScrambledGameScene.scrambledStartScene = scrambledStartScene;
    }
    String almostCorrect_sound = getClass().getResource("/com/example/demo/sound/almostCorrect.mp3").toExternalForm();
    String flip_sound = getClass().getResource("/com/example/demo/sound/flip.mp3").toExternalForm();
    String check_sound = getClass().getResource("/com/example/demo/sound/check.mp3").toExternalForm();

    Media almostCorrect_media = new Media(almostCorrect_sound);
    Media flip_media = new Media(flip_sound);
    Media check_media = new Media(check_sound);

    MediaPlayer almostCorrectSound = new MediaPlayer(almostCorrect_media);
    MediaPlayer flipSound = new MediaPlayer(flip_media);
    MediaPlayer checkSound = new MediaPlayer(check_media);
    @FXML
    private Text text;
    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private TextField YourGuess;
    @FXML
    private TextField SuggestWord;
    @FXML
    private Label Score;
    @FXML
    private Label Turn;
    @FXML
    private Label Heart;
    public  static String AnswerCorrect;
    private String SW;
    private Random randomWord = new Random();
    public static int turns = 0;
    public static int scores = 0;
    public static  int hearts = 5;
    private String getRandomWord() {
        int randomIndex = randomWord.nextInt(words.length);
        return words[randomIndex];
    }
    private String getRandomAL()
    {
        String randomAL = "";
        List<Character> characters = new ArrayList<>();

        // Chuyển từ thành một danh sách các ký tự
        for (char c : AnswerCorrect.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        for(char c : characters)
        {
            randomAL+=c;
        }
        return randomAL;
    }

    private String[] words = {
            "apple", "banana", "orange", "strawberry", "grape",
            "watermelon", "pineapple", "blueberry", "kiwi", "peach",
            "mango", "pear", "cherry", "raspberry", "lemon",
            "lime", "coconut", "blackberry", "apricot", "plum",
            "pomegranate", "fig", "nectarine", "guava", "lychee",
            "dragonfruit", "cantaloupe", "papaya", "cranberry", "melon",
            "avocado", "pine", "cucumber", "potato", "carrot",
            "lettuce", "onion", "garlic", "broccoli", "cauliflower",
            "pea", "bean", "corn", "eggplant", "zucchini",
            "squash", "bell pepper", "tomato", "celery", "spinach",
            "kale", "asparagus", "radish", "turnip", "beet",
            "cabbage", "sweet potato", "yam", "mushroom", "leek",
            "chive", "parsley", "cilantro", "basil", "oregano",
            "rosemary", "thyme", "dill", "sage", "mint",
            "lavender", "marjoram", "anise", "fennel",
            "cinnamon", "cloves", "nutmeg", "ginger", "cardamom",
            "vanilla", "chocolate", "coffee", "tea", "milk",
            "cream", "sugar", "honey", "salt", "pepper"
    };

    @Override
    public void showResult() throws IOException {
        optionSound.setVolume(0.7);
        optionSound.seek(Duration.ZERO);
        optionSound.play();
    }

    public void makeClearTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(javafx.util.Duration.millis(TimeUnit.SECONDS.toMillis(1)));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       ScrambledStartScene.setScrambledGameScene(this);
        setup();
        AnswerCorrect = getRandomWord();
        SW = getRandomAL();
        SuggestWord.setText(SW);
    }
    @FXML
    public void backtoScrambledStart() throws IOException {
        optionSound.setVolume(0.7);
        optionSound.seek(Duration.ZERO);
        optionSound.play();
        scrambledStartScene.hidememStartPane();
    }
    @FXML
    private void CheckAnswer(ActionEvent actionEvent)
    {
        String guess = YourGuess.getText().toLowerCase();
        if(guess.equals(AnswerCorrect.toLowerCase()))
        {
            scores+= 10;
            turns++;
            Turn.setText("Turns: " + turns);
            Score.setText("Score: " + scores);
            nextRound();
        }
        else
        {
            hearts--;
            Heart.setText("Heart: " + hearts);
            if(hearts==0)
            {
                OpenGameResult();
                ResertGame();
            }
        }
    }
    @FXML
    private void RestartGane(ActionEvent actionEvent)
    {
        ResertGame();
    }
    private void ResertGame()
    {
        turns = 0;
        hearts = 5;
        scores = 0;

        Turn.setText("Turns: " + turns);
        Heart.setText("Heart: " + hearts);
        Score.setText("Score: " + scores);

        YourGuess.setText("");
        SuggestWord.setText("");

        AnswerCorrect = getRandomWord();
        SuggestWord.setText(AnswerCorrect);

        setup();
    }
    private void OpenGameResult()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/fxml/Scrambled/GameResult.fxml"));
            Parent root = fxmlLoader.load();
            GameResult gameResult = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void nextRound()
    {
        YourGuess.setText("");
        AnswerCorrect = getRandomWord();
        SW = getRandomAL();
        SuggestWord.setText(SW);
    }



}
