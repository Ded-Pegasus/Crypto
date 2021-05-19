package org.pegasus.model.base64;

import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.pegasus.model.exception.AttributeCertificateDecodeException;
import org.pegasus.model.exception.CRLsDecodeException;
import org.pegasus.model.exception.CertificateDecodeException;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public abstract class ConvertBase64ToObject {

    public static List<X509Certificate> base64ToCertificate(String[] encodeCertificates) throws CertificateDecodeException {

        List<X509Certificate> certificates = new ArrayList<>();

        if (encodeCertificates[0].isEmpty()) {
            return certificates;
        }

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            for (String encodeCertificate : encodeCertificates) {
                byte[] decode = Base64.getDecoder().decode(encodeCertificate.getBytes());
                X509Certificate c = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(decode));
                certificates.add(c);
            }
        }catch (Exception e) {
            throw new CertificateDecodeException(e.getMessage());
        }

        return certificates;
    }

    public static List<X509CRL> base64ToCrl(String[] encodeCrls) throws CRLsDecodeException {

        List<X509CRL> crls = new ArrayList<>();

        if (encodeCrls[0].isEmpty()) {
            return crls;
        }

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            for (String encodeCrl : encodeCrls) {
                byte[] decode = Base64.getDecoder().decode(encodeCrl.getBytes());
                X509CRL c = (X509CRL) cf.generateCRL(new ByteArrayInputStream(decode));
                crls.add(c);
            }
        } catch (Exception e) {
            throw new CRLsDecodeException(e.getMessage());
        }
        return crls;
    }

    public static List<X509AttributeCertificateHolder> base64ToAC(String[] encodeAC) throws AttributeCertificateDecodeException {

        List<X509AttributeCertificateHolder> attributeCertificates = new ArrayList<>();

        if (encodeAC[0].isEmpty()) {
            return attributeCertificates;
        }
        try {
            for (String s : encodeAC) {
                byte[] decode = Base64.getDecoder().decode(s.getBytes());
                X509AttributeCertificateHolder x509AttributeCertificateHolder = new X509AttributeCertificateHolder(decode);
                attributeCertificates.add(x509AttributeCertificateHolder);
            }
        }catch (Exception e) {
            throw new AttributeCertificateDecodeException(e.getMessage());
        }

        return attributeCertificates;
    }
}