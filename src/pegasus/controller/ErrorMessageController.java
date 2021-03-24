package pegasus.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pegasus.model.ErrorMessage;

public class ErrorMessageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ok;

    @FXML
    private Text message;

    @FXML
    private AnchorPane error;


    @FXML
    void initialize() {
        String errorMessage = ErrorMessage.getErrorMessage();
        message.setText(errorMessage);

        ok.setOnAction(event -> {
            Stage stage = (Stage) ok.getScene().getWindow();
            stage.close();
        });
    }
}
