package org.pegasus.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.pegasus.model.ErrorMessage;
import org.pegasus.model.utils.TypeFile;
import org.pegasus.model.utils.SaveObject;
import org.pegasus.model.xml_dsig.Verification;

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
    private ChoiceBox<String> algorithm;


    @FXML
    void initialize() {

        //default value
        algorithm.setValue("DSA");
        //default value
        ObservableList<String> listDigestMethod = algorithm.getItems();
        listDigestMethod.add("DSA");
        listDigestMethod.add("RSA");
        algorithm.setItems(listDigestMethod);

        Verification verification = new Verification();

        chooseXml.setOnAction(event -> {
            Stage stage = (Stage) XmlSign.getScene().getWindow();
            File file = SaveObject.chooseFile(stage, TypeFile.xml);

            try {

                DOMParser parser = new DOMParser();
                parser.parse(file.getAbsolutePath());
                Document document = parser.getDocument();

                verification.setDocument(document);
                directoryXml.setText(file.getAbsolutePath());
            } catch (IOException  | SAXException e) {
                ErrorMessage.message(e.getMessage());
                result.setText("Couldn't read xml file");
                e.printStackTrace();
            }
        });

        choosePublicKey.setOnAction(event -> {
            Stage stage = (Stage) XmlSign.getScene().getWindow();
            File file = SaveObject.chooseFile(stage, TypeFile.key);
            directoryPublicKey.setText(file.getAbsolutePath());
            verification.setPublicKeyFile(file);
        });

        verify.setOnAction(event ->{
            verification.setAlgorithm(algorithm.getValue());
            String str = algorithm.getValue();
            boolean validFlag = false;
            try {
                 validFlag = verification.verificationXmlSignature();
            } catch (Exception e) {
                ErrorMessage.message(e.getMessage());
                result.setText(e.getMessage());
                e.printStackTrace();
            }
            result.setText(String.valueOf(validFlag));
        });
    }
}



