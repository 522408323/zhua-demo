package demo;


import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author sulibo
 * @date 2019/9/10
 */

public class Demo {

    private static final String KEY = "a54gffhksssjj88877088jj";

    /**
     * 加密数据
     *
     * @return
     * @paramencryptString
     * @paramencryptKey
     * @throwsException
     */
    public static String encryptDES(String encryptString) throws Exception {

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getKey(), "DES"));
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
        return Base64.encodeBase64URLSafeString(encryptedData);
    }
    /***
     *解密数据
     *@paramdecryptString
     *@paramdecryptKey
     *@return
     *@throwsException
     */
    public static String decryptDES(String decryptString) throws Exception {

        decryptString = decryptString.replace("-", "+").replace("_", "/");
        byte[] sourceBytes = Base64.decodeBase64(decryptString);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKey(), "DES"));
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, "UTF-8");
    }
    /**
     * key不足8位补位
     *
     * @paramstring
     */
    public static byte[] getKey() {
        Key key = null;
        byte[] keyByte = KEY.getBytes();
        //创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        //将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, "DES");
        return key.getEncoded();
    }



    public static void main(String[] args) throws Exception {
        String clearText = "{\"appId\":\" e5f34af6fc3f461cad551b\",\"certId\":\"520181200004081336\",\"product\":\"CRED\",\"realName\":\"张路\"}}";
        String encryptText = encryptDES(clearText);
        System.out.println("加密后：" + encryptText);
        String decText ="oZIaL8__ZLGvn4RvK0Kvu-tYAWRxwgn3fPeg5nOAyBeJh3TKnAV8FZw5sOephEZT1LvxYtVegBSThCz992muRRji5vzGbj2ULsTQoYwLEv5wi6sz061fiV6bsAse8PvueMSPYOE4Lhw";
        if (encryptText.equals(decText)) {
            System.out.println("相同：");
        } else {
            System.out.println("不同：");
        }

    }
}
