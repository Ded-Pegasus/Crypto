package org.pegasus.model.generate;

import org.pegasus.model.utils.SaveObject;

import java.io.IOException;
import java.security.*;

public class Generate {

    public static void generateKeyPair(String pathPublicKey, String pathPrivateKey, String algorithm) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();


        SaveObject.savePrivateKey(pathPrivateKey, privateKey);
        SaveObject.savePublicKey(pathPublicKey, publicKey);
    }
}
