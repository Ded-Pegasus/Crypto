package pegasus.model.bean.exception;

public class CertificateDecodeException extends Exception {
    private final int number = 3192;

    public CertificateDecodeException(String message) {
        super("Certificate Decode Exception: " + message);
    }

    public int getNumber() {
        return number;
    }
}
