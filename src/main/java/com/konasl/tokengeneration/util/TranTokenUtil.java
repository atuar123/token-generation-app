package com.konasl.tokengeneration.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.nio.charset.Charset;

public class TranTokenUtil {git 

    public static String generateToken(String currentTime, String secretKey, String message) {
        return "KMV1:" + currentTime + ":" + generateHMac(secretKey, message);
    }

    private static String generateHMac(String secretKey, String message) {

        Charset charset = Charset.defaultCharset();

        HmacAlgorithms algorithm = HmacAlgorithms.HMAC_SHA_256;

        byte[] secretKeyByte = secretKey.getBytes(charset);

        byte[] bodyByte = message.getBytes(charset);

        byte[] mac = HmacUtils.getInitializedMac(algorithm, secretKeyByte).doFinal(bodyByte);

        byte[] encodeBase64 = Base64.encodeBase64(mac);

        return new String(encodeBase64, charset);
    }
}
