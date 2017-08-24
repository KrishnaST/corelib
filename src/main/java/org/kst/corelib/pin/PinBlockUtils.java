package org.kst.corelib.pin;

import org.kst.corelib.crypt.TrippleDES;
import org.kst.corelib.crypt.TrippleDES.MODE;
import org.kst.corelib.crypt.TrippleDES.PADDING;
import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;

public class PinBlockUtils {
	
	public static String decryptPinBlock_TPK(String encryptedPinBlock, String TPK)
	{
		try {
			return TrippleDES.decrypt(encryptedPinBlock, TPK, MODE.ECB, PADDING.NoPadding);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String encryptPinBlock_TPK(String pinBlock, String TPK)
	{
		try {
			return TrippleDES.encrypt(pinBlock, TPK, MODE.ECB, PADDING.NoPadding);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPinblock_TPK(String PAN, String PIN, String TPK, PinBlock.PinBlockFormat pinBlockFormat)
	{
		String PINBLOCK = PinBlock.getPinBlock(PAN, PIN, pinBlockFormat);
		try {
			return ByteUtils.byteArrayToHexString(TrippleDES.encrypt(HexUtils.hexStringToByteArray(PINBLOCK), HexUtils.hexStringToByteArray(TPK), TrippleDES.MODE.ECB, TrippleDES.PADDING.NoPadding));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(getPinblock_TPK("6078210100000032", "1234", "75161CDC859467CBBCF7B33B9D54BF32", PinBlock.PinBlockFormat.ISO1));
		//System.out.println(decryptPinBlock_TPK("4B51036BB03C63EC", "a0a071e7db39781eccb1188153ee8790"));
	}
	
}
