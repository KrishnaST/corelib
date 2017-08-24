package org.kst.corelib.hsm;

import org.pmw.tinylog.Logger;

public class HSMUtils {

	private static final String DECIMALIZATION_TABLE = "0123456789012345";
	private static final String MAXIMUM_PIN_LENGTH = "12";
	private static final String MINIMUM_PIN_LENGTH = "04";
	private static final String PINBLOCK_FORMAT01 = "01";
	private static final String PINBLOCK_FORMAT05 = "05";

	private static final String BDK1_DESCRIPTOR = "009";
	private static final String BDK2_DESCRIPTOR = "609";

	public static String generateZMK() {
		String commandA0 = "0000A00" + "000U";
		String responseA0 = HSMHelper.execute(commandA0);
		return (responseA0.substring(6, 8).equals("00")) ? responseA0.substring(8, 41) : responseA0.substring(6, 8);
	}


	public static String generateZPK() {
		String commandA0 = "0000A00" + "001U";
		String responseA0 = HSMHelper.execute(commandA0);
		return (responseA0.substring(6, 8).equals("00")) ? responseA0.substring(8, 41) : "99";
	}


	public static String generateTMK() {
		String commandA0 = "0000A00" + "002U";
		String responseA0 = HSMHelper.execute(commandA0);
		return (responseA0.substring(6, 8).equals("00")) ? responseA0.substring(8, 41) : "99";
	}


	public static String generateBDK1() {
		String commandA0 = "0000A00" + BDK1_DESCRIPTOR + "U";
		String responseA0 = HSMHelper.execute(commandA0);
		return (responseA0.substring(6, 8).equals("00")) ? responseA0.substring(8, 41) : "99";
	}


	public static String generateBDK2() {
		String commandA0 = "0000A00" + BDK2_DESCRIPTOR + "U";
		String responseA0 = HSMHelper.execute(commandA0);
		return (responseA0.substring(6, 8).equals("00")) ? responseA0.substring(8, 41) : "99";
	}


	public static String generateBDK3() {
		String commandA0 = "0000A00" + "809U";
		String responseA0 = HSMHelper.execute(commandA0);
		return (responseA0.substring(6, 8).equals("00")) ? responseA0.substring(8, 41) : "99";
	}


	public static String translateData() // Failed Due To HSM License
	{
		String CBC_FLAG = "01";
		String INPUT_FORMAT = "0";
		String SOURCE_KEY_TYPE = "609";
		String SOURCE_KSN_DESCRIPTOR = "609";
		String SOURCE_BDK = "UE98A10B12E0A8E28C11A63BE71039817";
		String SOURCE_KSN = "9160470280FFFF000078";

		String DEST_KEY_TYPE = "609";
		String DEST_BDK = "UE98A10B12E0A8E28C11A63BE71039817";
		String DEST_KSN = "9160470280FFFF000079";

		String IV = "0000000000000000";
		String DATA_LENGTH = "0028";
		String MESSAGE = "E8E445A4730B46980A6A0F68EAA072DB261DD5BD9C9FD64CF354055A00262D0E89C3355F501F41FD";
		String commandM4 = "0000M4" + CBC_FLAG + CBC_FLAG + INPUT_FORMAT + INPUT_FORMAT + SOURCE_KEY_TYPE + SOURCE_BDK + SOURCE_KSN_DESCRIPTOR + SOURCE_KSN + DEST_KEY_TYPE + DEST_BDK + SOURCE_KSN_DESCRIPTOR + DEST_KSN + IV + IV + DATA_LENGTH
				+ MESSAGE;
		System.out.println(HSMHelper.execute(commandM4));
		return null;
	}


	public static final String encryptPinLMK(String PIN, String PAN) {
		PIN = PIN + "F";
		String PAN12 = PAN.substring(3, 15);
		String commandBA = "0000BA" + PIN + PAN12;
		String resultBA = HSMHelper.execute(commandBA);
		if (resultBA.substring(6, 8).equals("00")) return resultBA.substring(8);
		else return "99";
	}


	public static final String decryptPinLMK(String PIN_LMK, String PAN) {
		// PIN = PIN + "F";
		String PAN12 = PAN.substring(3, 15);
		String commandNG = "0000NG" + PAN12 + PIN_LMK;
		String resultBA = HSMHelper.execute(commandNG);
		if (resultBA.substring(6, 8).equals("00")) return resultBA.substring(8);
		else return "99";
	}


