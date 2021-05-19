package org.pegasus.model.exception;

public class CertificateDecodeException extends Exception {
    private final int number = 3192;

    public CertificateDecodeException(String message) {
        super("Certificate Decode exception: " + message);
    }

    public int getNumber() {
        return number;
    }
}
