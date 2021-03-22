package pegasus.model.scan;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import pegasus.model.p7b.bean.TypeFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.*;
import java.util.ArrayList;

public abstract class ReadObjectsForDirectory {

    private static ArrayList<String> scanRepository(String directory, TypeFile type) {

        ArrayList<String> fileNames = new ArrayList<>();
        Path dir = Paths.get(directory);
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(dir, "*." + type.toString())) {
            for (Path entry : stream) {
                fileNames.add(entry.getFileName().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (type.equals(TypeFile.cer)) {
            fileNames.addAll(scanRepository(directory, TypeFile.der));
        }
        return fileNames;
    }

    public static ArrayList<?> scan(String directory, TypeFile typeFile) {
        directory += "\\";

        ArrayList<String> filesNames = scanRepository(directory, typeFile);

        if (typeFile.equals(TypeFile.cer) || typeFile.equals(TypeFile.der)) {
            ArrayList<X509Certificate> certificates = new ArrayList<>();
            try {
                certificates = readCertificates(directory, filesNames);
            } catch (FileNotFoundException | CertificateException e) {
                e.printStackTrace();
            }
            return certificates;

        } else if (typeFile.equals(TypeFile.crl)) {
            ArrayList<X509CRL> crls = new ArrayList<>();
            try {
                crls = readX509Crls(directory, filesNames);
            } catch (CRLException | IOException | CertificateException e) {
                e.printStackTrace();
            }
            return crls;

        } else if (typeFile.equals(TypeFile.acr)) {
            ArrayList<X509AttributeCertificateHolder> acs = new ArrayList<>();
            try {
                acs = readAc(directory, filesNames);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return acs;
        }

        return null;
    }

    private static ArrayList<X509AttributeCertificateHolder> readAc(String directory, ArrayList<String> fileNames)
            throws IOException {

        ArrayList<X509AttributeCertificateHolder> x509AttributeCertificateHolders = new ArrayList<>();
        for (String fileName : fileNames) {
            FileInputStream fileInputStream = new FileInputStream(directory + fileName);
            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            X509AttributeCertificateHolder certificate = new X509AttributeCertificateHolder(bytes);
            x509AttributeCertificateHolders.add(certificate);
        }
        return x509AttributeCertificateHolders;

    }

    private static ArrayList<X509Certificate> readCertificates(String directory, ArrayList<String> fileNames)
            throws FileNotFoundException, CertificateException {
        ArrayList<X509Certificate> certificates = new ArrayList<>();
        for (String fileName : fileNames) {
            FileInputStream fileInputStream =
                    new FileInputStream(directory + fileName);
            X509Certificate certificate = (X509Certificate) getCertificateFactory().generateCertificate(fileInputStream);
            certificates.add(certificate);
        }
        return certificates;
    }

    private static ArrayList<X509CRL> readX509Crls(String directory, ArrayList<String> fileNames)
            throws IOException, CRLException, CertificateException {

        ArrayList<X509CRL> x509CRLS = new ArrayList<>();
        for (String fileName : fileNames) {
            FileInputStream fileInputStream =
                    new FileInputStream(directory + fileName);
            X509CRL crl = (X509CRL) getCertificateFactory().generateCRL(fileInputStream);
            x509CRLS.add(crl);
        }
        return x509CRLS;
    }

    private static CertificateFactory getCertificateFactory() throws CertificateException {
        return CertificateFactory.getInstance("X.509");
    }
}
