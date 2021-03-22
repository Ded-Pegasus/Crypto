package pegasus.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class SignController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane XmlSign;

    @FXML
    private Button chooseCertificate;

    @FXML
    private Text directoryXml;

    @FXML
    private Button execute;

    @FXML
    private Button chooseP7b;

    @FXML
    private Text directoryP7b;

    @FXML
    private RadioButton Enveloping;

    @FXML
    private ToggleGroup method;

    @FXML
    private RadioButton Enveloped;

    @FXML
    private Text message;

    @FXML
    private RadioButton Detached;

    @FXML
    private ToggleGroup method1;

    @FXML
    void decodeAction(ActionEvent event) {

    }

    @FXML
    void encodeAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
