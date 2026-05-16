package com.SajhaKrishi.utils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ForgetPasswordUtil {

    private static final Map<String, OTPEntry> OTP_STORE = new ConcurrentHashMap<>();
    private static final long OTP_EXPIRATION_MS = 10 * 60 * 1000; // 10 minutes

    public static String generateAndStoreOTP(String email) {
        String otp = String.format("%06d", new Random().nextInt(1_000_000));
        long expiresAt = System.currentTimeMillis() + OTP_EXPIRATION_MS;
        OTP_STORE.put(email.toLowerCase(), new OTPEntry(otp, expiresAt));
        return otp;
    }

    public static boolean verifyOtp(String email, String otp) {
        if (email == null || otp == null) return false;
        OTPEntry entry = OTP_STORE.get(email.toLowerCase());
        if (entry == null) return false;
        if (System.currentTimeMillis() > entry.expiresAt) {
            OTP_STORE.remove(email.toLowerCase());
            return false;
        }
        boolean ok = entry.otp.equals(otp);
        if (ok) OTP_STORE.remove(email.toLowerCase());
        return ok;
    }

    public static void clearOtp(String email) {
        if (email != null) OTP_STORE.remove(email.toLowerCase());
    }

    private static class OTPEntry {
        String otp;
        long expiresAt;

        OTPEntry(String otp, long expiresAt) {
            this.otp = otp;
            this.expiresAt = expiresAt;
        }
    }
}
