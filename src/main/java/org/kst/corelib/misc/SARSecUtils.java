package org.kst.corelib.misc;

public class SARSecUtils {
	
	public static String encrypt(String username, String password)
	{
		if(username == null || password == null)
		{
			return "";
		}
		else
		{
			String unicode = "";
			String concat = password+username+String.format("%02d", username.length());
			String asciiString = "";
			for(int i=0; i< concat.length(); i++) asciiString = asciiString + String.format("%02X", (int)concat.charAt(i));				
			
			unicode = unicode + String.format("%03d", (int) asciiString.charAt(0));
			for(int i=1; i<asciiString.length(); i++)
			{
				unicode = unicode + ":" + String.format("%03d", (int) asciiString.charAt(i));
			}
			return unicode;
		}
			
	}
	
	public static String[] decrypt(String encryptedString)
	{
		String[] decrypted = new String[2];
		String asciiString = "";
		String concat = "";
		for(String unicode : encryptedString.split(":")) asciiString += (char) Integer.parseInt(unicode);		
		for(int i=0; i<asciiString.length(); i=i+2) concat += (char) Integer.parseInt(asciiString.substring(i, i+2),16);
		
		int usernameLength = Integer.parseInt(concat.substring(concat.length()-2,concat.length()));
		
		decrypted[0] = concat.substring(concat.length()-usernameLength-2,concat.length()-2);
		decrypted[1] = concat.substring(0, concat.length()-usernameLength-2);
		return decrypted;
	}
	
	public static void main(String[] args) {
		
		String a = encrypt("abcd", "asdfeeee");
		System.out.println(a);
		String[] s = decrypt(a);
		System.out.println(s[0]);
		System.out.println(s[1]);
	}
}
