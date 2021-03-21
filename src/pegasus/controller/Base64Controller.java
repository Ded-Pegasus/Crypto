package pegasus.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import pegasus.model.base64.ConvertObjectToBase64;
import pegasus.model.base64.ConvertBase64ToObject;
import pegasus.model.base64.Flag;
import pegasus.model.bean.exception.AttributeCertificateDecodeException;
import pegasus.model.bean.exception.CRLsDecodeException;
import pegasus.model.bean.exception.CertificateDecodeException;
import pegasus.model.save.SaveObject;
import pegasus.model.scan.ReadObjectForDirectory;

import javax.naming.InvalidNameException;

public class Base64Controller {

    public Text message;

    public TextField pathDirectory;

    public Flag flag;

    @FXML
    private AnchorPane Base64;

    @FXML
    private RadioButton decodeFlag;

    @FXML
    private RadioButton encodeFlag;

    @FXML
    private Button executeButton;

    @FXML
    private Button addDirectory;

    @FXML
    private ToggleGroup method;

    @FXML
    private TextArea text;

    @FXML
    void initialize() {

        addDirectory.setOnAction(event -> {
            if (flag == Flag.Decode) {
                Stage stage = (Stage) Base64.getScene().getWindow();
                File file = SaveObject.chooseDirectory(stage);

                if (file != null) {
                    pathDirectory.setText(file.getAbsolutePath());
                }
            } else {
                Stage stage = (Stage) Base64.getScene().getWindow();
                File file = SaveObject.chooseFile(stage);

                if (file !=null) {
                    pathDirectory.setText(file.getAbsolutePath());
                }
            }
        });

        executeButton.setOnAction(event -> {
            RadioButton button = (RadioButton) method.getSelectedToggle();

            if (button != null && text != null && pathDirectory != null) {
                String[] textData = new String[1];
                textData[0] = text.getText();
                if (flag == Flag.Decode) {
                    message.setText(saveFile(textData));
                } else {
                    String base64 = "";
                    try {
                        X509Certificate x509Certificate = ReadObjectForDirectory.readCertificate(pathDirectory.getText());
                        base64 = java.util.Base64.getEncoder().encodeToString(x509Certificate.getEncoded());
                        message.setText("Cert parse success");
                    } catch (CertificateException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        X509CRL x509CRL = ReadObjectForDirectory.readX509Crl(pathDirectory.getText());
                        base64 = java.util.Base64.getEncoder().encodeToString(x509CRL.getEncoded());
                        message.setText("Crl parse success");
                    } catch (FileNotFoundException | CRLException e) {
                        e.printStackTrace();
                    }
                    try {
                        X509AttributeCertificateHolder x509AttributeCertificate =
                                ReadObjectForDirectory.readX509Ac(pathDirectory.getText());
                        message.setText("AC parse success");
                        base64 = java.util.Base64.getEncoder().encodeToString(x509AttributeCertificate.getEncoded());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    text.setText(base64);
                }
            }
        });

    }

    private String saveFile(String[] textData) {
        textData[0] = textData[0].replaceAll("\\s", "");

        try {
            List<X509Certificate> certificates = ConvertBase64ToObject.base64ToCertificates(textData);
            SaveObject.saveCertificate(certificates.get(0), pathDirectory.getText());
            return "Certificate saved";
        } catch (CertificateDecodeException e) {
            System.out.println("Not a certificate");
        } catch (IOException e) {
            System.out.println("Save error");
            e.printStackTrace();
            return "Save certificate error";
        } catch (InvalidNameException e) {
            e.printStackTrace();
            return "Save certificate error";
        }
        try {
            List<X509AttributeCertificateHolder> ac = ConvertBase64ToObject.base64ToAC(textData);
            SaveObject.saveAc(ac.get(0), pathDirectory.getText());
            return "AC saved";
        } catch (AttributeCertificateDecodeException e) {
            System.out.println("Not a AC");
        } catch (IOException e) {
            System.out.println("Save error");
            return "Save AC error";
        }
        try {
            List<X509CRL> crls = ConvertBase64ToObject.base64ToCrls(textData);
            SaveObject.saveCrl(crls.get(0), pathDirectory.getText());
            return "Crls saved";
        } catch (CRLsDecodeException e) {
            System.out.println("Not a Crls");
        } catch (IOException e) {
            System.out.println("Save error");
            return "Save Crls error";
        } catch (InvalidNameException e) {
            e.printStackTrace();
            return "Save Crls error";
        }
        return "NoN type";
    }

    private String openFile(String textData) {
        try {
            return ConvertObjectToBase64.readCertificates(textData);
        } catch (FileNotFoundException | CertificateException e) {
            System.out.println("Not a Certificate");
        }

        try {
            return ConvertObjectToBase64.readX509Crl(textData);
        } catch (IOException | CRLException e) {
            System.out.println("Not a Crl");
        }
        return "Error";
    }

    public void decodeAction(ActionEvent actionEvent) {
        flag = Flag.Decode;
    }

    public void encodeAction(ActionEvent actionEvent) {
        flag = Flag.Encode;
    }
}