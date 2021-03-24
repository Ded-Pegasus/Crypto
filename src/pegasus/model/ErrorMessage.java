package pegasus.model;

public class ErrorMessage {
    private static String errorMessage;

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String errorMessage) {
        ErrorMessage.errorMessage = errorMessage;
    }

    public static void message(String message) {
        if (message!=null && !message.isEmpty()) {
            errorMessage = message;
        } else {
            errorMessage = "520 Unknown Error";
        }
        LoaderWindow loaderWindow = new LoaderWindow();
        loaderWindow.loadFxml(Windows.errorMessage);
    }
}
