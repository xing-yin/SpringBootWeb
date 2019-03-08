package com.alany.other.security.data.core.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {

    public static String getBcryptPassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder(11);
        String encodePassword = encoder.encode(password);
        return encodePassword;
    }

}
