package pegasus.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private Button chooseXml;

    @FXML
    private Text directoryXml;

    @FXML
    private Button sign;

    @FXML
    private Button chooseKeyStore;

    @FXML
    private Text directoryKeyStore;

    @FXML
    private RadioButton enveloping;

    @FXML
    private ToggleGroup method;

    @FXML
    private RadioButton enveloped;

    @FXML
    private Text message;

    @FXML
    private RadioButton detached;

    @FXML
    private ToggleGroup method1;

    @FXML
    private ChoiceBox<?> digestMethod;

    @FXML
    private ChoiceBox<?> signatureMethod;

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
