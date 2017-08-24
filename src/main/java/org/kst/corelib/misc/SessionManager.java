package org.kst.corelib.misc;
import java.util.Date;
import java.util.HashMap;

public class SessionManager {
	
	private static int sessionTimeout = 300000;
	private static final HashMap<String, Date> sessionMap = new HashMap<>();
	
	public static boolean isUserAlreadyLoggedIn(String userName)
	{
		Date lastRequestDate = sessionMap.get(userName);
		return !(lastRequestDate == null || new Date().getTime() - lastRequestDate.getTime() > sessionTimeout);
	}
	
	public static void setUserLoggon(String userName)
	{
		sessionMap.put(userName, new Date());
	}

	
	public static int getSessiontimeout() {
		return sessionTimeout;
	}

	
	public static int getSessionTimeout() {
		return sessionTimeout;
	}

	
	public static void setSessionTimeout(int sessionTimeout) {
		SessionManager.sessionTimeout = sessionTimeout;
	}

	
	
}
