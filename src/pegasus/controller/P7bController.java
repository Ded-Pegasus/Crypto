package pegasus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pegasus.model.base64.Flag;
import pegasus.model.p7b.encode.EncodeToP7b;
import pegasus.model.p7b.encode.TypeFile;
import pegasus.model.utils.SaveObject;
import pegasus.model.scan.ReadObjectForDirectory;
import pegasus.model.scan.ReadObjectsForDirectory;
import sun.security.pkcs.PKCS7;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
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

    public Flag flag;

    @FXML
    void initialize() {

        execute.setOnAction(event -> {

            if (flag == Flag.Encode) {

                ArrayList<X509Certificate> certificates = (ArrayList<X509Certificate>) ReadObjectsForDirectory.scan(directoryCert.getText(), TypeFile.cer);
                ArrayList<X509CRL> crls = (ArrayList<X509CRL>) ReadObjectsForDirectory.scan(directoryCrls.getText(), TypeFile.crl);

                PKCS7 pkcs7 = EncodeToP7b.Encode(certificates, crls);

                try {
                    SaveObject.saveP7b(pkcs7, directoryP7b.getText(), "p7b");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }  else if (flag == Flag.Decode) {

                try {
                    PKCS7 pkcs7 = ReadObjectForDirectory.readPKCS7(directoryP7b.getText());
                    X509Certificate[] x509Certificate = pkcs7.getCertificates();
                    X509CRL[] x509CRL = pkcs7.getCRLs();
                    for (X509Certificate certificate : x509Certificate) {
                        SaveObject.saveCertificate(certificate, directoryCert.getText());
                    }
                    for (X509CRL crl : x509CRL) {
                        SaveObject.saveCrl(crl, directoryCrls.getText());
                    }
                } catch (IOException | InvalidNameException e) {
                    e.printStackTrace();
                }
            }
        });

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
            if (flag == Flag.Encode) {
                file = getPathToDirectory();
            } else if (flag == Flag.Decode) {
                file = getPathToFile();
            }

            if (file != null) {
                directoryP7b.setText(file.getAbsolutePath());
            }

        });
    }

    public void decodeAction(ActionEvent actionEvent) {
        flag = Flag.Decode;
    }

    public void encodeAction(ActionEvent actionEvent) {
        flag = Flag.Encode;
    }

    private File getPathToDirectory() {
        Stage stage = (Stage) P7b.getScene().getWindow();
        return SaveObject.chooseDirectory(stage);
    }

    private File getPathToFile() {
        Stage stage = (Stage) P7b.getScene().getWindow();
        return SaveObject.chooseFile(stage);
    }
}
