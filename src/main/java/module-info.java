module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires com.dlsc.formsfx;
    requires javafx.web;
    requires java.logging;
    requires jsapi;
    requires voicerss.tts;

//    requires google.cloud.speech;
//    requires google.api.translate.java;

    requires transitive javafx.graphics;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
//    requires org.openqa.selenium.core;
    requires selenium.api;
    requires selenium.firefox.driver;
    requires selenium.remote.driver;
    requires selenium.chrome.driver;
    requires tess4j;
//    requires transitive javafx.media;

    opens Game to javafx.fxml, java.base , javafx.graphics;
    opens Game.Wordle to javafx.fxml, java.base , javafx.graphics;
    opens com.example.demo to javafx.fxml, java.base , javafx.graphics;
    opens Model to javafx.fxml, java.base , javafx.graphics;
    opens Controller to javafx.fxml , java.base , javafx.graphics;
    opens Game.Scrambled to javafx.graphics , javafx.fxml , java.base;
    exports Game.Scrambled;
    exports Controller;
    exports com.example.demo;
    exports Game.Wordle ;
    exports Game ;
    exports Model;
}