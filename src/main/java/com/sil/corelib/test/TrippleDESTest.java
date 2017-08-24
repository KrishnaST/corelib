package com.sil.corelib.test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.kst.corelib.crypt.TrippleDES;
import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;


public class TrippleDESTest {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		//System.out.println(new String(HexUtils.hexStringToByteArray("E9F38C3D720AA4E1322B25EBCF9381EFE842482D53BD873EFDF0491E3FEF5F0BCE04E4940FE795264B4347A50E0B1E99A508676BF672D02CD31A7DB6DDC9C05567D4AC6E5DE691256F828057FEEC3C66C2561F3892DC9EE401C89D6670D8")));
		String data1 = "041111FFFFFFFFFF";
		String data2 = "0000102000221352";
		String data = HexUtils.xor(data1, data2);
		String key = "87EB226665842B39828DFC558EE7BDAC";
		
		/*data1 = "045993FFFFFFFFFF";
		data2 = "0000102000221357";
		data = HexUtils.xor(data1, data2);
		
		data1 = "045993FFFFFFFFFF";
		data2 = "0000102000221357";
		data = HexUtils.xor(data1, data2);
		
		data = "C2F6C13BB9BA5DAD";
		key  = "75161CDC859467CBBCF7B33B9D54BF32";*/
		
		//String data = "8177E2C02537C1BC";
		//String key = "0123456789ABCDEF0123456789ABCDEF";
		
		try {
			// byte[] encData = TrippleDES.encrypt(DataConverter.hexToByte(data), DataConverter.hexToByte(key), TrippleDES.MODE.ECB,
			// TrippleDES.PADDING.NoPadding);
			// System.out.println(DataConverter.byteToHex(encData));
			byte[] decData = TrippleDES.encrypt(HexUtils.hexStringToByteArray("0200022135214563"), HexUtils.hexStringToByteArray("164994D9C1516892B02CF7F8F4524CF2"), TrippleDES.MODE.ECB, TrippleDES.PADDING.NoPadding);
			System.out.println(ByteUtils.byteArrayToHexString(decData));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

//C2C15E07406851102FC47567C440C85B TPK

//0412647FBCFFE768
//141234EC81B620D1

//Generated PinBlock 0412647F8AFFCD6C
//Decrypted Pinblock 141234EC81B620D1

