package org.kst.corelib.crypt;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;

public class AES {

	private static final String AES = "AES";

	public static enum MODE {
		ECB, CBC
	}

	public static enum PADDING {
		NoPadding, PKCS5Padding, PKCS7Padding
	}


	private static byte[] IV = HexUtils.hexStringToByteArray("36B7C8E702DE9BE14718FBBC07C1F9DA");
	
	
	public static String encrypt(String data, String key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(AES + "/" + mode + "/" + padding);
		if (mode == MODE.CBC) cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), AES), new IvParameterSpec(new byte[16]));
		else cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), AES));
		return ByteUtils.byteArrayToHexString(cipher.doFinal(HexUtils.hexStringToByteArray(data)));
	}


	public static byte[] encrypt(byte[] data, byte[] key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(AES + "/" + mode + "/" + padding);
		if (mode == MODE.CBC) cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, AES), new IvParameterSpec(new byte[16]));
		else cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, AES));
		return cipher.doFinal(data);
	}


	public static String decrypt(String data, String key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(AES + "/" + mode + "/" + padding);
		if (mode == MODE.CBC) cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), AES), new IvParameterSpec(new byte[16]));
		else cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(HexUtils.hexStringToByteArray(key), AES));
		return ByteUtils.byteArrayToHexString(cipher.doFinal(HexUtils.hexStringToByteArray(data)));
	}


	public static byte[] decrypt(byte[] data, byte[] key, MODE mode, PADDING padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(AES + "/" + mode + "/" + padding);
		if (mode == MODE.CBC) cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, AES), new IvParameterSpec(IV));
		else cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, AES));
		return cipher.doFinal(data);
	}


	public static byte[] concat(byte[] src1, int src1Pos, int src1Len, byte[] src2, int src2Pos, int src2Len) {
		byte[] dest = new byte[src1Len + src2Len];
		System.arraycopy(src1, src1Pos, dest, 0, src1Len);
		System.arraycopy(src2, src2Pos, dest, src1Len, src2Len);
		return dest;
	}

	public static int[] concat(int[] src1,  int[] src2) {
		int[] dest = new int[src1.length + src2.length];
		System.arraycopy(src1, 0, dest, 0, src1.length);
		System.arraycopy(src2, 0, dest, src1.length, src2.length);
		return dest;
	}


	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		System.out.println(new String(HexUtils.hexStringToByteArray("31303030313232303136303432353135333735383030303032363030303030343835383038343837323031353130323330303030303031303030303030303030303031")));
		long start = System.currentTimeMillis();
		
		System.out.println();
		System.out.println("decrypted  : "+new String(decrypt(HexUtils.hexStringToByteArray("745ADEEE05097B456FC1B8118ADA101E657FD711E77563859B680FB0B135DD74B48122827C57BE32FCDF840115D060C0"), HexUtils.hexStringToByteArray("80AF4CCEDC8ED3AFF5A84041378FBADA42A9696B37D6C9C344EE1057C8091745"), MODE.CBC, PADDING.PKCS5Padding)));
		//System.out.println(ByteUtils.byteArrayToHexString(decrypt(HexUtils.hexStringToByteArray("95EAAFC94F64C3C3E0F1177DA38C22E1A5E00EDC20A73FAC6CC93DAC0730DE71"), HexUtils.hexStringToByteArray("80AF4CCEDC8ED3AFF5A84041378FBADA42A9696B37D6C9C344EE1057C8091745"), MODE.CBC, PADDING.PKCS5Padding)	));
		System.out.println(System.currentTimeMillis()-start);
		
	}

}
