package pegasus.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ErrorMessageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane Password;

    @FXML
    private Button ok;

    @FXML
    private Text message;

    @FXML
    void initialize() {
        assert Password != null : "fx:id=\"Password\" was not injected: check your FXML file 'ErrorMessage.fxml'.";
        assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'ErrorMessage.fxml'.";
        assert message != null : "fx:id=\"message\" was not injected: check your FXML file 'ErrorMessage.fxml'.";
    }
}
