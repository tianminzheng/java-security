package com.juejin.security.mfa.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class SecurityCodeUtils {

    private SecurityCodeUtils() {}

    public static String generateSecurityCode() {
        String code;

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            code = String.valueOf(random.nextInt(900000) + 100000);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("安全码生成失败");
        }

        return code;
    }
}
