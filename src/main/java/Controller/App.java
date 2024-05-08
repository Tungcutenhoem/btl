package Controller;

import com.example.demo.Word;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
    public static final String DATA_FILE_PATHE_V = "C:\\tudienn\\src\\main\\java\\Transition\\E_V.txt";
    public static final String DATA_FILE_PATHV_E = "C:\\tudienn\\src\\main\\java\\Transition\\V_E.txt";
    public static String DATA_FILE_PATH_CURENT;
    public static String DATA_FILE_PATH_USER_CURENT;
    public static Map<String, Word> dataCurent = new HashMap<>();
    public static Map<String, Word> UserDataCurent = new HashMap<>();
    public static List<String> dictionaryUserCurent = new ArrayList<>();
    public static List<String> dictionaryCurent = new ArrayList<>();


    protected double x, y = 0;
    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        hostServices = getHostServices();
        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.show();
    }

    public static HostServices getHostServicesInstance() {
        return hostServices;
    }

    public static void main(String[] args) {
        launch(args);
    }
}