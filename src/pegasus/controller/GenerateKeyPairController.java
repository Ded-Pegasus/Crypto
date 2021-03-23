package pegasus.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pegasus.model.generate.Generate;
import pegasus.model.utils.SaveObject;

public class GenerateKeyPairController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane GenerateKeyPair;

    @FXML
    private Button choosePublicKeyDirectory;

    @FXML
    private Text directoryPublicKey;

    @FXML
    private Button generate;

    @FXML
    private Button choosePrivateKeyDirectory;

    @FXML
    private Text directoryPrivateKey;

    @FXML
    private Text message;

    @FXML
    private ChoiceBox<String> encryptionAlgorithm;

    private File getPathToDirectory() {
        Stage stage = (Stage) GenerateKeyPair.getScene().getWindow();
        return SaveObject.chooseDirectory(stage);
    }

    @FXML
    void initialize() {

        //default value
        encryptionAlgorithm.setValue("DSA");
        //default value

        ObservableList<String> listDigestMethod = encryptionAlgorithm.getItems();
        listDigestMethod.add("DSA");
        listDigestMethod.add("RSA");

        choosePrivateKeyDirectory.setOnAction(event -> {
            File file = getPathToDirectory();
            if (file != null) {
                directoryPrivateKey.setText(file.getAbsolutePath());
            }
        });

        choosePublicKeyDirectory.setOnAction(event -> {
            File file = getPathToDirectory();
            if (file != null) {
                directoryPublicKey.setText(file.getAbsolutePath());
            }
        });

        generate.setOnAction(event -> {
            try {
                if (!directoryPublicKey.getText().isEmpty() || !directoryPrivateKey.getText().isEmpty()) {
                    Generate.generateKeyPair(directoryPublicKey.getText(),
                            directoryPrivateKey.getText(),
                            encryptionAlgorithm.getValue());
                    message.setText("Generate KeyPair success");
                } else {
                    message.setText("Choose directory");
                }
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            }
        });

    }
}
