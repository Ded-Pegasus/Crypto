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
            loaderWindow.loadFxml(Windows.base64);
        });

        P7bButton.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.p7b);
        });

        ASN1Button.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.asn1);
        });

        Sign.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.sign);
        });

        Verify.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.verify);
        });

        generate.setOnAction(event -> {
            loaderWindow.loadFxml(Windows.generateKeyPair);
        });
    }
}