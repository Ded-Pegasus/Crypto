package pegasus.model.exception;

public class SaveObjectException extends Exception {
    private final int number = 86451;

    public SaveObjectException(String message) {
        super("SaveObjectException: " + message);
    }

    public int getNumber() {
        return number;
    }
}
