package org.kst.corelib.crypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;

public class TrippleDES {
	
	private static final String DESede = "DESede";
	
	public static enum MODE{
		ECB, CBC
	}
	
	public static enum PADDING{
		NoPadding, PKCS5Padding
	}
	
	public static String encrypt(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		if(key.length() == 32) key = key+key.substring(0, 16);
		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), DESede));
		return ByteUtils.byteArrayToHexString(cipher.doFinal(HexUtils.hexStringToByteArray(data)));		
	}
	
	public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		if(key.length == 16) key = concat(key, 0, 16, key, 0, 8);
		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, DESede));
		return cipher.doFinal(data);		
	}
	
	public static String decrypt(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		if(key.length() == 32) key = key+key.substring(0, 16);
		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), DESede));
		return ByteUtils.byteArrayToHexString(cipher.doFinal(HexUtils.hexStringToByteArray(data)));		
	}
	
	public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		if(key.length == 16) key = concat(key, 0, 16, key, 0, 8);
		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, DESede));
		return cipher.doFinal(data);		
	}
	
	public static String encrypt(String data, String key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		if(key.length() == 32) key = key+key.substring(0, 16);
		Cipher cipher = Cipher.getInstance("DESede/"+mode+"/"+padding);
		if(mode == MODE.CBC) cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), DESede),new IvParameterSpec(new byte[8]));
		else cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), DESede));
		return ByteUtils.byteArrayToHexString(cipher.doFinal(HexUtils.hexStringToByteArray(data)));		
	}
	
	public static byte[] encrypt(byte[] data, byte[] key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		if(key.length == 16) key = concat(key, 0, 16, key, 0, 8);
		Cipher cipher = Cipher.getInstance("DESede/"+mode+"/"+padding);
		if(mode == MODE.CBC) cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, DESede),new IvParameterSpec(new byte[8]));
		else cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, DESede));
		return cipher.doFinal(data);		
	}
	
	public static String decrypt(String data, String key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		if(key.length() == 32) key = key+key.substring(0, 16);
		Cipher cipher = Cipher.getInstance("DESede/"+mode+"/"+padding);
		if(mode == MODE.CBC) cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), DESede),new IvParameterSpec(new byte[8]));
		else cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), DESede));
		return ByteUtils.byteArrayToHexString(cipher.doFinal(HexUtils.hexStringToByteArray(data)));		
	}
	
	public static byte[] decrypt(byte[] data, byte[] key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		if(key.length == 16) key = concat(key, 0, 16, key, 0, 8);
		Cipher cipher = Cipher.getInstance("DESede/"+mode+"/"+padding);
		if(mode == MODE.CBC) cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, DESede),new IvParameterSpec(new byte[8]));
		else cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, DESede));
		return cipher.doFinal(data);		
	}
	
	private static byte[] concat(byte[] src1, int src1Pos, int src1Len, byte[] src2, int src2Pos, int src2Len)
	{
		byte[] dest = new byte[src1Len+src2Len];
		System.arraycopy(src1, src1Pos, dest, 0, src1Len);
		System.arraycopy(src2, src2Pos, dest, src1Len, src2Len);
		return dest;
	}
	

}
