package org.pegasus.model.xml_dsig;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.pegasus.model.bean.ConvertFile;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import java.io.File;
import java.security.PublicKey;

public class Verification {
    private File publicKeyFile;
    private Document document;
    private String algorithm;

    public File getPublicKeyFile() {
        return publicKeyFile;
    }

    public void setPublicKeyFile(File publicKeyFile) {
        this.publicKeyFile = publicKeyFile;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

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
