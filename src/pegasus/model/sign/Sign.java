package pegasus.model.sign;

import org.w3c.dom.Document;
import pegasus.model.bean.ConvertFile;
import pegasus.model.exception.KeyException;
import pegasus.model.exception.SaveObjectException;
import pegasus.model.utils.SaveObject;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;

public class Sign {

    private Document document;
    private String selectedDigestMethod;
    private String selectedSignature;
    private String xmlFilePath;
    private String transform;
    private File privateKey;
    private File publicKey;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getSelectedDigestMethod() {
        return selectedDigestMethod;
    }

    public void setSelectedDigestMethod(String selectedDigestMethod) {
        this.selectedDigestMethod = selectedDigestMethod;
    }

    public String getSelectedSignature() {
        return selectedSignature;
    }

    public void setSelectedSignature(String selectedSignature) {
        this.selectedSignature = selectedSignature;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public File getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(File privateKey) {
        this.privateKey = privateKey;
    }

    public File getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(File publicKey) {
        this.publicKey = publicKey;
    }

    public String getTransform() {
        return transform;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }

    public void signXml() throws KeyException, java.security.KeyException, SaveObjectException {

        XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
        PrivateKey privateKey = ConvertFile.convertToPrivateKey(this.privateKey, selectedSignature);
        PublicKey publicKey = ConvertFile.convertFileToPublicKey(this.publicKey, selectedSignature);
        DOMSignContext domSignCtx = new DOMSignContext(privateKey, document.getDocumentElement());
        Reference ref = null;
        SignedInfo signedInfo = null;
        try {
            ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
                    Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
                            (TransformParameterSpec) null)), null, null);
            signedInfo = xmlSigFactory.newSignedInfo(
                    xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                            (C14NMethodParameterSpec) null),
                    xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                    Collections.singletonList(ref));
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException ex) {
            ex.printStackTrace();
        }
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(publicKey);
        KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(kv));
        XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
        try {
            //Sign the document
            xmlSignature.sign(domSignCtx);
        } catch (MarshalException ex) {
            ex.printStackTrace();
        } catch (XMLSignatureException ex) {
            ex.printStackTrace();
        }

        try {
            SaveObject.saveXml(document, xmlFilePath, "SignedXml");
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
            throw new SaveObjectException("signed xml not save " + e.getMessage());
        }
    }
}
