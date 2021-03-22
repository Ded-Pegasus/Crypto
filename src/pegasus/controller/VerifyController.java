package pegasus.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class VerifyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane XmlSign;

    @FXML
    private Button chooseXml;

    @FXML
    private Text directoryXml;

    @FXML
    private Button verify;

    @FXML
    private Button choosePublicKey;

    @FXML
    private Text directoryPublicKey;

    @FXML
    private Text result;

    @FXML
    void initialize() {

    }
}
