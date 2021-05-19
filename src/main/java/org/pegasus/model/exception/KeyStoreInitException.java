package org.pegasus.model.exception;

public class KeyStoreInitException extends Exception {
    private final int number = 6699;

    public KeyStoreInitException(String message) {
        super("KeyStoreInitException: " + message);
    }

    public int getNumber() {
        return number;
    }
}
