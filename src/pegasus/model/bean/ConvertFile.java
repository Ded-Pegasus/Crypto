package pegasus.model.bean;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;

public class ConvertFile {

    public static Document readDocument(File file) throws IOException, ParserConfigurationException, SAXException {
        return getDocumentBuilder().parse(file);
    }

    public static KeyStore readKeyStore(File file, String keyStorePassword) throws KeyStoreException, IOException,
            CertificateException, NoSuchAlgorithmException {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream fileInputStream = new FileInputStream(file);
        keystore.load(fileInputStream, keyStorePassword.toCharArray());
        return keystore;
    }

    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder();
    }

    public static PrivateKey convertToPrivateKey(File file, String selectedDigestMethod) throws IOException, KeyException {
        if (selectedDigestMethod.contains("RSA")) {
            RSAPrivateKey privateKey = new oracle.security.crypto.core.RSAPrivateKey(file);
        } else if (selectedDigestMethod.contains("DSA")) {
            DSAPrivateKey privateKey = new sun.security.provider.DSAPrivateKey(file.);
        } else {
            throw new KeyException("Undefined key");
        }
    }

}
