package com.mb_medical_clinic_be.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.apache.commons.codec.binary.Hex;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    private static final String PASSWORD_PREFIX = "-pwd;";

    @Override
    public String encode(CharSequence rawPassword) {
        if (StringUtils.isBlank(rawPassword)) {
            return null;
        } else if (StringUtils.startsWith(rawPassword, PASSWORD_PREFIX)) {
            return rawPassword.toString();
        }

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }

        md.update(rawPassword.toString().getBytes(StandardCharsets.UTF_8));

        byte[] raw = md.digest();

        return "-pwd;" + new String(Hex.encodeHex(raw));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return StringUtils.startsWith(encodedPassword, PASSWORD_PREFIX)
                ? StringUtils.equals(this.encode(rawPassword), encodedPassword)
                : StringUtils.equals(rawPassword, encodedPassword);
    }
}
