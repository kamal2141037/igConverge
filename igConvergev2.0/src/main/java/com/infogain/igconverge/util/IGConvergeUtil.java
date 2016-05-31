package com.infogain.igconverge.util;

import com.infogain.igconverge.service.EmployeeHandler;

/**
 * This class will contain common logic which can be used through all layers.
 * 
 * @author Infogain Dev Team
 * 
 */
public class IGConvergeUtil {

	public static EmployeeHandler employeeHandler;
	public static String igConvergeDownloadPage = "https://mobileapps.infogain.com/igconverge.html";
	public static String igConvergeAndroidUrl = "https://mobileapps.infogain.com/igConverge_Resources/igConverge.apk";
	public static String SERVER_ANDROID_API_KEY = "AIzaSyAnFQ1sSLNhZTuUgsmphX95yeLtUvxcuIU";
	public static String MESSAGE_KEY = "notification";
	
	public static boolean checkAdminUser(String employeeId){
		System.out.println("emp id from util class "+employeeId);
		return employeeHandler.checkAdminOrNot(employeeId);
	}

	public static enum NotificationType{
		Zero("0"), One("1"), Two("2");

		protected String value;
		  
		NotificationType(String value){
	          this.value=value;
	     }
		
	     public String getValue() {
	           return this.value;
	     }
	}
	
}
