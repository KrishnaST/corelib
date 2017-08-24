package org.kst.corelib.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;


public class DUKPT_PIN {

    private static final String ALG_DES = "DES";
    private static final String ALG_TRIPLE_DES = "DESede";
    private static final String TRANSFORMATION = "DESede/CBC/NoPadding";
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
    
    public static String encrypt(String ksn, String bdk, String data) throws Exception {
    	byte[] ksnbytes = hexStringToByteArray(ksn);
        byte[] databytes = hexStringToByteArray(data);
        byte[] bdkbytes = hexStringToByteArray(bdk);        
        return encrypt(ksnbytes, bdkbytes, databytes);
    }
    
    public static String encrypt(byte[] ksn, byte[] bdk, byte[] data) throws Exception
    {
    	final Cipher TDESCipher = Cipher.getInstance(ALG_TRIPLE_DES);
    	final Cipher DESCipher = Cipher.getInstance(ALG_DES);
    	byte[] ipek = generateIPEK(ksn, bdk, TDESCipher);
        byte[] datakey = getDatekey(ksn, ipek, TDESCipher, DESCipher);
        byte[] encrypted = encryptTDES(data, datakey, false);
        return byteArrayToHexString(encrypted);
    }
    
    public static String decrypt(String ksn, String bdk, String data) throws Exception {
    	byte[] ksnbytes = hexStringToByteArray(ksn);
        byte[] databytes = hexStringToByteArray(data);
        byte[] bdkbytes = hexStringToByteArray(bdk);        
        return decrypt(ksnbytes, bdkbytes, databytes);
    }
    
    public static String decrypt(byte[] ksn, byte[] bdk, byte[] data) throws Exception
    {
    	final Cipher TDESCipher = Cipher.getInstance(ALG_TRIPLE_DES);
    	final Cipher DESCipher = Cipher.getInstance(ALG_DES);
    	byte[] ipek = generateIPEK(ksn, bdk, TDESCipher);
        byte[] datakey = getDatekey(ksn, ipek, TDESCipher, DESCipher);
        byte[] decrypted = decryptTDES(data, datakey);
        return byteArrayToHexString(decrypted);
    }
    
    private static byte[] getDatekey(byte[] ksn, byte[] ipek, Cipher TDESCipher, Cipher DESCipher) throws Exception {
        byte[] key = subArray(ipek, 0, 16);
        byte[] cnt = new byte[3];
        cnt[0] = (byte) (ksn[7] & 0x1F);
        cnt[1] = ksn[8];
        cnt[2] = ksn[9];
        byte[] temp = new byte[8];
        System.arraycopy(ksn, 2, temp, 0, 6);
        temp[5] &= 0xE0;
        int shift = 0x10;
        while (shift > 0) {
            if ((cnt[0] & shift) > 0) {
                temp[5] |= shift;
                key = NRKGP(key, temp, DESCipher);
            }
            shift >>= 1;
        }

        shift = 0x80;
        while (shift > 0) {
            if ((cnt[1] & shift) > 0) {
                temp[6] |= shift;
                key = NRKGP(key, temp, DESCipher);
            }
            shift >>= 1;
        }

        shift = 0x80;
        while (shift > 0) {
            if ((cnt[2] & shift) > 0) {
                temp[7] |= shift;
                key = NRKGP(key, temp, DESCipher);
            }
            shift >>= 1;
        }
        key[7] ^= 0xFF;
        key[15] ^= 0xFF;
        return key;
    }

    private static byte[] NRKGP(byte[] key, byte[] ksn, Cipher DESCipher) throws Exception {
        byte[] key_temp = subArray(key, 0, 8);
        byte[] temp = new byte[8];
        for (int i = 0; i < 8; i++) {
            temp[i] = (byte) (ksn[i] ^ key[8 + i]);
        }
        byte[] res = encryptDES(temp, key_temp, DESCipher);
        byte[] key_r = res;
        for (int i = 0; i < 8; i++) {
            key_r[i] ^= key[8 + i];
        }
        key_temp[0] ^= 0xC0;
        key_temp[1] ^= 0xC0;
        key_temp[2] ^= 0xC0;
        key_temp[3] ^= 0xC0;
        key[8] ^= 0xC0;
        key[9] ^= 0xC0;
        key[10] ^= 0xC0;
        key[11] ^= 0xC0;

        temp = new byte[8];
        for (int i = 0; i < 8; i++) {
            temp[i] = (byte) (ksn[i] ^ key[8 + i]);
        }

        res = encryptDES(temp, key_temp, DESCipher);
        byte[] key_l = res;
        for (int i = 0; i < 8; i++) {
            key[i] = (byte) (key_l[i] ^ key[8 + i]);
        }
        System.arraycopy(key_r, 0, key, 8, 8);
        return key;
    }

    
    private static byte[] generateIPEK(byte[] ksn, byte[] bdkbytes, Cipher TDESCipher) throws Exception
    {
    	byte[] iksn = subArray(ksn, 0, 8);
    	iksn[7] &=0x0E;
    	byte[] iksn_enc_left = subArray(encryptTDES(iksn, bdkbytes, TDESCipher), 0, 8);
    	bdkbytes[0] ^= 0xC0;
    	bdkbytes[1] ^= 0xC0;
    	bdkbytes[2] ^= 0xC0;
        bdkbytes[3] ^= 0xC0;
        bdkbytes[8] ^= 0xC0;
        bdkbytes[9] ^= 0xC0;
        bdkbytes[10] ^= 0xC0;
        bdkbytes[11] ^= 0xC0;        
        byte[] iksn_enc_right = subArray(encryptTDES(iksn, bdkbytes, TDESCipher), 0, 8);
		return concat(iksn_enc_left, iksn_enc_right);    	
    }


