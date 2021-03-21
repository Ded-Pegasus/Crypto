package pegasus.model.base64;

import org.bouncycastle.cert.X509CRLHolder;
import sun.security.x509.X509CertImpl;

import java.io.*;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;

public abstract class ConvertObjectToBase64 {

    public static String readCertificates(String directoryCertificate)
            throws FileNotFoundException, CertificateException {

            FileInputStream fileInputStream =
                    new FileInputStream(directoryCertificate);

            X509Certificate certificate = new X509CertImpl(fileInputStream);

        return Arrays.toString(Base64.getEncoder().encode(certificate.getEncoded()));
    }

    public static String readX509Crl(String directoryCrl)
            throws IOException, CRLException {

        FileInputStream fileInputStream =
                new FileInputStream(directoryCrl);

        X509CRLHolder x509CRLHolder = new X509CRLHolder(fileInputStream);
        return Arrays.toString(Base64.getEncoder().encode(x509CRLHolder.getEncoded()));
    }
}
