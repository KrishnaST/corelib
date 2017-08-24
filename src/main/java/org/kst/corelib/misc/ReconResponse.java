package org.kst.corelib.misc;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.kst.corelib.datautils.HexUtils;
import org.kst.corelib.tlv.BERTLVRecon;

public class ReconResponse {

	private Date requestDate;// YYYYMMDDHHmmSS
	private Date responseDate;
	private String stan;
	private String responseCode;
	private String terminalID;
	private String merchantId;
	private String transactionSequenceNumber;

	private String appVersion;
	private String merchantNameEng;
	private String merchantNameArb;
	private String merchantAddrEng;
	private String merchantAddrEng1;
	private String merchantAddrArb;
	private String merchantAddrArb1;

	private List<DF9394Response> df93List = new ArrayList<>();
	private List<DF9394Response> df94List = new ArrayList<>();
	private List<DF95Response> df95List = new ArrayList<>();

	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public static ReconResponse parseResponse(String reconResponseString) throws ParseException {
		ReconResponse reconResponse = new ReconResponse();

		BERTLVRecon bertlvRecon = BERTLVRecon.parseTLV(reconResponseString, BERTLVRecon.ENCODING.HEX);

		System.out.println(bertlvRecon);
		
		
		String[] DF89 = bertlvRecon.get("DF89").split("7C");
		System.out.println(Arrays.toString(DF89));
		reconResponse.setRequestDate(reconResponse.sdf.parse(hexToASCII(DF89[0])));
		reconResponse.setResponseDate(reconResponse.sdf.parse(hexToASCII(DF89[1])));
		
		reconResponse.setStan(hexToASCII(bertlvRecon.get("DF0B")));
		reconResponse.setResponseCode(hexToASCII(bertlvRecon.get("DF27")));
		reconResponse.setTerminalID(hexToASCII(bertlvRecon.get("DF29")));
		reconResponse.setMerchantId(hexToASCII(bertlvRecon.get("DF2A")));
		reconResponse.setTransactionSequenceNumber(hexToASCII(bertlvRecon.get("DF81")));
		String DF91 = bertlvRecon.get("DF91");
		DF91 = convertSpacedHex(DF91);
		String[] DF91s = DF91.split("7C");

		String[] merchantData = hexToASCII(bertlvRecon.get("DF91")).split("\\|");
		if (merchantData.length > 0) reconResponse.setAppVersion(merchantData[0]);
		if (merchantData.length > 1) reconResponse.setMerchantNameEng(merchantData[1]);
		if (merchantData.length > 2) reconResponse.setMerchantNameArb(CharacterMappingforArabic(DF91s[2].trim()));
		if (merchantData.length > 3) reconResponse.setMerchantAddrEng(merchantData[3]);
		if (merchantData.length > 4) reconResponse.setMerchantAddrArb(CharacterMappingforArabic(DF91s[4].trim()));
		if (merchantData.length > 5) reconResponse.setMerchantAddrEng1(merchantData[5]);
		if (merchantData.length > 6) reconResponse.setMerchantAddrArb1(CharacterMappingforArabic(DF91s[6].trim()));

		String DF93 = bertlvRecon.get("DF93");
		System.out.println("DF93  :" + DF93);

		String DF93ASCII = new String(HexUtils.hexStringToByteArray(DF93));
		System.out.println("DF93ASCII  :" + DF93ASCII);

		String[] DF93Array = DF93ASCII.split(",");
		System.out.println(Arrays.toString(DF93Array));
		int d93Records = Integer.parseInt(DF93Array[0]);
		int counter = 0;
		for (int i = 0; i < d93Records; i++) {
			DF9394Response df93 = new DF9394Response();
			df93.setCardSchemeId(DF93Array[1 + counter]);
			df93.setBankCode(DF93Array[2 + counter]);
			df93.setDebitCount(Integer.parseInt(DF93Array[3 + counter]));
			df93.setDebitAmount(Double.parseDouble(DF93Array[4 + counter]));
			df93.setCreditCount(Integer.parseInt(DF93Array[5 + counter]));
			df93.setCreditAmount(Double.parseDouble(DF93Array[6 + counter]));
			df93.setNaqdAmount(Double.parseDouble(DF93Array[7 + counter]));
			df93.setCashAdvanceAmount(Double.parseDouble(DF93Array[8 + counter]));
			df93.setAuthCount(Integer.parseInt(DF93Array[9 + counter]));
			df93.setTotalCount(Integer.parseInt(DF93Array[10 + counter]));
			df93.setTotalAmount(Double.parseDouble(DF93Array[11 + counter]));
			reconResponse.getDf93List().add(df93);
			counter = counter + 11;
		}
		
		String DF94 = bertlvRecon.get("DF94");
		System.out.println("DF94  :" + DF94);

		String DF94ASCII = new String(HexUtils.hexStringToByteArray(DF94));
		System.out.println("DF94ASCII  :" + DF94ASCII);

		String[] DF94Array = DF94ASCII.split(",");
		System.out.println(Arrays.toString(DF94Array));
		int d94Records = Integer.parseInt(DF94Array[0]);
		counter = 0;
		for (int i = 0; i < d94Records; i++) {
			DF9394Response df94 = new DF9394Response();
			df94.setCardSchemeId(DF94Array[1 + counter]);
			df94.setBankCode(DF94Array[2 + counter]);
			df94.setDebitCount(Integer.parseInt(DF94Array[3 + counter]));
			df94.setDebitAmount(Double.parseDouble(DF94Array[4 + counter]));
			df94.setCreditCount(Integer.parseInt(DF94Array[5 + counter]));
			df94.setCreditAmount(Double.parseDouble(DF94Array[6 + counter]));
			df94.setNaqdAmount(Double.parseDouble(DF94Array[7 + counter]));
			df94.setCashAdvanceAmount(Double.parseDouble(DF94Array[8 + counter]));
			df94.setAuthCount(Integer.parseInt(DF94Array[9 + counter]));
			df94.setTotalCount(Integer.parseInt(DF94Array[10 + counter]));
			df94.setTotalAmount(Double.parseDouble(DF94Array[11 + counter]));
			reconResponse.getDf94List().add(df94);
			counter = counter + 11;
		}
		
		String DF95 = bertlvRecon.get("DF95");
		System.out.println("DF95  :" + DF95);

		String DF95ASCII = new String(HexUtils.hexStringToByteArray(DF95));
		System.out.println("DF95ASCII  :" + DF95ASCII);

		String[] DF95Array = DF95ASCII.split(",");
		System.out.println(Arrays.toString(DF95Array));
		int d95Records = Integer.parseInt(DF95Array[0]);
		counter = 0;
		for (int i = 0; i < d95Records; i++) {
			DF95Response df95 = new DF95Response();
			df95.setCardSchemeId(DF95Array[1 + counter]);
			df95.setBankCode(DF95Array[2 + counter]);
			df95.setPurchaseOffCount(Integer.parseInt(DF95Array[3 + counter]));
			df95.setPurchaseOffAmount(Double.parseDouble(DF95Array[4 + counter]));
			df95.setPurchaseOnCount(Integer.parseInt(DF95Array[5 + counter]));
			df95.setPurchaseOnAmount(Double.parseDouble(DF95Array[6 + counter]));
			df95.setPurchaseNAQDCount(Integer.parseInt(DF95Array[7 + counter]));
			df95.setPurchaseNAQDAmount(Double.parseDouble(DF95Array[8 + counter]));
			df95.setReversalCount(Integer.parseInt(DF95Array[9 + counter]));
			df95.setReversalAmount(Double.parseDouble(DF95Array[10 + counter]));
			
			df95.setRefundCount(Integer.parseInt(DF95Array[11 + counter]));
			df95.setRefundAmount(Double.parseDouble(DF95Array[12 + counter]));
			df95.setCompletionCount(Integer.parseInt(DF95Array[13 + counter]));
			df95.setCompletionAmount(Double.parseDouble(DF95Array[14 + counter]));
			reconResponse.getDf95List().add(df95);
			counter = counter + 14;
		}
		

		return reconResponse;
	}


