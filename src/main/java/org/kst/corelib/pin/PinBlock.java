package org.kst.corelib.pin;

import org.kst.corelib.crypt.TrippleDES;
import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;
import org.kst.corelib.datautils.StringUtils;


public class PinBlock {
	
	public static enum PinBlockFormat {
		IBM3624,
		ISO0,
		ISO1,
		ISO3,
	}
	
	public static String getPinBlock(String PAN, String PIN, PinBlockFormat pinBlockFormat){
		System.out.println(pinBlockFormat);
		/**
		 * @param PIN
		 * @return PIN+"FFFFFFFFFFFF"
		 */
		if(pinBlockFormat == PinBlockFormat.IBM3624){
			return StringUtils.padRight(PIN, 'F', 16).toString();
		}
		else if(pinBlockFormat == PinBlockFormat.ISO0){
			/**
			 * @param PIN & PAN
			 * @return (0+PIN.length+PIN+FFFFFFFFFF) ^ 0000+Rightmost 12 digits of PAN excluding check digit
			 */
			String formatedPIN = StringUtils.padRight("0"+Integer.toHexString(PIN.length())+PIN, 'F', 16).toString();
			String formatedPAN = StringUtils.padLeft((PAN.length() > 13 ? PAN.substring(PAN.length()-13,PAN.length()-1) : StringUtils.padLeft(PAN.substring(0, PAN.length()-1), '0', 12).toString()),'0' , 16).toString();
			return HexUtils.xor(formatedPAN, formatedPIN);
		}
		else if(pinBlockFormat == PinBlockFormat.ISO1){
			/**
			 * @param PIN
			 * @return 1+PIN.length+PIN+Randon Hex String
			 */
			String formatedPIN = "1"+Integer.toHexString(PIN.length())+PIN;
			return formatedPIN+HexUtils.getRandomHexString(16-formatedPIN.length());
		}
		else if(pinBlockFormat == PinBlockFormat.ISO3){
			/**
			 * @param PIN & PAN
			 * @return (3+PIN.length+PIN+Randon Hex String) ^ (Rightmost 12 digits of PAN excluding check digit)
			 */
			String formatedPIN = "3"+Integer.toHexString(PIN.length())+PIN;
			formatedPIN = formatedPIN+HexUtils.getRandomHexString(16-formatedPIN.length());
			String formatedPAN = "0000"+(PAN.length() > 13 ? PAN.substring(PAN.length()-13,PAN.length()-1) : StringUtils.padLeft(PAN.substring(0, PAN.length()-1), '0', 12).toString());
			return HexUtils.xor(formatedPIN, formatedPAN);
		}
		return null;		
	}
	
	
	/**
	 * @param PIN & PAN
	 * @return (0+PIN.length+PIN+FFFFFFFFFF) ^ 0000+Rightmost 12 digits of PAN excluding check digit
	 */
	public static String getISO0PinBlock(String PIN, String PAN)
	{
		String formatedPIN = String.format("%-16s", "0"+Integer.toString(PIN.length(),16)+PIN).replace(' ', 'F');
		String formatedPAN = "0000"+PAN.substring(3,15);
		return HexUtils.xor(formatedPIN, formatedPAN);
	}
	
	/**
	 * @param PIN
	 * @return 1+PIN.length+PIN+Randon Hex String
	 */
	public static String getISO1PinBlock(String PIN)
	{
		String formatedPIN = "1"+Integer.toHexString(PIN.length())+PIN;
		return formatedPIN+HexUtils.getRandomHexString(16-formatedPIN.length());
	}
	
	/**
	 * @param PIN & PAN
	 * @return (3+PIN.length+PIN+Randon Hex String) ^ (Rightmost 12 digits of PAN excluding check digit)
	 */
	public static String getISO3PinBlock(String PIN, String PAN)
	{
		String formatedPIN = "3"+Integer.toHexString(PIN.length())+PIN;
		System.out.println(formatedPIN);
		formatedPIN = formatedPIN+HexUtils.getRandomHexString(16-formatedPIN.length());
		String formatedPAN = "0000"+PAN.substring(3, 15);
		return HexUtils.xor(formatedPAN, formatedPIN);
	}
	
	/**
	 * @param PIN
	 * @return PIN+"FFFFFFFFFFFF"
	 */
	public static String getIBM3624PinBlock(String PIN)
	{
		return String.format("%-16s", PIN).replace(' ', 'F');				
	}
	
	public static String getISO1Pinblock_TPK(String PIN, String TPK)
	{
		String PINBLOCK = getISO1PinBlock(PIN);
		try {
			return ByteUtils.byteArrayToHexString(TrippleDES.encrypt(HexUtils.hexStringToByteArray(PINBLOCK), HexUtils.hexStringToByteArray(TPK), TrippleDES.MODE.ECB, TrippleDES.PADDING.NoPadding));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getPinBlock("1111111111111111", "9999", PinBlockFormat.ISO0));
		//System.out.println(getISO1Pinblock_TPK("1234", "75161CDC859467CBBCF7B33B9D54BF32"));
	}
	
}
