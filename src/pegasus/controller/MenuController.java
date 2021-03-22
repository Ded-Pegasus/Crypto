package pegasus.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pegasus.model.Windows;

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
    void initialize() {

        Base64Button.setOnAction(event -> {
            loadFxml(Windows.getBase64());
        });

        P7bButton.setOnAction(event -> {
            loadFxml(Windows.getP7b());
        });

        ASN1Button.setOnAction(event -> {
            loadFxml(Windows.getAsn1());
        });

        Sign.setOnAction(event -> {
            loadFxml(Windows.getSign());
        });

        Verify.setOnAction(event -> {
            loadFxml(Windows.getVerify());
        });
    }

    private void loadFxml(String pathToFxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(pathToFxml));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();
    }

}