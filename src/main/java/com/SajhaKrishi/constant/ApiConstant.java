package com.SajhaKrishi.constant;

public class ApiConstant {
	public static final String LOGIN = "/login";
	public static final String REGISTER = "/register";
	public static final String LOGOUT = "/logout";
	public static final String FORGET_PASSWORD = "/forget-password";
	public static final String KISSAN_EQUIPMENT = "/kisan/equipment";
	public static final String OWNER_EQUIPMENT = "/owner/equipment";
	public static final String BOOKING = "/booking";
	public static final String DASHBOARD = "/dashboard";
	public static final String ADD = "/add";
	public static final String EDIT = "/edit";
	public static final String DELETE = "/delete";
	public static final String LIST = "/list";
	public static final String UNAUTHORIZED = "/unauthorized";
	public static final String DETAIL = "/details";
	
	public static final String HOME = "/home";
	
	
	
	public static final String USER_SESSION_KEY = "user";
	
    // Static resource paths
    public static final String CSS = "/css/";
    public static final String JS = "/js/";
    public static final String IMAGES = "/assets/";
    public static final String UPLOADS = "/uploads/";
    
    public static final String SAVED_IMAGE_PATH = "D:/eclipse-workspace/SajhaKrishi/src/main/webapp";
    public static final String EQUIPMENT_SAVED_IMAGE_PATH = "/uploads/equipment";

	public static String getLogin() {
		return LOGIN;
	}

	public static String getRegister() {
		return REGISTER;
	}

	public static String getLogout() {
		return LOGOUT;
	}

	public static String getKissanEquipment() {
		return KISSAN_EQUIPMENT;
	}

	public static String getOwnerEquipment() {
		return OWNER_EQUIPMENT;
	}

	public static String getDashboard() {
		return DASHBOARD;
	}

	public static String getAdd() {
		return ADD;
	}

	public static String getEdit() {
		return EDIT;
	}

	public static String getList() {
		return LIST;
	}

	public static String getDelete() {
		return DELETE;
	}

	public static String getUnauthorized() {
		return UNAUTHORIZED;
	}

	public static String getDetail() {
		return DETAIL;
	}

	public static String getHome() {
		return HOME;
	}

	public static String getUserSessionKey() {
		return USER_SESSION_KEY;
	}

	public static String getCss() {
		return CSS;
	}

	public static String getJs() {
		return JS;
	}

	public static String getImages() {
		return IMAGES;
	}

	public static String getUploads() {
		return UPLOADS;
	}

	public static String getSavedImagePath() {
		return SAVED_IMAGE_PATH;
	}

	public static String getEquipmentSavedImagePath() {
		return EQUIPMENT_SAVED_IMAGE_PATH;
	}
	
}
