package pegasus.model.asn1;

import javafx.scene.control.TextArea;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import pegasus.model.exception.ASN1ExceptionParse;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ExecuteEvent {

    public static String textToAsn1(String textData) throws NullPointerException, ASN1ExceptionParse {
        if (textData != null) {
            try {
                textData = textData.replaceAll("\n", "");
                byte[] decode = Base64.getDecoder().decode(textData);
                ASN1InputStream bIn = new ASN1InputStream(new ByteArrayInputStream(decode));
                ASN1Primitive obj = bIn.readObject();
                return ASN1Dump.dumpAsString(obj);
            } catch (Exception e) {
                throw new ASN1ExceptionParse("Couldn't read ASN.1 structure");
            }
        } else {
            throw new NullPointerException();
        }
    }
}
