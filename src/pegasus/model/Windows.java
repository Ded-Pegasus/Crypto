package pegasus.model;

public class Windows {
    private static final String base64 = "/pegasus/view/Base64.fxml";
    private static final String p7b = "/pegasus/view/p7b.fxml";
    private static final String asn1 = "/pegasus/view/Asn1.fxml";
    private static final String sign = "/pegasus/view/Sign.fxml";

    public static String getBase64() {
        return base64;
    }

    public static String getP7b() {
        return p7b;
    }

    public static String getAsn1() {
        return asn1;
    }

    public static String getSign(){ return sign; }
}
