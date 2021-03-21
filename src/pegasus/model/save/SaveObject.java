package pegasus.model.save;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import sun.security.pkcs.PKCS7;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public class SaveObject extends SaveFile {

    public static File chooseDirectory(Stage stage) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(stage);
    }

    public static File chooseFile(Stage stage) {
        final FileChooser fileChooser = new FileChooser();
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
