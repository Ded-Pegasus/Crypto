package pegasus.model.utils;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import pegasus.model.base64.ConvertBase64ToObject;
import pegasus.model.bean.exception.AttributeCertificateDecodeException;
import pegasus.model.bean.exception.CRLsDecodeException;
import pegasus.model.bean.exception.CertificateDecodeException;
import pegasus.model.p7b.bean.TypeFile;
import sun.security.pkcs.PKCS7;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.List;

public class SaveObject extends FileUtils {

    public static String saveFile(String[] textData, TextField pathDirectory) {
        textData[0] = textData[0].replaceAll("\\s", "");

        try {
            List<X509Certificate> certificates = ConvertBase64ToObject.base64ToCertificate(textData);
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
            List<X509CRL> crls = ConvertBase64ToObject.base64ToCrl(textData);
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

    public static File chooseDirectory(Stage stage) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(stage);
    }

    public static File chooseFile(Stage stage, TypeFile typeFile) {
        final FileChooser fileChooser = new FileChooser();
        if (typeFile.equals(TypeFile.certificates)) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Crls, Certificates",
                            "*.crl", "*.cer", "*.pem", "*.der")
            );
        } else if (typeFile.equals(TypeFile.p7b)){
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PKCS#7",
                            "*.p7b"));
        }
        return fileChooser.showOpenDialog(stage);
    }

    public static File saveCrl(X509CRL x509CRL, String nameFolder) throws IOException, InvalidNameException {
        Files.createDirectories(Paths.get(nameFolder + "\\"));
        String name = getCertificateName(x509CRL.getIssuerX500Principal().getName());
        File file = new File(nameFolder + "\\" + name + ".crl");
        pemWriter(file, x509CRL);
        return file;
    }

    public static File saveCertificate(X509Certificate X509Certificate, String nameFolder) throws IOException, InvalidNameException {
        Files.createDirectories(Paths.get(nameFolder + "\\"));
        String name = getCertificateName(X509Certificate.getIssuerX500Principal().getName());
        File file = new File(nameFolder + "\\" + name + ".cer");
        pemWriter(file, X509Certificate);
        return file;
    }

    public static File saveAc(X509AttributeCertificateHolder x509AttributeCertificateHolder, String nameFolder) throws IOException {
        Files.createDirectories(Paths.get(nameFolder + "\\"));
        String name = "Ac";
        File file = new File(nameFolder + "\\" + name + ".acr");
        pemWriter(file, x509AttributeCertificateHolder);
        return file;
    }

    public static void saveP7b(PKCS7 p7b, String directory, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(directory + "\\" + name + ".p7b");
        p7b.encodeSignedData(fos);
        fos.close();
    }

}
