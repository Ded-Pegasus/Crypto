package org.pegasus.model.p7b;

import javafx.scene.text.Text;
import org.pegasus.model.ErrorMessage;
import org.pegasus.model.base64.bean.CodeFlag;
import org.pegasus.model.exception.PKCS7EncodeException;
import org.pegasus.model.bean.TypeFile;
import org.pegasus.model.p7b.encode.EncodeToP7b;
import org.pegasus.model.scan.ReadObjectForDirectory;
import org.pegasus.model.scan.ReadObjectsForDirectory;
import org.pegasus.model.utils.SaveObject;
import sun.security.pkcs.PKCS7;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

public class ExecuteEvent {

    public static void execute(Text directoryCrls, Text directoryCert, Text directoryP7b,
                               CodeFlag codeFlag, Text message) {

        if (codeFlag == CodeFlag.Encode) {

            ArrayList<X509Certificate> certificates = (ArrayList<X509Certificate>) ReadObjectsForDirectory.scan(directoryCert.getText(), TypeFile.cer);
            ArrayList<X509CRL> crls = (ArrayList<X509CRL>) ReadObjectsForDirectory.scan(directoryCrls.getText(), TypeFile.crl);

            try {
                if (certificates.isEmpty() && crls.isEmpty()) {
                    message.setText("Certificates in directory not found");
                    PKCS7EncodeException encodeException = new PKCS7EncodeException("Certificates in directory not found");
                    encodeException.printStackTrace();
                }
                PKCS7 pkcs7 = EncodeToP7b.Encode(certificates, crls);
                SaveObject.saveP7b(pkcs7, directoryP7b.getText(), "p7b");
                message.setText("PKCS7 file generate and saved success");
            } catch (IOException e) {
                ErrorMessage.message(e.getMessage());
                message.setText("Couldn't save p7b file");
                e.printStackTrace();
            } catch (NullPointerException e) {
                ErrorMessage.message(e.getMessage());
                message.setText("List certificates or crls is null");
                //e.printStackTrace();
            }
        }  else if (codeFlag == CodeFlag.Decode) {
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
                message.setText("Decode p7b and save files success");
            } catch (IOException | InvalidNameException e) {
                message.setText("Couldn't read p7b file or save files from p7b");
                ErrorMessage.message(e.getMessage());
                //e.printStackTrace();
            }
        }
    }
}
