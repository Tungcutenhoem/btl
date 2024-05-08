package Controller;

import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Introduce {
    protected FXMLLoader fxmlLoaderIntroduce = new FXMLLoader(getClass().getResource("/com/example/demo/introduce.fxml"));
    private AnchorPane root;
    private static HostServices hostServices;

    public AnchorPane getRoot() throws IOException {
        root = fxmlLoaderIntroduce.load();
        return root;
        //hostServices = getHostServices();
    }

    static WebDriver browser;

    public void DucNam() throws IOException {
        HostServices hostServices = App.getHostServicesInstance();
        hostServices.showDocument("https://www.facebook.com/dnam.unteee");
    }

    public void MinhTung() throws IOException {
        HostServices hostServices = App.getHostServicesInstance();
        hostServices.showDocument("https://www.facebook.com/profile.php?id=100034934505795");
    }

    public void MinhNhat() throws IOException {
        HostServices hostServices = App.getHostServicesInstance();
        hostServices.showDocument("https://www.facebook.com/johan.nhat.9");

    }

    public void Github() {
        HostServices hostServices = App.getHostServicesInstance();
        hostServices.showDocument("https://github.com/Tungcutenhoem/btl");
    }

    public static HostServices getHostServicesInstance() {
        return hostServices;
    }

}
