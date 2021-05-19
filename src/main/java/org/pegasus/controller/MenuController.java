package org.pegasus.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.pegasus.model.LoaderWindow;
import org.pegasus.model.UrlAddress;

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

        Base64Button.setOnAction(event -> loaderWindow.loadFxml(UrlAddress.urlBase64));

        P7bButton.setOnAction(event -> loaderWindow.loadFxml(UrlAddress.urlP7b));

        ASN1Button.setOnAction(event -> loaderWindow.loadFxml(UrlAddress.urlAsn1));

        Sign.setOnAction(event -> loaderWindow.loadFxml(UrlAddress.urlSign));

        Verify.setOnAction(event -> loaderWindow.loadFxml(UrlAddress.urlVerify));

        generate.setOnAction(event -> loaderWindow.loadFxml(UrlAddress.urlGenerateKey));
    }
}