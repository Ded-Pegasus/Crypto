package org.pegasus.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlAddress {

    public static URL urlMenu;
    public static URL urlAsn1;
    public static URL urlBase64;
    public static URL urlErrorMessage;
    public static URL urlP7b;
    public static URL urlSign;
    public static URL urlVerify;
    public static URL urlGenerateKey;

    static {
        try {
            urlMenu = new File("src/main/resources/view/Menu.fxml").toURI().toURL();
            urlAsn1 = new File("src/main/resources/view/Asn1.fxml").toURI().toURL();
            urlBase64 = new File("src/main/resources/view/Base64.fxml").toURI().toURL();
            urlErrorMessage = new File("src/main/resources/view/ErrorMessage.fxml").toURI().toURL();
            urlP7b = new File("src/main/resources/view/P7b.fxml").toURI().toURL();
            urlSign = new File("src/main/resources/view/Sign.fxml").toURI().toURL();
            urlVerify = new File("src/main/resources/view/Verify.fxml").toURI().toURL();
            urlGenerateKey = new File("src/main/resources/view/GenerateKey.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
