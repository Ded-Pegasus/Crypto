package org.pegasus.model.exception;

public class ASN1ExceptionParse extends Exception {
    private final int number = 3196;

    public ASN1ExceptionParse(String message) {
        super("ASN1ExceptionParse: " + message);
    }

    public int getNumber() {
        return number;
    }
}
