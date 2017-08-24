package com.sil.corelib.test;

import org.kst.corelib.hsm.HSMHelper;
import org.pmw.tinylog.Logger;

public class AjayTest {

	private static final String DECIMALIZATION_TABLE = "0123456789012345";
	private static final String MINIMUM_PIN_LENGTH = "04";
	private static final String MAXIMUM_PIN_LENGTH = "12";
	
	private static final String PINBLOCK_FORMAT01 = "01";
	private static final String PINBLOCK_FORMAT05 = "05";
	
	public static final String getPinOffsetDoublePVK(String PAN, String PIN, String PVK) {
		String PINOFFSET = "";
		PIN = PIN + "F";
		String PAN12 = PAN.substring(3, 15);
		String PINVALIDATIONDATA = 	PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		String commandBA = "0000BA" + PIN + PAN12;
		String resultBA = HSMHelper.execute(commandBA);
		if (resultBA.substring(6, 8).equals("00")) {
			String response = resultBA.substring(8);
			Logger.info("Intermediate Pin : "+response);
			String commandDE = "0000DE" + PVK +"" + response + MINIMUM_PIN_LENGTH + PAN12 + DECIMALIZATION_TABLE + PINVALIDATIONDATA;
			String resultDE = HSMHelper.execute(commandDE);
			if (resultDE.substring(6, 8).equals("00") || resultDE.substring(6, 8).equals("02")) PINOFFSET = resultDE.substring(8, 12);
		}
		return PINOFFSET;
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
		
		//System.out.println(HSMHelper.execute("0000JK"));
		/*
		String resp = new String("0000100087896396;0100200001353684;0200300000035692;0300400000010724;0400500000018602;0500600000074124;0600700000166553;0700800000097579;0800900000009098;0901000000000910;1001000000000533;");
		String[] per = resp.split(";");
		
		for(String p : per){
			System.out.println(p.substring(0, 3)+" To "+p.substring(3, 6)+" : "+p.substring(6));
		}*/
		
		//System.exit(0);
		
		/*String CVK1_LMK 	= "150D8C0DF3348295";
		String CVK2_LMK 	= "B75E6BCE8B0A1D07";
		String PAN = "6071020002213527";
		String CVV1 = "170";
		String CVV2 = "160";
		String ICVV = "540";
		String EXPIRY_YYMM = "2412";
		
		System.out.println("CVV1 : "+ HSMUtils.verifyCVV(PAN, EXPIRY_YYMM, CVV1, CVK1_LMK, CVK2_LMK, "620"));
		System.out.println("CVV2 : "+ HSMUtils.verifyCVV(PAN, EXPIRY_YYMM, CVV2, CVK1_LMK, CVK2_LMK, "000"));
		System.out.println("ICVV : "+ HSMUtils.verifyCVV(PAN, EXPIRY_YYMM, ICVV, CVK1_LMK, CVK2_LMK, "999"));*/
		
		commandShweta();
		System.exit(0);
		String PVK_LMK		= "U4F12B3A7123D83B504C9F06D899C5D6B";
		String pinOffset = "";
		String pinOffset_exp = "0629";
		
		String PINBLOCK_ZPK = "A3CC37D07FEA57C9";
		String ZPK_LMK = "UB27EC3FAB16D5D4D0DFCC5C3246776E3";
		
		
		
		Logger.info("PVK_LMK : " + PVK_LMK);
		Logger.info("ZPK_LMK : "+ZPK_LMK);
		Logger.info("");
		String PAN = null;
		Logger.info("***********************************"+PAN+"*************************************");
		Logger.info("PAN : " + PAN);
		String PIN = null;
		Logger.info("PIN : " + PIN);
		Logger.info("PINBLOCK_ZPK "+PINBLOCK_ZPK);
		Logger.info("PINOFFSET EXPECTED : " + pinOffset_exp);
		
		//pinOffset = getPinOffsetDoublePVK(PAN, PIN, PVK_LMK);
		Logger.info("PINOFFSET GENERATED : "+pinOffset);
		
		//Logger.info("PinBlock Validation Result : "+validatePinBlock_ZPK(PAN, PINBLOCK_ZPK, pinOffset, PVK_LMK, ZPK_LMK));
		System.exit(0);
		PAN = "6071020002213527";
		PIN = "4563";
		PINBLOCK_ZPK = "84F84F21F7AC7E74";
		pinOffset_exp = "7699";
		
		Logger.info("");
		Logger.info("***********************************"+PAN+"*************************************");
		Logger.info("PAN : " + PAN);
		Logger.info("PIN : " + PIN);
		Logger.info("PINBLOCK_ZPK "+PINBLOCK_ZPK);
		Logger.info("PINOFFSET EXPECTED : " + pinOffset_exp);		
		pinOffset = getPinOffsetDoublePVK(PAN, PIN, PVK_LMK);
		Logger.info("PINOFFSET GENERATED : "+pinOffset);
		
		Logger.info("PinBlock Validation Result : "+validatePinBlock_ZPK(PAN, PINBLOCK_ZPK, pinOffset, PVK_LMK, ZPK_LMK));
	
		
	}
	
