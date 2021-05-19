package org.pegasus.model.xml_dsig;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.pegasus.model.bean.ConvertFile;
import org.pegasus.model.exception.KeyException;
import org.pegasus.model.exception.SaveObjectException;
import org.pegasus.model.utils.FileUtils;
import org.pegasus.model.utils.SaveObject;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
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
        PrivateKey privateKey = ConvertFile.convertFileToPrivateKey(this.privateKey, selectedSignature);
        PublicKey publicKey = ConvertFile.convertFileToPublicKey(this.publicKey, selectedSignature);
        DOMSignContext domSignCtx = null;
        Document docToSign = null;

        Reference ref = null;
        SignedInfo signedInfo = null;
        XMLObject obj = null;
        try {
            switch (transform) {
                case "Enveloped  ":
                    ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(
                            SearchAlgorithms.searchDigestAlgorithm(selectedDigestMethod), null),
                            Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
                                    (TransformParameterSpec) null)), null, null);
                    domSignCtx = new DOMSignContext(privateKey, document.getDocumentElement());
                    docToSign = document;
                    break;
                case "Enveloping  ": {
                    ref = xmlSigFactory.newReference("#object",
                            xmlSigFactory.newDigestMethod(
                                    SearchAlgorithms.searchDigestAlgorithm(selectedDigestMethod), null));
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    dbf.setNamespaceAware(true);
                    Document doc = dbf.newDocumentBuilder().newDocument();
                    docToSign = doc;
                    Node text = doc.createTextNode(FileUtils.documentToBase64(document));
                    XMLStructure content = new DOMStructure(text);
                    obj = xmlSigFactory.newXMLObject
                            (Collections.singletonList(content), "object", null, null);
                    domSignCtx = new DOMSignContext(privateKey, doc);
                    break;
                }
                case "Detached ": {
                    ref = xmlSigFactory.newReference(
                            document.getDocumentURI(), xmlSigFactory.newDigestMethod(
                                    SearchAlgorithms.searchDigestAlgorithm(selectedDigestMethod), null));
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    dbf.setNamespaceAware(true);
                    Document doc = dbf.newDocumentBuilder().newDocument();
                    docToSign = doc;
                    domSignCtx = new DOMSignContext(privateKey, doc);
                    break;
                }
            }
            signedInfo = xmlSigFactory.newSignedInfo(
                    xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                            (C14NMethodParameterSpec) null),
                    xmlSigFactory.newSignatureMethod(
                            SearchAlgorithms.searchSignatureMethod(selectedSignature), null),
                    Collections.singletonList(ref));
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | ParserConfigurationException
                | TransformerException ex) {
            ex.printStackTrace();
        }
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(publicKey);
        KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(kv));
        XMLSignature xmlSignature = null;
        if (transform.equals("Enveloped  ") || transform.equals("Detached "))
            xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
        if (transform.equals("Enveloping  "))
            xmlSignature = fac.newXMLSignature(signedInfo, keyInfo,
                    Collections.singletonList(obj), null, null);
        try {
            //Sign the document
            assert xmlSignature != null;
            xmlSignature.sign(domSignCtx);
        } catch (MarshalException ex) {
            ex.printStackTrace();
        } catch (XMLSignatureException ex) {
            ex.printStackTrace();
        }

        try {
            SaveObject.saveXml(docToSign, xmlFilePath, "SignedXml" + transform);
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
            throw new SaveObjectException("signed xml not save " + e.getMessage());
        }
    }
}
