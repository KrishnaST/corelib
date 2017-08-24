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


public class DES {
	
	private static final String DES = "DES";
	
	public static enum MODE{
		ECB, CBC
	}
	
	public static enum PADDING{
		NoPadding, PKCS5Padding
	}
	
	public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("DES");
		System.out.println(cipher.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, DES));
		return cipher.doFinal(data);		
	}

	public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, DES));
		return cipher.doFinal(data);		
	}
	
	public static byte[] encrypt(byte[] data, byte[] key, MODE mode, PADDING padding)  throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		Cipher cipher = Cipher.getInstance("DES/"+mode+"/"+padding);
		if(mode == MODE.CBC) cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, DES),new IvParameterSpec(new byte[8]));
		else cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, DES));
		return cipher.doFinal(data);		
	}
	
	public static byte[] decrypt(byte[] data, byte[] key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		Cipher cipher = Cipher.getInstance("DES/"+mode+"/"+padding);
		if(mode == MODE.CBC) cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, DES), new IvParameterSpec(new byte[8]));
		else cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, DES));
		return cipher.doFinal(data);		
	}
	
	
	public static void main(String[] args) {
		String data = "73C8619EF83B0810";
		String key 	= "1C1C1C1C1C1C1C1C";
		
		try {
			byte[] bytes  = org.kst.corelib.crypt.DES.decrypt(HexUtils.hexStringToByteArray(data), HexUtils.hexStringToByteArray(key), MODE.ECB, PADDING.NoPadding);
			System.out.println(ByteUtils.byteArrayToHexString(bytes));
			//System.out.println(ByteUtils.byteArrayToHexString(org.kst.corelib.crypt.DES.decrypt(HexUtils.hexStringToByteArray(data), HexUtils.hexStringToByteArray(key), MODE.ECB, PADDING.NoPadding)));
			//System.out.println(DataConverter.byteArrayToHexString(com.sil.corelib.crypt.DES.decrypt(bytes, DataConverter.hexStringToByteArray(key))));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