	public static final String getPinOffset(String PAN, String PIN, String PVK) {
		String PINOFFSET = "";
		PIN = PIN + "F";
		String PAN12 = PAN.substring(3, 15);
		String PINVALIDATIONDATA = PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		String commandBA = "0000BA" + PIN + PAN12;
		String resultBA = HSMHelper.execute(commandBA);
		if (resultBA.substring(6, 8).equals("00")) {
			String response = resultBA.substring(8);
			Logger.info("Intermediate Pin : " + response);
			String commandDE = "0000DE" + PVK + response + MINIMUM_PIN_LENGTH + PAN12 + DECIMALIZATION_TABLE + PINVALIDATIONDATA;
			String resultDE = HSMHelper.execute(commandDE);
			/* if (resultDE.substring(6, 8).equals("00")) */
			PINOFFSET = resultDE.substring(8, 12);
		}
		return PINOFFSET;
	}


	public static final String generateCVV(String PAN, String EXPIRY_YYMM, String CVK1, String CVK2, String SERVICE_CODE) {
		String commandCW = "0000CW" + CVK1 + CVK2 + PAN + ";" + EXPIRY_YYMM + SERVICE_CODE;
		String responseCW = HSMHelper.execute(commandCW);
		return responseCW.substring(6, 8).equals("00") ? responseCW.substring(8) : "";
	}

	/*
	 * 0000CY B0314C6D17FD9912 8F1F9E3ECF601CF8 283 5085570020022942;2103620
	 */


	public static final String verifyCVV(String PAN, String EXPIRY_YYMM, String CVV, String CVK1, String CVK2, String SERVICE_CODE) {
		String commandCY = "0000CY" + CVK1 + CVK2 + CVV + PAN + ";" + EXPIRY_YYMM + SERVICE_CODE;
		String responseCY = HSMHelper.execute(commandCY);
		return responseCY.substring(6, 8);
	}


	public static String getTPKTMK_TPKLMK(String TMK_LMK) {
		String commandHC = "0000HC" + TMK_LMK + ";XU1";
		String HCResult = HSMHelper.execute(commandHC);
		String TPKTMK = HCResult.substring(9, 41);
		String TPKLMK = HCResult.substring(41);
		return TPKTMK + "_" + TPKLMK;
	}


	public static String translatePinBlock_TPK_ZPK(String PAN, String PINBLOCK_TPK, String TPK_LMK, String ZPK_LMK) {
		String PAN12 = (PAN.substring(PAN.length() - 13, PAN.length() - 1));
		String commandCA = "0000CA" + TPK_LMK + ZPK_LMK + MAXIMUM_PIN_LENGTH + PINBLOCK_TPK + PINBLOCK_FORMAT05 + PINBLOCK_FORMAT01 + PAN12; // ZPK Starts With
																																				// 'U'
		String resultCA = HSMHelper.execute(commandCA);
		if (resultCA.substring(6, 8).equals("00")) return resultCA.substring(10, 26);
		else return "01";
	}


	public static String translatePinBlock_DUKPT_ZPK(String PAN, String PINBLOCK_DUKPT, String KSN, String BDK_LMK, String ZPK_LMK) {
		String PAN12 = PAN.substring(3, 15);
		String commandG0 = "0000G0" + BDK_LMK + ZPK_LMK + BDK2_DESCRIPTOR + KSN + PINBLOCK_DUKPT + PINBLOCK_FORMAT01 + PINBLOCK_FORMAT01 + PAN12;
		String responseG0 = HSMHelper.execute(commandG0);
		return responseG0.substring(6, 8).equals("00") ? responseG0.substring(10, 26) : responseG0.substring(6, 8);
	}


