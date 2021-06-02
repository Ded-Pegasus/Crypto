package org.pegasus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.pegasus.model.base64.bean.CodeFlag;
import org.pegasus.model.p7b.ExecuteEvent;
import org.pegasus.model.utils.TypeFile;
import org.pegasus.model.utils.SaveObject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class P7bController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane P7b;

    @FXML
    private Button chooseCertificate;

    @FXML
    private Text directoryCert;

    @FXML
    private Button chooseCrls;

    @FXML
    private Text directoryCrls;

    @FXML
    private Button execute;

    @FXML
    private Button chooseP7b;

    @FXML
    private Text directoryP7b;

    @FXML
    private ToggleGroup method;

    @FXML
    private Text message;

    @FXML
    private RadioButton decode;

    @FXML
    private RadioButton encode;

    public CodeFlag codeFlag;

    public void decodeAction(ActionEvent actionEvent) {
        codeFlag = CodeFlag.Decode;
    }

    public void encodeAction(ActionEvent actionEvent) {
        codeFlag = CodeFlag.Encode;
    }

    private File getPathToDirectory() {
        Stage stage = (Stage) P7b.getScene().getWindow();
        return SaveObject.chooseDirectory(stage);
    }

    private File getPathToFile() {
        Stage stage = (Stage) P7b.getScene().getWindow();
        return SaveObject.chooseFile(stage, TypeFile.p7b);
    }

    @FXML
    void initialize() {

        //default
        decode.setSelected(true);
        codeFlag = CodeFlag.Decode;

        chooseCertificate.setOnAction(event -> {
            File file = getPathToDirectory();
            if (file != null) {
                directoryCert.setText(file.getAbsolutePath());
            }
        });

        chooseCrls.setOnAction(event -> {
            File file = getPathToDirectory();
            if (file != null) {
                directoryCrls.setText(file.getAbsolutePath());
            }
        });

        chooseP7b.setOnAction(event -> {
            File file = null;
            if (codeFlag == CodeFlag.Encode) {
                file = getPathToDirectory();
            } else if (codeFlag == CodeFlag.Decode) {
                file = getPathToFile();
            }
            if (file != null) {
                directoryP7b.setText(file.getAbsolutePath());
            }
        });

        execute.setOnAction(event -> ExecuteEvent.execute(directoryCrls, directoryCert, directoryP7b, codeFlag, message));

    }
}
