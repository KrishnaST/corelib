package com.sil.corelib.test;

import java.util.ResourceBundle;

public class Keys {
	
	public static String HSMIP;
	public static int HSMPORT;
	
	public static String CVK1;
	public static String CVK2;
	
	public static String PVK;
	public static String PVKLMK;
	
	public static String BDK;
	public static String BDKLMK;
	
	public static String TPK;
	public static String TPKLMK;
	
	public static String TMK;
	public static String TMKLMK;
	
	static {
		try
		{
			ResourceBundle resourceBundle = ResourceBundle.getBundle("hsm");
			HSMIP = resourceBundle.getString("HSMIP");
			HSMPORT = Integer.parseInt(resourceBundle.getString("HSMPORT"));
			
			CVK1 = resourceBundle.getString("CVK1");
			CVK2 = resourceBundle.getString("CVK2");
			
			PVK = resourceBundle.getString("PVK");
			PVKLMK = resourceBundle.getString("PVKLMK");
			
			TPK = resourceBundle.getString("TPK");
			TPKLMK = resourceBundle.getString("TPKLMK");
			
			BDK = resourceBundle.getString("BDK");
			BDKLMK = resourceBundle.getString("BDKLMK");
			
			TMK = resourceBundle.getString("TMK");
			TMKLMK = resourceBundle.getString("TMKLMK");
		} catch (Exception e) {e.printStackTrace();}
	}
	
}
