package com.ming.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

/**
 * @author 邹明
 */
public class UploadUtils {
    /**
     * 秘钥16位
     */
    private static final String KEY_64STR = "FontManagementSystemAA";
    /**
     * 重新形成秘钥，SecretKey是Key的子类
     */
    private static final SecretKey SECRET_KEY = new SecretKeySpec(Base64.decodeBase64(KEY_64STR), "AES");


    /**
     * 对文件夹名加密
     */
    public static String aesEncrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
            //加密后的数据，首先将字符串转为byte数组，然后加密，为便于保存先转为base64
            str = Base64.encodeBase64URLSafeString(cipher.doFinal(str.getBytes()));
            //解密
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 对文件夹名解密
     */
    public static String aesDecrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            //将加密组件的模式改为解密
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
            //和加密相反，先解base64，再解密，最后将byte数组转为字符串
            str = new String(cipher.doFinal(Base64.decodeBase64(str)), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) {
            return "";
        }
        return filePath.substring(lastPoi + 1);
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        if (file == null) {
            return null;
        }
        String extension = getFileExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }


}
