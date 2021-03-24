package pegasus.model.generate;

import oracle.security.crypto.core.RSAPrivateKey;
import org.apache.commons.io.FileUtils;
import pegasus.model.utils.SaveObject;
import sun.security.provider.DSAPrivateKey;

import java.io.File;
import java.io.IOException;
import java.security.*;

public class Generate {

    public static void generateKeyPair(String pathPublicKey, String pathPrivateKey, String algorithm) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

//        File file = new File("D:\\RSAPrivateKey");
//        PrivateKey privateKey1 = new RSAPrivateKey(file);

//        File file = new File("D:\\DSAPrivateKey");
//        PrivateKey privateKey1 = new DSAPrivateKey(file);

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();


        SaveObject.savePrivateKey(pathPrivateKey, privateKey);
        SaveObject.savePublicKey(pathPublicKey, publicKey);
    }
}
