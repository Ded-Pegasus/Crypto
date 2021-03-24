package pegasus.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import pegasus.model.bean.TypeFile;
import pegasus.model.bean.ConvertFile;
import pegasus.model.exception.KeyException;
import pegasus.model.exception.SaveObjectException;
import pegasus.model.sign.Sign;
import pegasus.model.utils.SaveObject;

import javax.xml.parsers.ParserConfigurationException;

public class SignController {

    @FXML
    private AnchorPane XmlSign;

    @FXML
    private Button chooseXml;

    @FXML
    private Text directoryXml;

    @FXML
    private Button sign;

    @FXML
    private Button choosePublicKey;

    @FXML
    private Text directoryPublicKey;

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
    private ChoiceBox<String> digestMethod;

    @FXML
    private ChoiceBox<String> signatureMethod;

    @FXML
    private Button choosePrivateKey;

    @FXML
    private Text directoryPrivateKey;

    @FXML
    void initialize() {

        //default value
        digestMethod.setValue("SHA1");
        signatureMethod.setValue("DSA_SHA1");
        method.selectToggle(enveloped);
        //default value
        ObservableList<String> listDigestMethod = digestMethod.getItems();
        listDigestMethod.add("SHA1");
        listDigestMethod.add("SHA256");
        listDigestMethod.add("SHA512");
        listDigestMethod.add("SHA512");
        digestMethod.setItems(listDigestMethod);

        ObservableList<String> listSignatureMethod = signatureMethod.getItems();
        listSignatureMethod.add("DSA_SHA1");
        listSignatureMethod.add("RSA_SHA1");
//        listSignatureMethod.add("HMAC_SHA1");
        signatureMethod.setItems(listSignatureMethod);

        Sign signOperation = new Sign();

        chooseXml.setOnAction(event -> {
            Stage stage = (Stage) XmlSign.getScene().getWindow();
            File file = SaveObject.chooseFile(stage, TypeFile.xml);
            try {
                Document document = ConvertFile.readDocument(file);
                signOperation.setDocument(document);
                directoryXml.setText(file.getAbsolutePath());
                signOperation.setXmlFilePath(file.getAbsolutePath());
            } catch (IOException | ParserConfigurationException | SAXException e) {
                message.setText("Couldn't read xml file");
                e.printStackTrace();
            }
        });

        choosePrivateKey.setOnAction(event -> {
            Stage stage = (Stage) XmlSign.getScene().getWindow();
            File file = SaveObject.chooseFile(stage, TypeFile.key);
            directoryPrivateKey.setText(file.getAbsolutePath());
            signOperation.setPrivateKey(file);
        });

        choosePublicKey.setOnAction(event -> {
            Stage stage = (Stage) XmlSign.getScene().getWindow();
            File file = SaveObject.chooseFile(stage, TypeFile.key);
            directoryPublicKey.setText(file.getAbsolutePath());
            signOperation.setPublicKey(file);
        });

        sign.setOnAction(event -> {
            signOperation.setSelectedDigestMethod(digestMethod.getValue());
            signOperation.setSelectedSignature(signatureMethod.getValue());
            RadioButton button = (RadioButton) method.getSelectedToggle();
            signOperation.setTransform(button.getText());
            try {
                signOperation.signXml();
            } catch (KeyException | java.security.KeyException | SaveObjectException e) {
                message.setText(e.getMessage());
                e.printStackTrace();
            }
        });

    }

}
