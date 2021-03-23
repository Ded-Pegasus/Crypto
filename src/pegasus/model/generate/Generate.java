package pegasus.model.generate;

import oracle.security.crypto.core.RSAPrivateKey;
import pegasus.model.utils.SaveObject;

import java.io.File;
import java.io.IOException;
import java.security.*;

public class Generate {

    public static void generateKeyPair(String pathPublicKey, String pathPrivateKey, String algorithm) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

//        File file = new File("D:\\rsaPrivateKey");
//        PrivateKey privateKey1 = new RSAPrivateKey(file);

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        SaveObject.savePrivateKey(pathPrivateKey, privateKey);
        SaveObject.savePublicKey(pathPublicKey, publicKey);
    }
}
