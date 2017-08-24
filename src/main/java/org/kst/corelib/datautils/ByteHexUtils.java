package org.kst.corelib.datautils;

import java.security.SecureRandom;

import org.pmw.tinylog.Logger;

public class ByteHexUtils {

	private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
	
	public static int hexToByte(char ch) {
		if ('0' <= ch && ch <= '9') return ch - '0';
		if ('A' <= ch && ch <= 'F') return ch - 'A' + 10;
		if ('a' <= ch && ch <= 'f') return ch - 'a' + 10;
		return -1;
	}
	
	public static byte hexPairToByte(String hexString) {
		int h = hexToByte(hexString.charAt(0));
		int l = hexToByte(hexString.charAt(1));
		if (h == -1 || l == -1) throw new IllegalArgumentException("contains illegal character for hexBinary: " + hexString);
		byte byteValue = (byte) (h * 16 + l);
		return byteValue;
	}
	
	public static byte[] hexStringToByteArray(String hexString) {
		if (hexString.length() % 2 != 0) hexString = "0" + hexString;
		byte[] byteArray = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2) {
			byteArray[i / 2] = hexPairToByte(hexString.substring(i, i + 2));
		}
		return byteArray;
	}
	
	public static String xor(String hexString1, String hexString2){
		if(hexString1.length() != hexString2.length()){
			Logger.error("Hex strings of unequal length");
			return null;
		}
		byte[] byteArray1 = hexStringToByteArray(hexString1);
		byte[] byteArray2 = hexStringToByteArray(hexString2);
		String xoredHexString = byteArrayToHexString(xor(byteArray1, byteArray2));
		return xoredHexString.substring(xoredHexString.length()-hexString1.length());
	}
	
	public static String getRandomHexString(int len){
		byte[] randomBytes = new byte[len/2+1];
		new SecureRandom().nextBytes(randomBytes);
		return byteArrayToHexString(randomBytes).substring(0, len);
	}
	
	
	public static String byteToHexPair(byte b) {
		String hexPair = "";
		hexPair += hexCode[(b >> 4) & 0xF];
		hexPair += hexCode[(b & 0xF)];
		return hexPair;
	}
	
	public static String byteArrayToHexString(byte[] data) {
		StringBuilder s = new StringBuilder(data.length * 2);
		for (byte b : data) {
			s.append(hexCode[(b >> 4) & 0xF]);
			s.append(hexCode[(b & 0xF)]);
		}
		return s.toString();
	}
	
	public static byte[] xor(byte[] byteArray1, byte[] byteArray2){
		if(byteArray1.length != byteArray2.length) {
			Logger.error("Byte arrays of unequal length.");
			return null;
		}
		byte[] xoredArray = new byte[byteArray1.length];
		for(int i=0 ;i < byteArray1.length; i++){
			xoredArray[i] = (byte) (byteArray1[i] ^ byteArray2[i]); 
		}
		return xoredArray;
	}
	
	public static byte[] getRandomBytes(int len){
		byte[] randomBytes = new byte[len];
		new SecureRandom().nextBytes(randomBytes);
		return randomBytes;
	}
	
	public static StringBuilder byteToBinaryString(byte b) {
		return StringUtils.padLeft(Integer.toBinaryString(b & 0xFF), '0', 8);
	}
}
