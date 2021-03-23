package pegasus.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pegasus.model.LoaderWindow;
import pegasus.model.Windows;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Base64Button;

    @FXML
    private Button P7bButton;

    @FXML
    private Button ASN1Button;

    @FXML
    private Button Sign;

    @FXML
    private Button Verify;

    @FXML
    private Button generate;

    @FXML
    void initialize() {

        LoaderWindow loaderWindow = new LoaderWindow();

        Base64Button.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.getBase64());
        });

        P7bButton.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.getP7b());
        });

        ASN1Button.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.getAsn1());
        });

        Sign.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.getSign());
        });

        Verify.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.getVerify());
        });

        generate.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.getGenerateKeyPair());
        });
    }
}