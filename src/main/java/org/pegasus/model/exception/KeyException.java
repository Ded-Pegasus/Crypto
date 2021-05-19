package org.pegasus.model.exception;

public class KeyException extends Exception {
    private final int number = 78259;

    public KeyException(String message) {
        super("KeyException : " + message);
    }

    public int getNumber() {
        return number;
    }
}
