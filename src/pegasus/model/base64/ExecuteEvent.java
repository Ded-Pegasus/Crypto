package pegasus.model.base64;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import pegasus.model.base64.bean.CreateFlag;
import pegasus.model.base64.bean.CodeFlag;
import pegasus.model.scan.ReadObjectForDirectory;
import pegasus.model.utils.SaveObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public class ExecuteEvent {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void execute(TextArea text, TextField pathDirectory, Text message,
                               ToggleGroup method, ToggleGroup createGroup) {

        RadioButton button = (RadioButton) method.getSelectedToggle();
        RadioButton createButton = (RadioButton) createGroup.getSelectedToggle();

        if (createButton.getText().equals(CreateFlag.file.name()) && text != null) {
            if (button != null && pathDirectory != null) {
                int errorInt = 0;
                String[] textData = new String[1];
                textData[0] = text.getText();
                if (button.getText().equals(CodeFlag.Encode.name())) {
                    message.setText(SaveObject.saveFile(textData, pathDirectory));
                } else if (button.getText().equals(CodeFlag.Decode.name())) {
                    String base64 = "";
                    try {
                        X509Certificate x509Certificate = ReadObjectForDirectory.readCertificate(pathDirectory.getText());
                        base64 = java.util.Base64.getEncoder().encodeToString(x509Certificate.getEncoded());
                        message.setText("Cert parse success");
                        System.out.println(ANSI_GREEN + "Certificate parse success");
                    } catch (CertificateException | FileNotFoundException e) {
                        System.out.println(ANSI_YELLOW + "Not a certificate");
                        errorInt++;
                    }
                    try {
                        X509CRL x509CRL = ReadObjectForDirectory.readX509Crl(pathDirectory.getText());
                        base64 = java.util.Base64.getEncoder().encodeToString(x509CRL.getEncoded());
                        message.setText("Crl parse success");
                        System.out.println(ANSI_GREEN + "Crl parse success");
                    } catch (FileNotFoundException | CRLException | CertificateException e) {
                        System.out.println(ANSI_YELLOW + "Not a crl");
                        errorInt++;
                    }
                    try {
                        X509AttributeCertificateHolder x509AttributeCertificate =
                                ReadObjectForDirectory.readX509Ac(pathDirectory.getText());
                        base64 = java.util.Base64.getEncoder().encodeToString(x509AttributeCertificate.getEncoded());
                        message.setText("AC parse success");
                        System.out.println(ANSI_GREEN + "AC parse success");
                    } catch (IOException e) {
                        System.out.println(ANSI_YELLOW + "Not a AttributeCertificate");
                        errorInt++;
                    }
                    if (errorInt != 3) {
                        text.setText(base64);
                    } else {
                        message.setText("Undefined file!");
                        System.out.println(ANSI_RED + "Undefined file!");
                    }
                }
            }
        } else if (createButton.getText().equals(CreateFlag.text.name()) && text != null) {
            String textData = text.getText();
            if (button.getText().equals(CodeFlag.Encode.name())) {
                try {
                    String base64 = java.util.Base64.getEncoder().encodeToString(textData.getBytes(StandardCharsets.UTF_8));
                    message.setText("Text encoded success");
                    text.setText(base64);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (button.getText().equals(CodeFlag.Decode.name())) {
                try {
                    byte[] bytes = java.util.Base64.getDecoder().decode(textData);
                    String decodeBase64 = new String(bytes);
                    message.setText("Text decoded success");
                    text.setText(decodeBase64);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
