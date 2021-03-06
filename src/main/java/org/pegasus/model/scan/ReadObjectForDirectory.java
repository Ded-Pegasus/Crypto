package org.pegasus.model.scan;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import sun.security.pkcs.PKCS7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.*;

public abstract class ReadObjectForDirectory {

    public static X509Certificate readCertificate(String directory) throws CertificateException, FileNotFoundException {
        directory +=  "\\";
        FileInputStream fileInputStream = new FileInputStream(directory);
        return (X509Certificate) getCertificateFactory().generateCertificate(fileInputStream);
    }

    public static X509CRL readX509Crl(String directory) throws FileNotFoundException, CRLException, CertificateException {
        directory +=  "\\";
        FileInputStream fileInputStream = new FileInputStream(directory);
        return (X509CRL) getCertificateFactory().generateCRL(fileInputStream);
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

    private static CertificateFactory getCertificateFactory() throws CertificateException {
        return CertificateFactory.getInstance("X.509");
    }
}
