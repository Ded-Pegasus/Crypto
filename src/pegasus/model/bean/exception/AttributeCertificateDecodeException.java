package pegasus.model.bean.exception;

public class AttributeCertificateDecodeException extends Exception {
    private final int number = 3195;

    public AttributeCertificateDecodeException(String message) {
        super("AttributeCertificate Decode exception: " + message);
    }

    public int getNumber() {
        return number;
    }
}
