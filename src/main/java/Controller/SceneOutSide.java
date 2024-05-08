package Controller;

import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

import static Controller.App.*;
import static Controller.App.dictionaryCurent;
//import static Controller.SceneUser.DATA_FILE_PATH_User;

public class SceneOutSide {
    protected FXMLLoader fxmlLoaderSceneOutside = new FXMLLoader(getClass().getResource("/com/example/demo/SceneOutSide.fxml"));
    private AnchorPane root;

    public AnchorPane getRoot() throws IOException {
        root = fxmlLoaderSceneOutside.load();
        return root;
    }

    public void Clickhere() throws IOException {
        Introduce introduce = new Introduce();
        AnchorPane root = introduce.getRoot();// t sửa  như này
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Introduce");
        stage.showAndWait();
    }
}
