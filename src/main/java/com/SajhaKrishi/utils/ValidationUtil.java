package com.SajhaKrishi.utils;

public class ValidationUtil {
	public static double parseDouble(String value) {
		return (value == null || value.trim().isEmpty()) ? 0.0 : Double.parseDouble(value);
	}

	public static int parseInt(String value) {
		return (value == null || value.trim().isEmpty()) ? 0 : Integer.parseInt(value);
	}

	public static boolean isNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}

	public static boolean isValidEmail(String email) {
		return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
	}

	public static boolean isValidPhone(String phone) {
		return phone != null && phone.matches("^[0-9]{10}$");
	}

}