	public static void main(String[] args) throws ParseException {
		ReconResponse reconResponse = ReconResponse.parseResponse(
				"DF0B06303030313231DF2703353030DF891D32303137303630373138353931317C3230313730363037313930303136DF9203353730DF291036333435303030313031343536393638DF2A0F343530313039303033363830202020DF936F342C41582C414D45582C302C302C302C302C302C302C302C302C302C4D432C534142422C302C302C302C302C302C302C302C302C302C50312C534142422C302C302C302C302C302C302C302C302C302C56432C534142422C312C302E31302C302C302C302C302C312C312C302E3130DF946F342C41582C414D45582C302C302C302C302C302C302C302C302C302C4D432C534142422C302C302C302C302C302C302C302C302C302C50312C534142422C302C302C302C302C302C302C302C302C302C56432C534142422C312C302E31302C302C302C302C302C312C312C302E3130DF958184342C41582C414D45582C302C302C302C302C302C302C302C302C302C302C302C302C4D432C534142422C302C302C302C302C302C302C302C302C302C302C302C302C50312C534142422C302C302C302C302C302C302C302C302C302C302C302C302C56432C534142422C302C302C312C302E31302C302C302C302C302C302C302C302C30DF811000000000000000000000000000000000DF9146312E30327C416C205368617965652054726164696E677C9CE69F98A7C420B0A6CE987C48656164204F66666963657CAD97E6B3CEA7C420CFA6CEA8A7C47C7CD39FE6CEA7C421DF870453414242DF880435363531");
		System.out.println(reconResponse);
	}


