package org.pegasus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.pegasus.model.base64.bean.CreateFlag;
import org.pegasus.model.base64.ExecuteEvent;
import org.pegasus.model.base64.bean.CodeFlag;
import org.pegasus.model.utils.TypeFile;
import org.pegasus.model.utils.SaveObject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Base64Controller {

    public Text message;

    public TextField pathDirectory;

    public CodeFlag codeFlag;

    public CreateFlag createFlag;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane Base64;

    @FXML
    private TextArea text;

    @FXML
    private Button executeButton;

    @FXML
    private Button addDirectory;

    @FXML
    private RadioButton decodeFlag;

    @FXML
    private ToggleGroup method;

    @FXML
    private RadioButton encodeFlag;

    @FXML
    private RadioButton createFile;

    @FXML
    private ToggleGroup createGroup;

    @FXML
    private RadioButton createText;

    @FXML
    void createFileFlag(ActionEvent event) {
        createFlag = CreateFlag.file;
        addDirectory.setVisible(true);
        pathDirectory.setVisible(true);
    }

    @FXML
    void createTextFlag(ActionEvent event) {
        createFlag = CreateFlag.text;
        addDirectory.setVisible(false);
        pathDirectory.setVisible(false);
    }

    @FXML
    void decodeAction(ActionEvent event) {
        codeFlag = CodeFlag.Decode;
    }

    @FXML
    void encodeAction(ActionEvent event) {
        codeFlag = CodeFlag.Encode;
    }


    @FXML
    void initialize() {

        //default selected
        decodeFlag.setSelected(true);
        createFile.setSelected(true);
        text.setWrapText(true);
        text.getStylesheets().add("/view/TextAreaBlue.css");

        addDirectory.setOnAction(event -> {
            if (decodeFlag.isSelected()) {
                Stage stage = (Stage) Base64.getScene().getWindow();
                File file = SaveObject.chooseFile(stage, TypeFile.certificates);
                if (file != null) {
                    pathDirectory.setText(file.getAbsolutePath());
                }
            } else {
                Stage stage = (Stage) Base64.getScene().getWindow();
                File file = SaveObject.chooseDirectory(stage);

                if (file !=null) {
                    pathDirectory.setText(file.getAbsolutePath());
                }
            }
        });

        executeButton.setOnAction(event -> ExecuteEvent.execute(text, pathDirectory, message, method, createGroup));
    }
}