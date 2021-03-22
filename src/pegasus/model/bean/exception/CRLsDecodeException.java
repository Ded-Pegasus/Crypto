package pegasus.model.bean.exception;

public class CRLsDecodeException extends Exception {
    private final int number = 6694;

    public CRLsDecodeException(String message) {
        super("CRL(s) Decode exception: " + message);
    }

    public int getNumber() {
        return number;
    }
}
