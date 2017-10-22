package esform.encrypt;

/**
 * Created by zhuminghua on 16/3/15.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
public class DesUtil {
    /**
     * 加密、解密key.
     */
    private static final String PASSWORD_CRYPT_KEY = "ks4SH&SZ";
    /**
     * 加密算法,可用 DES,DESede,Blowfish.
     */
    private final static String ALGORITHM = "DES";
    static Logger logger = LoggerFactory.getLogger(DesUtil.class);

    public static String encrypt(String value) {
        String rs = null;
        if (StringUtils.isEmpty(value)) {
            return rs;
        }
        try {
            DESKeySpec ks = new DESKeySpec(makeKeyBytes());

            Key k = SecretKeyFactory.getInstance("DES").generateSecret(ks);

            AlgorithmParameterSpec params_iv = new IvParameterSpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));

            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, k, params_iv);

            byte code[] = c.doFinal(value.getBytes("UTF-8"));

            rs = DatatypeConverter.printBase64Binary(code);
            return rs;
        } catch (Exception e) {
            logger.error("DES加密出错", e);
            return rs;
        }

    }

    public static String decrypt(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        byte[] bytesrc = DatatypeConverter.parseBase64Binary(value);
        byte[] retByte = null;
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));

            DESKeySpec ks = new DESKeySpec(makeKeyBytes());
            Key k = SecretKeyFactory.getInstance("DES").generateSecret(ks);

            IvParameterSpec iv = new IvParameterSpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, k, iv);

            retByte = cipher.doFinal(bytesrc);
        } catch (Exception e) {
            logger.error("DES解码出错 - value[{}]", value);
            return null;
        }
        return new String(retByte);
    }


    private static byte[] makeKeyBytes() throws UnsupportedEncodingException {
        byte kw[] = PASSWORD_CRYPT_KEY.getBytes("UTF-8");
        byte kb[] = new byte[8];
        System.arraycopy(kw, 0, kb, 0, kw.length);
        return kb;
    }

}
