package wcNaver.security;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;


/**
 * Created by hoyoungher on 3/3/17.
 */
public class Rsa
{
    /**
     * Create enc key string.
     *
     * @param value
     *         the value
     * @param id
     *         the id
     * @param pwd
     *         the pwd
     *
     * @return the string
     *
     * @throws Exception
     *         the exception
     */
    public String createEncKey(String value,String id, String pwd) throws Exception
    {
            Rsa rsa = new Rsa();

            String encGetValue = value;

            String[] splitString = encGetValue.split(",");

            String sessionKey = splitString[0];
            String keyname    = splitString[1];
            String evalue     = splitString[2];
            String nvalue     = splitString[3];

            BigInteger modulus = new BigInteger(evalue, 16);
            BigInteger pubExp  = new BigInteger(nvalue, 16);

            KeyFactory       keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
            RSAPublicKey     key        = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);

            String encValue = rsa.encValue(sessionKey, id, pwd);

            String inputStr = encValue;

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] arrCipherData = cipher.doFinal(inputStr.getBytes());

            return byteArrayToHex(arrCipherData);
    }

    /**
     * @param sessionKey
     * @param id
     * @param pwd
     *
     * @return
     *
     * @throws Exception
     */
    private String encValue(String sessionKey, String id, String pwd) throws Exception
    {
        StringBuffer sbf = new StringBuffer();
        sbf.append(fromCharCode(sessionKey.length()));
        sbf.append(sessionKey);
        sbf.append(fromCharCode(id.length()));
        sbf.append(id);
        sbf.append(fromCharCode(pwd.length()));
        sbf.append(pwd);
        return sbf.toString();
    }

    /**
     * Byte array to hex string.
     *
     * @param a
     *         the a
     *
     * @return the string
     */
    public static String byteArrayToHex(byte[] a)
    {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    /**
     * From char code string.
     *
     * @param codePoints
     *         the code points
     *
     * @return the string
     */
    public String fromCharCode(int... codePoints) {
        return new String(codePoints, 0, codePoints.length);
    }

}
