package pegasus.model.xml_dsig;

import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;

public abstract class SearchAlgorithms {

    public static String searchDigestAlgorithm(String selected) {
        switch (selected) {
            case "SHA1":
                return DigestMethod.SHA1;
            case "SHA256":
                return DigestMethod.SHA256;
            case "SHA512":
                return DigestMethod.SHA512;
            case "RIPEMD160":
                return DigestMethod.RIPEMD160;
            default:
                return "Unsupported algorithm";
        }
    }

    public static String searchSignatureMethod(String selected) {
        switch (selected) {
            case "DSA_SHA1":
                return SignatureMethod.DSA_SHA1;
            case "RSA_SHA1":
                return SignatureMethod.RSA_SHA1;
            case "HMAC_SHA1":
                return SignatureMethod.HMAC_SHA1;
            default:
                return "Unsupported algorithm";
        }
    }
}
