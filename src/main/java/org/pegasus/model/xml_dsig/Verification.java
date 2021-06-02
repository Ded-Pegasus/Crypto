package org.pegasus.model.xml_dsig;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.pegasus.model.utils.ConvertFile;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import java.io.File;
import java.security.PublicKey;

@Getter
@Setter
public class Verification {
    private File publicKeyFile;
    private Document document;
    private String algorithm;

    public boolean verificationXmlSignature() throws Exception {

            PublicKey publicKey = ConvertFile.convertFileToPublicKey(publicKeyFile, algorithm);

            boolean validFlag;
            NodeList nl = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
            if (nl.getLength() == 0) {
                throw new Exception("No XML Digital Signature Found, document is discarded");
            }
            DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            XMLSignature signature = fac.unmarshalXMLSignature(valContext);
            validFlag = signature.validate(valContext);
            return validFlag;
    }
}
