package org.kst.corelib.emv;

import java.util.ArrayList;
import java.util.Arrays;

import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;

public class TVR {

	public static final String[] tvrStrings = new String[40];
	public static final String[] tsi = new String[16];

	private boolean isValidTVR = true;
	private ArrayList<String> tvr_errors = new ArrayList<>();

	static {

		Arrays.fill(tvrStrings, "");

		// Terminal Verification Results
		tvrStrings[0] = "Offline data authentication was not performed";
		tvrStrings[1] = "SDA failed";
		tvrStrings[2] = "ICC data missing";
		tvrStrings[3] = "Card appears in terminal exception file";
		tvrStrings[4] = "DDA failed";
		tvrStrings[5] = "CDA failed";
		tvrStrings[6] = "RFU";
		tvrStrings[7] = "RFU";

		tvrStrings[8] = "ICC and terminal have different application versions";
		tvrStrings[9] = "Expired application";
		tvrStrings[10] = "Application not yet effective";
		tvrStrings[11] = "Requested service not allowed for card product";
		tvrStrings[12] = "New card";
		tvrStrings[13] = "RFU";
		tvrStrings[14] = "RFU";
		tvrStrings[15] = "RFU";

		tvrStrings[16] = "Cardholder verification was not successful";
		tvrStrings[17] = "Unrecognised CVM";
		tvrStrings[18] = "PIN Try Limit exceeded";
		tvrStrings[19] = "PIN entry required and PIN pad not present or not working";
		tvrStrings[20] = "PIN entry required, PIN pad present but PIN was not entered";
		tvrStrings[21] = "Online PIN entered";
		tvrStrings[22] = "RFU";
		tvrStrings[23] = "RFU";

		tvrStrings[24] = "Transaction exceeds floor limit";
		tvrStrings[25] = "Lower consecutive offline limit exceeded";
		tvrStrings[26] = "Upper consecutive offline limit exceeded";
		tvrStrings[27] = "Transaction selected randomly for online processing";
		tvrStrings[28] = "Merchant forced transaction online";
		tvrStrings[29] = "RFU";
		tvrStrings[30] = "RFU";
		tvrStrings[31] = "RFU";

		tvrStrings[32] = "Default TDOL used";
		tvrStrings[33] = "Issuer authentification failed";
		tvrStrings[34] = "Script processing failed before final GENERATE AC";
		tvrStrings[35] = "Script processing failed after final GENERATE AC";
		tvrStrings[36] = "RFU";
		tvrStrings[37] = "RFU";
		tvrStrings[38] = "RFU";
		tvrStrings[39] = "RFU";

	}


	public static TVR parseTVR(String tvrHex) {
		TVR tvr = new TVR();
		StringBuilder tvrBinaryString = new StringBuilder("");
		for (int i = 0; i < tvrHex.length(); i = i + 2)
			tvrBinaryString.append(ByteUtils.byteToBinaryString(HexUtils.hexPairToByte(tvrHex.substring(i, i + 2))));

		for (int i = 0; i < tvrBinaryString.length(); i++) {
			if (tvrBinaryString.charAt(i) == '1') {
				int byteNum = (i + 1) / 8 + 1;
				int bitNum = 8 - i % 8;
				if (!TVR.tvrStrings[i].equals("RFU")) {
					System.out.println("Byte " + byteNum + "\tBit " + bitNum + " : " + TVR.tvrStrings[i]);
					tvr.isValidTVR = false;
					tvr.getTvr_errors().add(TVR.tvrStrings[i]);
				}
			}
		}
		return tvr;
	}


	public boolean isValidTVR() {
		return isValidTVR;
	}


	public ArrayList<String> getTvr_errors() {
		return tvr_errors;
	}


	public void setTvr_errors(ArrayList<String> tvr_errors) {
		this.tvr_errors = tvr_errors;
	}


	public static void main(String[] args) {
		TVR tvr = parseTVR("0200048055");// 0280008000
		System.out.println(tvr.isValidTVR());
		System.out.println(tvr.getTvr_errors());
	}
}