    private static byte[] encryptDES(byte[] data, byte[] key, Cipher DESCipher) throws Exception
    {
    	 DESCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALG_DES));
    	 return DESCipher.doFinal(data);
    }
    
    private static byte[] encryptTDES(byte[] data, byte[] key, Cipher TDESCipher) throws Exception
    {
    	 TDESCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(concat(key, 0, 16, key, 0, 8), ALG_TRIPLE_DES));
    	 return TDESCipher.doFinal(data);
    }
    
    private static byte[] encryptTDES(byte[] data, byte[] key, boolean encryptFinal) throws Exception
    {
    	Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(concat(key, 0, 16, key, 0, 8), ALG_TRIPLE_DES), new IvParameterSpec(new byte[8]));
        return cipher.doFinal(data);
    }
    
    private static byte[] decryptTDES(byte[] data, byte[] key) throws Exception
    {
    	Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(concat(key, 0, 16, key, 0, 8), ALG_TRIPLE_DES), new IvParameterSpec(new byte[8]));
        return cipher.doFinal(data);
    }
    
    private static byte[] hexStringToByteArray(String hexString)
   	{
   		if(hexString.length()%2 != 0) hexString = "0"+hexString;
   		final int len = hexString.length();

        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(hexString.charAt(i));
            int l = hexToBin(hexString.charAt(i + 1));
            if (h == -1 || l == -1) {
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + hexString);
            }
            out[i / 2] = (byte) (h * 16 + l);
        }
        return out;
   	}
   
    private static byte[] subArray(byte[] srcArray, int srcPos, int length)
    {
    	byte[] subArray = new byte[length];
    	System.arraycopy(srcArray, srcPos, subArray, 0, length);
		return subArray;
    }
    
	private static byte[] concat(byte[] array1, int beginIndex1, int length1, byte[] array2, int beginIndex2, int length2) {
		byte[] concatArray = new byte[length1 + length2];
		System.arraycopy(array1, beginIndex1, concatArray, 0, length1);
		System.arraycopy(array2, beginIndex2, concatArray, length1, length2);
		return concatArray;
	}

	private static byte[] concat(byte[] array1, byte[] array2) {
		byte[] concatArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, concatArray, 0, array1.length);
		System.arraycopy(array2, 0, concatArray, array1.length, array2.length);
		return concatArray;
	}
    
	private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        return -1;
    }
	
	public static String byteArrayToHexString(byte[] byteArray) {
		StringBuilder s = new StringBuilder(byteArray.length * 2);
		for (byte b : byteArray) {
			s.append(hexCode[(b >> 4) & 0xF]);
			s.append(hexCode[(b & 0xF)]);
		}
		return s.toString();
	}
	
	public static void example()
	{
		
		//14B1687684F0E5265C
		final String KSN = "110000F15CAD8800013A";
		final String BDK = "0123456789ABCDEFFEDCBA9876543210";
		final String ENC_DATA = "E27FB9E8B57C9AC0";
		System.out.println("KSN : "+KSN);
		System.out.println("BDK : "+BDK);
		System.out.println("ENC_DATA : "+ENC_DATA);
		
		try {
			System.out.println("DEC_DATA decrypt(String KSN, String BDK, String ENC_DATA) : '"+decrypt(KSN, BDK, ENC_DATA)+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(HexUtils.xor("0411268DDFFBBDDA", "0000377220044225"));
		//System.out.println(HexUtils.xor("06432665F76FDFAF", "0000333008902050"));
		//example();
		//System.exit(0);
		//4166464300629813=1806121000115   
		
		String KSN = "110000F15CAD88000066";
		String BDK = "0123456789ABCDEFFEDCBA9876543210";//"0123456789ABCDEFFEDCBA9876543210";
		try {
			System.out.println(decrypt(KSN, BDK, "4A241EE54F037C64"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*PINBLOCK_DUKPT : 4A241EE54F037C64
[19:58:13] : Transaction.transact() : PINBLOCK_KSN : 110000F15CAD88000066
[19:58:13] : Transaction.transact() : PINBLOCK_CLEAR : B83CB34F0A737125
[19:58:13] : Transaction.transact() : PINBLOCK_ZPK : B34802A03B59ECE6*/