package org.pegasus.model.utils;

import oracle.security.crypto.core.RSAPrivateKey;
import oracle.security.crypto.core.RSAPublicKey;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.pegasus.model.exception.KeyException;
import sun.security.provider.DSAPrivateKey;
import sun.security.provider.DSAPublicKey;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

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

    public static PrivateKey convertFileToPrivateKey(File file, String algorithm) throws
            org.pegasus.model.exception.KeyException {
        if (algorithm.contains("RSA")) {
            try {
                return new RSAPrivateKey(file);
            } catch (IOException e) {
                throw new KeyException(e.getMessage());
            }
        } else if (algorithm.contains("DSA")) {
            return generateDSAPrivateKey(file);
        } else {
            throw new KeyException("Undefined key algorithm");
        }
    }

    private static PrivateKey generateDSAPrivateKey(File file)
            throws org.pegasus.model.exception.KeyException {
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            return new DSAPrivateKey(bytes);
        } catch (IOException | InvalidKeyException e) {
            throw new KeyException(e.getMessage());
        }
    }

    public static PublicKey convertFileToPublicKey(File file, String algorithm) throws KeyException {
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            if (algorithm.contains("DSA")) {
                return new DSAPublicKey(bytes);
            } else if (algorithm.contains("RSA")) {
                return new RSAPublicKey(bytes);
            } else {
                throw new KeyException("Undefined key algorithm");
            }
        }
        catch (IOException  | InvalidKeyException e) {
            e.printStackTrace();
            throw new KeyException(e.getMessage());
        }
    }

}
