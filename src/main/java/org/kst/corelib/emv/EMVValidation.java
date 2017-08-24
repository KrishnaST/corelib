package org.kst.corelib.emv;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.util.TreeMap;

import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;
import org.kst.corelib.tlv.BERTLV;

public class EMVValidation {
	
	private static final String HSM_COMMAND = "0000KW";
	private static final String MODE = "1";
	private static final String SCHEME_ID = "2";	
	private static final String MK_AC = "U6C58184333A1628A1EF4F0C3B9C41D37";
	
	
	private static final String ARC = "0014";
	private static final String SEMI_COLON ="3B";
	private static final String ARPC_TAG = "910A";
	
	public static String generateARPC(final String PAN, String DE55)
	{
		final String PAN14 = PAN.substring(PAN.length()-14, PAN.length());
		
		String response = null;
		try {

			DE55 = DE55.toUpperCase();
			final TreeMap<String, String> emvMap = BERTLV.parseTLV(DE55,BERTLV.ENCODING.HEX).getTlvMap();
			System.out.println(emvMap.toString().replace(',','\n'));
			String pan_seq_no 					= emvMap.get("5F34");			
			String trans_amount 				= emvMap.get("9F02");
			String add_amount					= (emvMap.get("9F03") != null) ? emvMap.get("9F03") : "000000000000";
			String term_country_code 			= emvMap.get("9F1A");
			String tvr 							= emvMap.get("95");
			String trans_currency_code 			= emvMap.get("5F2A");
			String trans_date 					= emvMap.get("9A");		
			String trans_type 					= emvMap.get("9C");
			String unpredictable_number 		= emvMap.get("9F37");
			String app_interchange_profile 		= emvMap.get("82");
			
			String app_trans_counter 			= emvMap.get("9F36");
			String issuer_app_data 				= emvMap.get("9F10").substring(2,4).equals("05") ? emvMap.get("9F10") : emvMap.get("9F10").substring(4);
			
			String cryptogram 					= emvMap.get("9F26");
			
			String transData 					= trans_amount+add_amount+term_country_code+tvr+trans_currency_code+trans_date+trans_type+unpredictable_number+app_interchange_profile+app_trans_counter+issuer_app_data;
			
			System.out.println("transData : "+transData);
			if((transData.length() % 2) != 0) transData += "800000000000000";
			else transData += "80000000000000";

			String header = HSM_COMMAND+MODE+SCHEME_ID+MK_AC;
			String message = PAN14 + pan_seq_no + app_trans_counter;
			transData = ByteUtils.byteArrayToHexString(Integer.toHexString(transData.length()/2).getBytes()) + transData; // Suffix length in hex of transaction Data
			String trailer = SEMI_COLON + cryptogram + ARC;
			System.out.println("transData : "+transData);
			int len =(message+transData+trailer).length()/2;
			byte[] headerBytes = new byte[2];
			
			headerBytes[0] = (byte) ((len + header.length())/256);
			headerBytes[1] = (byte) ((len + header.length())%256);
			byte[] responseBytes = execute(headerBytes, header.getBytes(), HexUtils.hexStringToByteArray(message), HexUtils.hexStringToByteArray(transData), HexUtils.hexStringToByteArray(trailer));
			response = new String(responseBytes).substring(0,8)+String.format("%03d", (ARPC_TAG+ByteUtils.byteArrayToHexString(responseBytes).substring(16)+ARC).length()/2)+ARPC_TAG+ByteUtils.byteArrayToHexString(responseBytes).substring(16)+ARC;
		}
		catch (Exception e) {e.printStackTrace();}
		return response;
	}
	
	public static byte[] execute(byte[]... byteArrays ) {
		byte[] response = null;
		try(Socket sc = new Socket("10.100.5.21", 6046);
			BufferedInputStream bis = new BufferedInputStream(sc.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(sc.getOutputStream());) {
			for(byte[] bytes : byteArrays)	{
				bos.write(bytes);
				//System.out.print(ByteUtils.byteArrayToHexString(bytes));
				System.out.print(new String(bytes));
			}
			System.out.println();
			bos.flush();
			int len = bis.read();
			len = bis.read();
			len = bis.available();
			response = new byte[len];
			bis.read(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static void main(String a[])
	{
		//String PAN = "6521500430015129";		
		//String DE55 = "5F34009F090200999F1A0203569F1E009F3501229F37045A1216AD9F22008F009F0802008D9F360200B09F34033F00009F3303E0F8C89F100706010C03A000005F2A020356950580800080009F2701809A031705169F2608F303B0C67548F3839F4104000011579F0206000000000069820208008407A00000000310109C01009B0228009F0607A0000000031010" ;
		//01011111
		
		String PAN = "6521500060000029";
		String DE55 = "9F1A0203569F360200315F3401019F3704CD2FAC629F34030200009F3501229F10080105A000004020009F3303E0F0C85F2A020356950508800480009F2701809A031703039F2608C7F2D58A2F7B767A9F090200029F4104000003069F020600000000123482023800840500000000009C0100" ;
		System.out.println(generateARPC(PAN, DE55));
	}
    
}
