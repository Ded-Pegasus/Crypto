package pegasus.model.p7b.encode;

import oracle.security.crypto.asn1.ASN1Sequence;
import org.bouncycastle.cert.jcajce.JcaCRLStore;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.util.Store;
import pegasus.model.exception.PKCS7EncodeException;
import sun.security.pkcs.PKCS7;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.cert.*;
import java.util.List;

public abstract class EncodeToP7b {

    public static PKCS7 Encode(List<X509Certificate> certList, List<X509CRL> crlList) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if (certList.isEmpty() && crlList.isEmpty()) {
            new PKCS7EncodeException("content is null").getStackTrace();
        }
        CMSSignedData signedData = null;
        try {
            Store certs = new JcaCertStore(certList);
            Store crl = new JcaCRLStore(crlList);

            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

            gen.addCertificates(certs);
            gen.addCRLs(crl);

            CMSTypedData msg = new CMSProcessableByteArray("777".getBytes());

            signedData = gen.generate(msg);

            byte[] decode = (signedData.getEncoded());
            ByteArrayInputStream byteArray = new ByteArrayInputStream(decode);
            ASN1Sequence asn1Sequence = new ASN1Sequence(byteArray);

            asn1Sequence.output(byteArrayOutputStream);
        } catch (CRLException | CertificateEncodingException | CMSException | IOException e ) {
            new PKCS7EncodeException(e.getMessage()).getStackTrace();
        }

        PKCS7 pkcs7 = null;
        try {
            pkcs7 = new PKCS7(signedData.getEncoded());
            return pkcs7;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pkcs7;
    }
}
