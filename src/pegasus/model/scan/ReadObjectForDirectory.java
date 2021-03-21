package pegasus.model.scan;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import sun.security.pkcs.PKCS7;
import sun.security.x509.X509CRLImpl;
import sun.security.x509.X509CertImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public abstract class ReadObjectForDirectory {

    public static X509Certificate readCertificate(String directory) throws CertificateException, FileNotFoundException {

        directory +=  "\\";
        FileInputStream fileInputStream = new FileInputStream(directory);
        return new X509CertImpl(fileInputStream);
    }

    public static X509CRL readX509Crl(String directory) throws FileNotFoundException, CRLException {

        directory +=  "\\";
        FileInputStream fileInputStream = new FileInputStream(directory);
        return new X509CRLImpl(fileInputStream);
    }

    public static X509AttributeCertificateHolder readX509Ac(String directory) throws IOException {

        directory +=  "\\";
        FileInputStream fileInputStream = new FileInputStream(directory);
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        return new X509AttributeCertificateHolder(bytes);
    }

    public static PKCS7 readPKCS7(String directory) throws IOException {

        directory +=  "\\";
        FileInputStream fileInputStream = new FileInputStream(directory);
        return new PKCS7(fileInputStream);
    }
}
