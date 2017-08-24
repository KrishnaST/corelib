package org.kst.corelib.emv;

import java.util.ArrayList;
import java.util.Arrays;

import org.kst.corelib.datautils.ByteUtils;
import org.kst.corelib.datautils.HexUtils;

public class TSI {

	public static final String[] tsiStrings = new String[16];

	private boolean isValidTSI = true;
	private ArrayList<String> tsi_errors = new ArrayList<>();
	static {
		Arrays.fill(tsiStrings, "");
		tsiStrings[0] = "Offline data authentication was performed";
		tsiStrings[1] = "Cardholder verification was performed";
		tsiStrings[2] = "Card risk management was performed";
		tsiStrings[3] = "Issuer authentication was performed";
		tsiStrings[4] = "Terminal risk management was performed";
		tsiStrings[5] = "Script processing was performed";
		tsiStrings[6] = "RFU";
		tsiStrings[7] = "RFU";

		tsiStrings[8] = "RFU";
		tsiStrings[9] = "RFU";
		tsiStrings[10] = "RFU";
		tsiStrings[11] = "RFU";
		tsiStrings[12] = "RFU";
		tsiStrings[13] = "RFU";
		tsiStrings[14] = "RFU";
		tsiStrings[15] = "RFU";
	}


	public static TSI parseTSI(String tsiHex) {
		TSI tsi = new TSI();
		StringBuilder tsiBinaryString = new StringBuilder("");
		for (int i = 0; i < tsiHex.length(); i = i + 2) {
			tsiBinaryString.append(ByteUtils.byteToBinaryString(HexUtils.hexPairToByte(tsiHex.substring(i, i + 2))));
		}
		for (int i = 0; i < tsiBinaryString.length(); i++) {

			if (tsiBinaryString.charAt(i) == '1') {
				int byteNum = (i + 1) / 8 + 1;
				int bitNum = 8 - i % 8;
				if (!tsiStrings[i].equals("RFU")) {
					System.out.println("Byte " + byteNum + "\tBit " + bitNum + " : " + tsiStrings[i]);
					tsi.isValidTSI = false;
					tsi.getTsi_errors().add(tsiStrings[i]);
				}
			}
		}
		return tsi;
	}


	public boolean isValidTSI() {
		return isValidTSI;
	}


	public void setValidTSI(boolean isValidTSI) {
		this.isValidTSI = isValidTSI;
	}


	public ArrayList<String> getTsi_errors() {
		return tsi_errors;
	}


	public void setTsi_errors(ArrayList<String> tsi_errors) {
		this.tsi_errors = tsi_errors;
	}


	public static void main(String[] args) {
		TSI tsi = parseTSI("F00F");// 0280008000
		System.out.println(tsi.isValidTSI());
	}
}