	public static String validatePinBlock_TPK(String PAN, String PINBLOCK_TPK, String PINOFFSET, String TPK_LMK, String PVK_LMK) {
		String PAN12 = PAN.substring(3, 15);
		String PIN_VALIDATION_DATA = PAN.substring(0, 10) + "N" + PAN.substring(PAN.length() - 1);
		PINOFFSET = String.format("%-12s", PINOFFSET).replace(' ', 'F');
		String commandDA = "0000DA" + TPK_LMK + PVK_LMK + MAXIMUM_PIN_LENGTH + PINBLOCK_TPK + PINBLOCK_FORMAT05 + MINIMUM_PIN_LENGTH + PAN12 + DECIMALIZATION_TABLE + PIN_VALIDATION_DATA + PINOFFSET;
		String responseDA = HSMHelper.execute(commandDA);
		System.out.println("validatePinBlock_TPK>>"+responseDA.substring(6, 8));
		return responseDA.substring(6, 8);
	}


	public static String validatePinBlock_DUKPT(String PAN, String BDK_LMK, String PVK_LMK, String KSN, String PINBLOCK_DUKPT, String PINOFFSET) {
		String MODE = "0";
		String PINVALIDATIONDATA = PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		String PAN12 = PAN.substring(PAN.length() - 13, PAN.length() - 1);
		PINOFFSET = String.format("%4s", PINOFFSET).replace(' ', '0') + "FFFFFFFF";
		String commandGO = "0000GO" + MODE + BDK_LMK + PVK_LMK + BDK2_DESCRIPTOR + KSN + PINBLOCK_DUKPT + PINBLOCK_FORMAT01 + MINIMUM_PIN_LENGTH + PAN12 + DECIMALIZATION_TABLE + PINVALIDATIONDATA + PINOFFSET;
		String resultGO = HSMHelper.execute(commandGO);
		return resultGO;
	}


	public static String validatePinBlock_ZPK(String PAN, String PINBLOCK_ZPK, String PINOFFSET, String PVK, String ZPK_LMK) {
		String PAN12 = (PAN.substring(PAN.length() - 13, PAN.length() - 1));
		String PINVALIDATIONDATA = PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		String PINOFFSET12 = String.format("%4s", PINOFFSET).replace(' ', '0') + "FFFFFFFF";
		String commandEA = "0000EA" + ZPK_LMK + PVK + MAXIMUM_PIN_LENGTH + PINBLOCK_ZPK + PINBLOCK_FORMAT01 + MINIMUM_PIN_LENGTH + PAN12 + DECIMALIZATION_TABLE + PINVALIDATIONDATA + PINOFFSET12;
		String resultEA = HSMHelper.execute(commandEA);
		return resultEA.substring(6, 8);
	}


	public static void main(String[] args) {
	
		System.out.println(getPinOffset("6078210100000040", "1111", "4385B5DB5AEAF809"));
		System.out.println(HSMUtils.validatePinBlock_DUKPT("6078210100000040", "UE7F7DF56E31E9FCD230C030D88DB0327", "4385B5DB5AEAF809", "110000F15CAD88000006", "823568C1C2F2B12D", "1258"));
		//System.out.println(HSMHelper.execute("0000DAUB9322774320AB911EC740326592F688C4385B5DB5AEAF80912A4DEC67876BC811A050400088982218001234567890123450000008898N07166FFFFFFFF"));
		System.exit(0);
/*
1>>>
Card : 6077140010000031
pin : 1234
offset : 8112
PVK:4385B5DB5AEAF809
pinblock 1003349E1DAA8375
ksn F8765432100F0F000001
BDKLMK:UE98A10B12E0A8E28C11A63BE71039817
Resp : 20


2>>>
Card : 6077140010000031
pin : 1234
offset : 8112
PVK:4385B5DB5AEAF809
pinblock : 35A04DBEBB017BE0
KSN : F8765432100F0F000001
BDK : U58B89E020A510989482CD46F3399054B
Resp : 10
*/
		
		String PVK_LMK = "4385B5DB5AEAF809";
		String BDK_LMK = "UE98A10B12E0A8E28C11A63BE71039817";
		String PAN = "6077140010000031";
		String KSN = "F8765432100F0F000001";
		String PINBLOCK = "1003349E1DAA8375";
		String pinOffset = "8112";
		
		
		//System.out.println(getPinOffset(PAN, "1234", PVK_LMK));
		System.out.println(validatePinBlock_TPK("9665997897526325689789","","","",""));
		//validatePinBlock_DUKPT(PAN, BDK_LMK, PVK_LMK, KSN, PINBLOCK, pinOffset);
		
		
	
	}
	

}
