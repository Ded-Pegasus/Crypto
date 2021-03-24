package pegasus.model.utils;

import org.bouncycastle.openssl.PEMWriter;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.*;

public abstract class FileUtils {

    public static void pemWriter(File file, Object object) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        PEMWriter pemWriter = new PEMWriter(new PrintWriter(outputStream));
        pemWriter.writeObject(object);
        pemWriter.flush();
        pemWriter.close();
        outputStream.close();
    }

    public static String getCertificateName(String dn) throws InvalidNameException {
        LdapName ldapDN = new LdapName(dn);
        for(Rdn rdn: ldapDN.getRdns()) {
            if (rdn.getType().equals("CN")) {
                byte[] byteCn = rdn.getValue().toString().getBytes();
                String nameDecode = decodeBMPString(byteCn);
                if(nameDecode.equals("CN-not-found")) {
                    //System.out.println(rdn.getValue().toString());
                    return rdn.getValue().toString();
                } else {
                    //System.out.println(nameDecode);
                    return nameDecode;
                }
            }
        }
        return "CN-not-found";
    }

    private static String decodeBMPString(byte[] bmpString) {
        if (bmpString.length % 2 != 0) {
            return "CN-not-found";
        }
        int len = bmpString.length;
        if (len > 2 && bmpString[len - 1] == 0 && bmpString[len - 2] == 0) {
            byte[] tmp = new byte[len - 2];
            System.arraycopy(bmpString, 0, tmp, 0, len - 2);
            bmpString = tmp;
        }
        StringBuilder decodeString = new StringBuilder();
        for (int i = 0; i < len; i += 2) {
            char symbol;
            if (bmpString[i] > 4) {
                symbol = (char) bmpString[i];
                decodeString.append(symbol);
                --i;
            } else {
                symbol = (char) ((bmpString[i] << 8) + bmpString[i + 1]);
                decodeString.append(symbol);
            }
        }
        return decodeString.toString();
    }

    public static void saveKey(String directory, String name, byte[] key) throws IOException {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream(directory + "\\"
                    + name));
            dos.write(key);
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null)
                try {
                    dos.close();
                } catch (IOException e) {
                    throw new IOException(e);
                }
        }
    }
}
