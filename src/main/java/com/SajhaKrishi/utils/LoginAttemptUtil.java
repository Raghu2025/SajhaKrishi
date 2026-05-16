package com.SajhaKrishi.utils;

/**
 * Utility class to handle login attempt tracking and account lockout logic
 */
public class LoginAttemptUtil {
	
	// Configuration constants
	public static final int MAX_FAILED_ATTEMPTS = 5;
	public static final long ACCOUNT_LOCK_DURATION_MINUTES = 15;
	
	/**
	 * Converts minutes to milliseconds
	 */
	public static long getAccountLockDurationMillis() {
		return ACCOUNT_LOCK_DURATION_MINUTES * 60 * 1000;
	}
	
	/**
	 * Checks if account should be locked based on current time and lock expiration
	 */
	public static boolean isAccountLocked(Long accountLockedUntil) {
		if (accountLockedUntil == null) {
			return false;
		}
		
		long currentTime = System.currentTimeMillis();
		return currentTime < accountLockedUntil;
	}
	
	/**
	 * Calculates the lock expiration time (current time + lock duration)
	 */
	public static long calculateLockExpirationTime() {
		return System.currentTimeMillis() + getAccountLockDurationMillis();
	}
	
	/**
	 * Gets remaining lock time in minutes
	 */
	public static long getRemainingLockTimeMinutes(Long accountLockedUntil) {
		if (accountLockedUntil == null || !isAccountLocked(accountLockedUntil)) {
			return 0;
		}
		
		long remainingMillis = accountLockedUntil - System.currentTimeMillis();
		return (remainingMillis + 59999) / 60000; // Round up to nearest minute
	}
}
