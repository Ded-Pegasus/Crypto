package pegasus.model.scan;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import pegasus.model.p7b.encode.TypeFile;
import sun.misc.BASE64Decoder;
import sun.security.x509.X509CRLImpl;
import sun.security.x509.X509CertImpl;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Scanner;

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
            System.err.println(e);
        }
        return fileNames;
    }

    public static ArrayList<?> scan(String directory, TypeFile typeFile) {
        directory += "\\";

        ArrayList<String> filesNames = scanRepository(directory, typeFile);

        if (typeFile.equals(TypeFile.cer)) {
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
            } catch (CRLException | IOException e) {
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
            X509Certificate certificate = new X509CertImpl(fileInputStream);
            certificates.add(certificate);
        }
        return certificates;
    }

    private static ArrayList<X509CRL> readX509Crls(String directory, ArrayList<String> fileNames)
            throws IOException, CRLException {

        ArrayList<X509CRL> x509CRLS = new ArrayList<>();
        for (String fileName : fileNames) {
            Scanner scanner = new Scanner(new File(directory + fileName));
            X509CRL crl = new X509CRLImpl(readBase64Crl(scanner));
            x509CRLS.add(crl);
        }
        return x509CRLS;
    }

    private static byte[] readBase64Crl(Scanner scanner) throws IOException {
        String beginCert = "-----BEGIN X509 CRL-----";
        String endCert = "-----END X509 CRL-----";
        StringBuilder byteCertificate = new StringBuilder();
        String analize = "";
        if (scanner.nextLine().equals(beginCert)) {
            while (!analize.equals(endCert)) {
                byteCertificate.append(analize);
                analize = scanner.nextLine();
            }
        }
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(byteCertificate.toString().getBytes());
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(byteArrayInputStream);
    }
}
