package org.pegasus.model.exception;

public class PKCS7EncodeException extends Exception {
    private final int number = 3381;

    public PKCS7EncodeException(String message) {
        super("PKCS#7 encode exception: " + message);
    }

    public int getNumber() {
        return number;
    }
}