	public List<DF9394Response> getDf93List() {
		return df93List;
	}


	public void setDf93List(List<DF9394Response> df93List) {
		this.df93List = df93List;
	}


	public List<DF9394Response> getDf94List() {
		return df94List;
	}


	public void setDf94List(List<DF9394Response> df94List) {
		this.df94List = df94List;
	}


	public List<DF95Response> getDf95List() {
		return df95List;
	}


	public void setDf95List(List<DF95Response> df95List) {
		this.df95List = df95List;
	}


	public Date getRequestDate() {
		return requestDate;
	}


	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}


	public Date getResponseDate() {
		return responseDate;
	}


	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}


	public String getStan() {
		return stan;
	}


	public void setStan(String stan) {
		this.stan = stan;
	}


	public String getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}


	public String getTerminalID() {
		return terminalID;
	}


	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}


	public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	public String getTransactionSequenceNumber() {
		return transactionSequenceNumber;
	}


	public void setTransactionSequenceNumber(String transactionSequenceNumber) {
		this.transactionSequenceNumber = transactionSequenceNumber;
	}


	public String getAppVersion() {
		return appVersion;
	}


	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}


	public String getMerchantNameEng() {
		return merchantNameEng;
	}


	public void setMerchantNameEng(String merchantNameEng) {
		this.merchantNameEng = merchantNameEng;
	}


	public String getMerchantNameArb() {
		return merchantNameArb;
	}


	public void setMerchantNameArb(String merchantNameArb) {
		this.merchantNameArb = merchantNameArb;
	}


	public String getMerchantAddrEng() {
		return merchantAddrEng;
	}


	public void setMerchantAddrEng(String merchantAddrEng) {
		this.merchantAddrEng = merchantAddrEng;
	}


	public String getMerchantAddrEng1() {
		return merchantAddrEng1;
	}


	public void setMerchantAddrEng1(String merchantAddrEng1) {
		this.merchantAddrEng1 = merchantAddrEng1;
	}


	public String getMerchantAddrArb() {
		return merchantAddrArb;
	}


	public void setMerchantAddrArb(String merchantAddrArb) {
		this.merchantAddrArb = merchantAddrArb;
	}


	public String getMerchantAddrArb1() {
		return merchantAddrArb1;
	}


	public void setMerchantAddrArb1(String merchantAddrArb1) {
		this.merchantAddrArb1 = merchantAddrArb1;
	}


	public static String convertSpacedHex(String hex) {
		StringBuilder spacedHex = new StringBuilder("");
		for (int i = 0; i < hex.length(); i = i + 2) {
			spacedHex.append(hex.substring(i, i + 2) + " ");
		}
		return spacedHex.toString().trim();
	}


	public static String CharacterMappingforArabic(String originalHexString) {

		int loopIterator = 0;
		originalHexString.trim();
		String[] splittedString = originalHexString.split(" ");
		String arabicResult = "";

		for (loopIterator = (splittedString.length - 1); loopIterator >= 0; loopIterator--) {

			if (splittedString[loopIterator].trim().equalsIgnoreCase("80")) arabicResult += "٠";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("81")) arabicResult += "١";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("82")) arabicResult += "۲";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("83")) arabicResult += "٣";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("84")) arabicResult += "٤";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("85")) arabicResult += "۵";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("86")) arabicResult += "٦";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("87")) arabicResult += "۷";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("88")) arabicResult += "۸";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("89")) arabicResult += "۹";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DF")) arabicResult += "ﻩ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("25")) arabicResult += "٪";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BC")) arabicResult += ";";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BD")) arabicResult += "?";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BE")) arabicResult += "ﺀ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BF") || splittedString[loopIterator].trim().equalsIgnoreCase("8C")) arabicResult += "ﺁ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C0") || splittedString[loopIterator].trim().equalsIgnoreCase("8B")) arabicResult += "ﺃ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C2") || splittedString[loopIterator].trim().equalsIgnoreCase("8D")) arabicResult += "ﺇ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C1")) arabicResult += "ﺆ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("B3")) arabicResult += "ﺋ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("B9")) arabicResult += "ﺉ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C4")) arabicResult += "ا";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9F")) arabicResult += "ﺎ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C5")) arabicResult += "ﺏ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("8E")) arabicResult += "ﺑ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("6E") || splittedString[loopIterator].trim().equalsIgnoreCase("B0")) arabicResult += "ﺔ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("8F")) arabicResult += "ﺗ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C7")) arabicResult += "ﺕ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("90")) arabicResult += "ث";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("91")) arabicResult += "ﺟ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("92")) arabicResult += "ﺝ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("93")) arabicResult += "ﺣ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("94")) arabicResult += "ﺡ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("95")) arabicResult += "ﺧ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("96")) arabicResult += "ﺥ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CC")) arabicResult += "ﺩ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CD")) arabicResult += "ﺫ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CE")) arabicResult += "ﺭ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CF")) arabicResult += "ﺯ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("97")) arabicResult += "ﺳ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D0")) arabicResult += "ﺱ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("98")) arabicResult += "ﺸ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D1")) arabicResult += "ﺵ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("99")) arabicResult += "ﺻ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D2")) arabicResult += "ﺹ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("8A")) arabicResult += "ﺿ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D3")) arabicResult += "ﺽ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D4")) arabicResult += "ﻁ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D5")) arabicResult += "ﻅ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9A")) arabicResult += "ﻋ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9B")) arabicResult += "ﻌ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9C")) arabicResult += "ﻊ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D6")) arabicResult += "ﻉ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9D")) arabicResult += "ﻏ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9E")) arabicResult += "ﻐ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A0")) arabicResult += "ﻎ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D7")) arabicResult += "ﻍ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("2D")) arabicResult += "۰";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A1") || splittedString[loopIterator].trim().equalsIgnoreCase("A2")) arabicResult += "ﻓ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D9")) arabicResult += "ﻑ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A5")) arabicResult += "ﻗ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A4") || splittedString[loopIterator].trim().equalsIgnoreCase("DA")) arabicResult += "ﻕ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A6")) arabicResult += "ﮐ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DB")) arabicResult += "ﻙ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A7")) arabicResult += "ﻟ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DC")) arabicResult += "ﻝ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A8")) arabicResult += "ﻣ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DD")) arabicResult += "ﻡ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A9")) arabicResult += "ﻧ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DE")) arabicResult += "ﻥ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("AA") || splittedString[loopIterator].trim().equalsIgnoreCase("AC")) arabicResult += "ﻪ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("AB")) arabicResult += "ﻬ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E0")) arabicResult += "ﻭ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E5") || splittedString[loopIterator].trim().equalsIgnoreCase("E1")) arabicResult += "ﻯ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("B1")) arabicResult += "ﯽ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E6")) arabicResult += "ﻳ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("AD")) arabicResult += "ﻲ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C7") || splittedString[loopIterator].trim().equalsIgnoreCase("B4") || splittedString[loopIterator].trim().equalsIgnoreCase("B5")) arabicResult += "ﻻ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C3") || splittedString[loopIterator].trim().equalsIgnoreCase("B6") || splittedString[loopIterator].trim().equalsIgnoreCase("B7")) arabicResult += "ﻷ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C2") || splittedString[loopIterator].trim().equalsIgnoreCase("B8") || splittedString[loopIterator].trim().equalsIgnoreCase("BB")) arabicResult += "ﻵ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C5") || splittedString[loopIterator].trim().equalsIgnoreCase("E3") || splittedString[loopIterator].trim().equalsIgnoreCase("E4")) arabicResult += "ﻹ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("20")) arabicResult += " ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("2E")) arabicResult += ".";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E2")) arabicResult += "ﻱ";

		}
		return arabicResult;
	}


	public static String hexToASCII(String hexString) {
		try {
			return new String(HexUtils.hexStringToByteArray(hexString), "UTF8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString;
	}


	@Override
	public String toString() {
		return "ReconResponse [requestDate=" + requestDate + "\r\n responseDate=" + responseDate + "\r\n stan=" + stan + "\r\n responseCode=" + responseCode + "\r\n terminalID=" + terminalID + "\r\n merchantId=" + merchantId + "\r\n transactionSequenceNumber="
				+ transactionSequenceNumber + "\r\n appVersion=" + appVersion + "\r\n merchantNameEng=" + merchantNameEng + "\r\n merchantNameArb=" + merchantNameArb + "\r\n merchantAddrEng=" + merchantAddrEng + "\r\n merchantAddrEng1=" + merchantAddrEng1
				+ "\r\n merchantAddrArb=" + merchantAddrArb + "\r\n merchantAddrArb1=" + merchantAddrArb1 + "\r\n df93List=" + df93List + "\r\n df94List=" + df94List + "\r\n df95List=" + df95List + "]";
	}
	
	

}