	private static String commandJA(){
		String PAN = "6071020002213576";
		System.out.println("PAN : "+PAN);
		String PIN = "5993";
		System.out.println("PIN : "+PIN);
		String PVK_LMK = "U4F12B3A7123D83B504C9F06D899C5D6B";
		System.out.println("PVK_LMK : "+PVK_LMK);
		String PVKI = "1";
		System.out.println("PVKI : "+PVKI);
		String TPK_LMK = "UB9322774320AB911EC740326592F688C";
		String ZPK_LMK = "UB27EC3FAB16D5D4D0DFCC5C3246776E3";
		System.out.println("TPK_LMK : "+TPK_LMK);
		String PINBLOCK_TPK = "60BC4EC98BF9921A";
		String NEW_PINBLOCK_TPK = "";
		String PINBLOCK_ZPK = "A3CC37D07FEA57C9";
		System.out.println("PINBLOCK_TPK : "+PINBLOCK_TPK);
		String PAN12 = PAN.substring(PAN.length()-13, PAN.length()-1);
		//String commandJA = "0000JA"+PAN12+"04";
		
	//	String generatedPin = HSMHelper.execute(commandJA).substring(8);
		
		PIN = PIN + "F";
		String PINVALIDATIONDATA = PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		String commandBA = "0000BA" + PIN + PAN12;
		String generatedPin = HSMHelper.execute(commandBA).substring(8);
		System.out.println("Intermediate Generated Pin : "+generatedPin);

		String commandDG = "0000DG"+PVK_LMK+generatedPin+PAN12+PVKI;
		String PVV = HSMHelper.execute(commandDG).substring(8);
		System.out.println("Visa PVV : "+PVV);
		
		String commandDC = "0000DC"+TPK_LMK+PVK_LMK+PINBLOCK_TPK+PINBLOCK_FORMAT05+PAN12+PVKI+PVV;
		System.out.println(HSMHelper.execute(commandDC));
		
		String commandEC = "0000EC"+ZPK_LMK+PVK_LMK+PINBLOCK_ZPK+"01"+PAN12+PVKI+PVV;
		System.out.println(HSMHelper.execute(commandEC));
		
		String PINBLOCK_KEY_TYPE_TPK = "002";
		String PINBLOCK_KEY_TYPE_ZPK = "001";
		String commandCU = "0000CU"+PINBLOCK_KEY_TYPE_TPK+TPK_LMK+PVK_LMK+PINBLOCK_TPK+PINBLOCK_FORMAT05+PAN12+PVKI+PVV+PINBLOCK_TPK;
		System.out.println(HSMHelper.execute(commandCU));
		
		commandCU = "0000CU"+PINBLOCK_KEY_TYPE_ZPK+ZPK_LMK+PVK_LMK+PINBLOCK_ZPK+PINBLOCK_FORMAT01+PAN12+PVKI+PVV+PINBLOCK_ZPK;
		System.out.println(HSMHelper.execute(commandCU));
		
		
		
		PINBLOCK_ZPK = "7905D63257273DD3";
		PAN12 = "102000221352";
		PIN = "1111";
		
		PIN = PIN + "F";
		PINVALIDATIONDATA = PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		commandBA = "0000BA" + PIN + PAN12;
		generatedPin = HSMHelper.execute(commandBA).substring(8);
		System.out.println("Intermediate Generated Pin : "+generatedPin);

		commandDG = "0000DG"+PVK_LMK+generatedPin+PAN12+PVKI;
		PVV = HSMHelper.execute(commandDG).substring(8);
		System.out.println("Visa PVV : "+PVV);
		commandCU = "0000CU"+PINBLOCK_KEY_TYPE_ZPK+ZPK_LMK+PVK_LMK+PINBLOCK_ZPK+PINBLOCK_FORMAT01+PAN12+PVKI+PVV+PINBLOCK_ZPK;
		System.out.println(HSMHelper.execute(commandCU));
		
		//Test
		
		
		
		
		return null;
	}
	
	
	private static void commandShweta(){

		String PAN = "6071020002000049";
		System.out.println("PAN : "+PAN);
		String PIN = "1234";
		System.out.println("PIN : "+PIN);
		String PVK_LMK = "U4F12B3A7123D83B504C9F06D899C5D6B";
		System.out.println("PVK_LMK : "+PVK_LMK);
		String PVKI = "1";
		System.out.println("PVKI : "+PVKI);
		String TPK_LMK = "UB9322774320AB911EC740326592F688C";
		String ZPK_LMK = "UB27EC3FAB16D5D4D0DFCC5C3246776E3";
		System.out.println("TPK_LMK : "+TPK_LMK);
		String PINBLOCK_TPK = "60BC4EC98BF9921A";
		String NEW_PINBLOCK_TPK = "";
		String PINBLOCK_ZPK = "A3CC37D07FEA57C9";
		System.out.println("PINBLOCK_TPK : "+PINBLOCK_TPK);
		String PAN12 = PAN.substring(PAN.length()-13, PAN.length()-1);
		//String commandJA = "0000JA"+PAN12+"04";
		
	//	String generatedPin = HSMHelper.execute(commandJA).substring(8);
		
		PIN = PIN + "F";
		String PINVALIDATIONDATA = PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		String commandBA = "0000BA" + PIN + PAN12;
		String generatedPin = HSMHelper.execute(commandBA).substring(8);
		System.out.println("Intermediate Generated Pin : "+generatedPin);

		String commandDG = "0000DG"+PVK_LMK+generatedPin+PAN12+PVKI;
		String PVV = HSMHelper.execute(commandDG).substring(8);
		System.out.println("Visa PVV : "+PVV);
		
		
		
		String commandDC = "0000DC"+TPK_LMK+PVK_LMK+PINBLOCK_TPK+PINBLOCK_FORMAT05+PAN12+PVKI+PVV;
		System.out.println(HSMHelper.execute(commandDC));
		
		String commandEC = "0000EC"+ZPK_LMK+PVK_LMK+PINBLOCK_ZPK+"01"+PAN12+PVKI+PVV;
		System.out.println(HSMHelper.execute(commandEC));
		
		String PINBLOCK_KEY_TYPE_TPK = "002";
		String PINBLOCK_KEY_TYPE_ZPK = "001";
		String commandCU = "0000CU"+PINBLOCK_KEY_TYPE_TPK+TPK_LMK+PVK_LMK+PINBLOCK_TPK+PINBLOCK_FORMAT05+PAN12+PVKI+PVV+PINBLOCK_TPK;
		System.out.println(HSMHelper.execute(commandCU));
		
		commandCU = "0000CU"+PINBLOCK_KEY_TYPE_ZPK+ZPK_LMK+PVK_LMK+PINBLOCK_ZPK+PINBLOCK_FORMAT01+PAN12+PVKI+PVV+PINBLOCK_ZPK;
		System.out.println(HSMHelper.execute(commandCU));
		
		
		
		PINBLOCK_ZPK = "7905D63257273DD3";
		PAN12 = "102000221352";
		PIN = "1111";
		
		PIN = PIN + "F";
		PINVALIDATIONDATA = PAN.substring(0, 10) + "N" + PAN.substring(15, 16);
		commandBA = "0000BA" + PIN + PAN12;
		generatedPin = HSMHelper.execute(commandBA).substring(8);
		System.out.println("Intermediate Generated Pin : "+generatedPin);

		commandDG = "0000DG"+PVK_LMK+generatedPin+PAN12+PVKI;
		PVV = HSMHelper.execute(commandDG).substring(8);
		System.out.println("Visa PVV : "+PVV);
		commandCU = "0000CU"+PINBLOCK_KEY_TYPE_ZPK+ZPK_LMK+PVK_LMK+PINBLOCK_ZPK+PINBLOCK_FORMAT01+PAN12+PVKI+PVV+PINBLOCK_ZPK;
		System.out.println(HSMHelper.execute(commandCU));
		
		//Test
		
		
		
		
	
	}
}
