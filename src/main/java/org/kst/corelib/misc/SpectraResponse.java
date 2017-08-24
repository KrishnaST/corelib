package org.kst.corelib.misc;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeMap;

import org.kst.corelib.datautils.HexUtils;
import org.kst.corelib.tlv.BERTLV;

public class SpectraResponse {

	private String PAN;
	private Double amount;
	private String stan;
	private Date requestDate;
	private Date responseDate;
	private String cardExpiry;
	private String RRN;
	private String authCode;
	private String responseCode;
	private String TID;
	private String MID;
	private Double additionalAmount;
	private String sequenceNo;
	private String cardScheme;
	private String bankId;
	private String merchantCategory;
	private String cardSchemeLabel;
	private String transMode;
	private String AID;
	private String TVR;
	private String TSI;
	private String cryptResult;
	private String cvmResult;
	private String cryptogram;
	private String cardHolderVerificationMethod = "";
	private String appVersion;
	private String merchantNameEng;
	private String merchantNameArb;
	private String merchantAddrEng;
	private String merchantAddrArb;
	private String merchantAddrEng1;
	private String merchantAddrArb1;
	private String hwSerial;

	public static void main(String[] args) {
		String response_Swap_No_PIN = "DF0B06303031303035DF2703353030DF891D32303137303531383039343032387C3230313730353138303934303239DF9203353730DF291036333435303030313031343536393638DF2A0F343530313039303033363830202020DF936F342C41582C414D45582C302C302C302C302C302C302C302C302C302C4D432C534142422C312C302E31302C302C302C302C302C312C312C302E31302C50312C534142422C302C302C302C302C302C302C302C302C302C56432C534142422C302C302C302C302C302C302C302C302C30DF946F342C41582C414D45582C302C302C302C302C302C302C302C302C302C4D432C534142422C312C302E31302C302C302C302C302C312C312C302E31302C50312C534142422C302C302C302C302C302C302C302C302C302C56432C534142422C302C302C302C302C302C302C302C302C30DF9584342C41582C414D45582C302C302C302C302C302C302C302C302C302C302C302C302C4D432C534142422C302C302C312C302E31302C302C302C302C302C302C302C302C302C50312C534142422C302C302C302C302C302C302C302C302C302C302C302C302C56432C534142422C302C302C302C302C302C302C302C302C302C302C302C30DF811000000000000000000000000000000000DF9146312E30317C416C205368617965652054726164696E677C9CE69F98A7C420B0A6CE987C48656164204F66666963657CAD97E6B3CEA7C420CFA6CEA8A7C47C7CD39FE6CEA7C421DF870453414242DF880435363531";
		
		//String response_EMV_Pin = "DF02103535393630312A2A2A2A2A2A34393737DF0406000000000010DF0B06303030363833DF0C0C313730343230313430353234DF0E022109DF250C373131303134303530343236DF2606363639383036DF2703303030DF291036333435303030313031343536393638DF82024D43DF2A0F343530313039303033363830202020DF3606000000000000DF870453414242DF880435363531DF891D32303137303432303134303532347C3230313730343230313430353331DF90534445424954204D4153544552434152447C4449505045447C41303030303030303034313031307C303030303034383030307C453830307C34307C3432303330307C333546323234433442393631323144367C32DF811030303030303030303030303030303042DF9146312E30317C416C205368617965652054726164696E677C9CE69F98A7C420B0A6CE987C48656164204F66666963657CAD97E6B3CEA7C420CFA6CEA8A7C47C7CD39FE6CEA7C421DF96083553303030303537";
		
		//String response_ContactLess_No_Pin = "DF02103437383637392A2A2A2A2A2A36303230DF0406000000000100DF0B06303030363738DF0C0C313730343230313335393139DF0E022110DF250C373131303133353930343233DF2606353733333237DF2703303030DF291036333435303030313031343536393638DF82025643DF2A0F343530313039303033363830202020DF3606000000000000DF870453414242DF880435363531DF891D32303137303432303133353931397C3230313730343230313335393233DF9052566973612044656269747C434F4E544143544C4553537C41303030303030303033313031307C303030303030303030307C303030307C38307C3030303030307C464643393334423636384430393839427C33DF811030303030303030303030303030303038DF9146312E30317C416C205368617965652054726164696E677C9CE69F98A7C420B0A6CE987C48656164204F66666963657CAD97E6B3CEA7C420CFA6CEA8A7C47C7CD39FE6CEA7C421DF96083553303030303537";
		try {
			System.out.println(parseSpectraResponse(response_Swap_No_PIN));
			
			//System.out.println(parseSpectraResponse(response_EMV_Pin).getCardHolderVerificationMethod());
			
			//System.out.println(parseSpectraResponse(response_ContactLess_No_Pin).getCardHolderVerificationMethod());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static SpectraResponse parseSpectraResponse(String responseString) {
		try {
			SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
			TreeMap<String, String> responseMap = BERTLV.parseTLV(responseString, BERTLV.ENCODING.HEX).getTlvMap();
			SpectraResponse spectraResponse = new SpectraResponse();

			String add_amount = (responseMap.get("DF36") == null) ? "0000000000" : responseMap.get("DF36");

			spectraResponse.setPAN(hexToASCII(responseMap.get("DF02")));
			spectraResponse.setAmount(Double.parseDouble(responseMap.get("DF04")) / 100);
			spectraResponse.setStan(hexToASCII(responseMap.get("DF0B")));
			spectraResponse.setCardExpiry(responseMap.get("DF0E"));
			spectraResponse.setRRN(hexToASCII(responseMap.get("DF25")));
			spectraResponse.setAuthCode(hexToASCII(responseMap.get("DF26")));
			spectraResponse.setResponseCode(hexToASCII(responseMap.get("DF27")));
			spectraResponse.setTID(hexToASCII(responseMap.get("DF29")));
			spectraResponse.setMID(hexToASCII(responseMap.get("DF2A")));

			spectraResponse.setAdditionalAmount(Double.parseDouble(add_amount) / 100);
			spectraResponse.setSequenceNo(hexToASCII(responseMap.get("DF81")));
			spectraResponse.setCardScheme(hexToASCII(responseMap.get("DF82")));
			spectraResponse.setBankId(hexToASCII(responseMap.get("DF87")));
			spectraResponse.setMerchantCategory(hexToASCII(responseMap.get("DF88")));

			String[] requestResponseTime = hexToASCII(responseMap.get("DF89")).split("\\|");
			if(requestResponseTime.length > 0) spectraResponse.setRequestDate(yyyyMMddHHmmss.parse(requestResponseTime[0].trim()));
			if(requestResponseTime.length > 1) spectraResponse.setResponseDate(yyyyMMddHHmmss.parse(requestResponseTime[1].trim()));

			String[] emvTags = hexToASCII(responseMap.get("DF90")).split("\\|");
			if(emvTags.length > 0) spectraResponse.setCardSchemeLabel(emvTags[0].trim());
			if(emvTags.length > 1) spectraResponse.setTransMode(emvTags[1].trim());
			System.out.println(Arrays.toString(emvTags));
			if(emvTags[1].trim().equalsIgnoreCase("SWIPED")){
				spectraResponse.setCardHolderVerificationMethod(emvTags[2].trim());
			}
			else{
				if(emvTags.length > 2) spectraResponse.setAID(emvTags[2].trim());
				if(emvTags.length > 3) spectraResponse.setTVR(emvTags[3].trim());
				if(emvTags.length > 4) spectraResponse.setTSI(emvTags[4].trim());
				if(emvTags.length > 5) spectraResponse.setCryptResult(emvTags[5].trim());
				if(emvTags.length > 6) spectraResponse.setCvmResult(emvTags[6].trim());
				if(emvTags.length > 7) spectraResponse.setCryptogram(emvTags[7].trim());
				if(emvTags.length > 8) spectraResponse.setCardHolderVerificationMethod(emvTags[8].trim());
			}
			
			
			String DF91 = responseMap.get("DF91");
			DF91 = convertSpacedHex(DF91);
			String[] DF91s = DF91.split("7C");


			String[] merchantData = hexToASCII(responseMap.get("DF91")).split("\\|");
			if(merchantData.length > 0) spectraResponse.setAppVersion(merchantData[0]);
			if(merchantData.length > 1) spectraResponse.setMerchantNameEng(merchantData[1]);
			if(merchantData.length > 2) spectraResponse.setMerchantNameArb(CharacterMappingforArabic(DF91s[2].trim()));
			if(merchantData.length > 3) spectraResponse.setMerchantAddrEng(merchantData[3]);
			if(merchantData.length > 4) spectraResponse.setMerchantAddrArb(CharacterMappingforArabic(DF91s[4].trim()));
			if(merchantData.length > 5) spectraResponse.setMerchantAddrEng1(merchantData[5]);
			if(merchantData.length > 6) spectraResponse.setMerchantAddrArb1(CharacterMappingforArabic(DF91s[6].trim()));

			spectraResponse.setHwSerial(hexToASCII(responseMap.get("DF96").trim()));
			return spectraResponse;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;


}

	public static String convertSpacedHex(String hex) {
		StringBuilder spacedHex = new StringBuilder("");
		for (int i = 0; i < hex.length(); i = i + 2) {
			spacedHex.append(hex.substring(i, i + 2) + " ");
		}
		return spacedHex.toString().trim();
	}

	public static String hexToASCII(String hexString) {
		try {
			return new String(HexUtils.hexStringToByteArray(hexString), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String PAN) {
		this.PAN = PAN;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
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

	public String getCardExpiry() {
		return cardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String TID) {
		this.TID = TID;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String MID) {
		this.MID = MID;
	}

	public Double getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(Double additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getCardScheme() {
		return cardScheme;
	}

	public void setCardScheme(String cardScheme) {
		this.cardScheme = cardScheme;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getMerchantCategory() {
		return merchantCategory;
	}

	public void setMerchantCategory(String merchantCategory) {
		this.merchantCategory = merchantCategory;
	}

	public String getCardSchemeLabel() {
		return cardSchemeLabel;
	}

	public void setCardSchemeLabel(String cardSchemeLabel) {
		this.cardSchemeLabel = cardSchemeLabel;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public String getAID() {
		return AID;
	}

	public void setAID(String aID) {
		AID = aID;
	}

	public String getTVR() {
		return TVR;
	}

	public void setTVR(String tVR) {
		TVR = tVR;
	}

	public String getTSI() {
		return TSI;
	}

	public void setTSI(String tSI) {
		TSI = tSI;
	}

	public String getCryptResult() {
		return cryptResult;
	}

	public void setCryptResult(String cryptResult) {
		this.cryptResult = cryptResult;
	}

	public String getCvmResult() {
		return cvmResult;
	}

	public void setCvmResult(String cvmResult) {
		this.cvmResult = cvmResult;
	}

	public String getCryptogram() {
		return cryptogram;
	}

	public void setCryptogram(String cryptogram) {
		this.cryptogram = cryptogram;
	}

	public String getCardHolderVerificationMethod() {
		return cardHolderVerificationMethod;
	}

	public void setCardHolderVerificationMethod(String cardHolderVerificationMethod) {
		this.cardHolderVerificationMethod = cardHolderVerificationMethod;
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

	public String getMerchantAddrArb() {
		return merchantAddrArb;
	}

	public void setMerchantAddrArb(String merchantAddrArb) {
		this.merchantAddrArb = merchantAddrArb;
	}

	public String getMerchantAddrEng1() {
		return merchantAddrEng1;
	}

	public void setMerchantAddrEng1(String merchantAddrEng1) {
		this.merchantAddrEng1 = merchantAddrEng1;
	}

	public String getMerchantAddrArb1() {
		return merchantAddrArb1;
	}

	public void setMerchantAddrArb1(String merchantAddrArb1) {
		this.merchantAddrArb1 = merchantAddrArb1;
	}

	public String getHwSerial() {
		return hwSerial;
	}

	public void setHwSerial(String hwSerial) {
		this.hwSerial = hwSerial;
	}

	@Override
	public String toString() {
		return "SpectraResponse [PAN=" + PAN + "\r\n amount=" + amount + "\r\n stan=" + stan + "\r\n requestDate=" + requestDate
				+ "\r\n responseDate=" + responseDate + "\r\n cardExpiry=" + cardExpiry + "\r\n RRN=" + RRN + "\r\n authCode="
				+ authCode + "\r\n responseCode=" + responseCode + "\r\n TID=" + TID + "\r\n MID=" + MID + "\r\n additionalAmount="
				+ additionalAmount + "\r\n sequenceNo=" + sequenceNo + "\r\n cardScheme=" + cardScheme + "\r\n bankId=" + bankId
				+ "\r\n merchantCategory=" + merchantCategory + "\r\n cardSchemeLabel=" + cardSchemeLabel + "\r\n transMode="
				+ transMode + "\r\n AID=" + AID + "\r\n TVR=" + TVR + "\r\n TSI=" + TSI + "\r\n cryptResult=" + cryptResult
				+ "\r\n cvmResult=" + cvmResult + "\r\n cryptogram=" + cryptogram + "\r\n cardHolderVerificationMethod="
				+ cardHolderVerificationMethod + "\r\n appVersion=" + appVersion + "\r\n merchantNameEng=" + merchantNameEng
				+ "\r\n merchantNameArb=" + merchantNameArb + "\r\n merchantAddrEng=" + merchantAddrEng + "\r\n merchantAddrArb="
				+ merchantAddrArb + "\r\n merchantAddrEng1=" + merchantAddrEng1 + "\r\n merchantAddrArb1=" + merchantAddrArb1
				+ "\r\n hwSerial=" + hwSerial + "]";
	}

	public static String CharacterMappingforArabic(String originalHexString) 
	{

		int loopIterator = 0;
		originalHexString.trim();
		String[] splittedString = originalHexString.split(" ");
		String arabicResult = "";

		for (loopIterator = (splittedString.length - 1); loopIterator >= 0; loopIterator--) {

			if (splittedString[loopIterator].trim().equalsIgnoreCase("80"))
				arabicResult += "٠";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("81"))
				arabicResult += "١";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("82"))
				arabicResult += "۲";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("83"))
				arabicResult += "٣";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("84"))
				arabicResult += "٤";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("85"))
				arabicResult += "۵";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("86"))
				arabicResult += "٦";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("87"))
				arabicResult += "۷";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("88"))
				arabicResult += "۸";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("89"))
				arabicResult += "۹";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DF"))
				arabicResult += "ﻩ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("25"))
				arabicResult += "٪";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BC"))
				arabicResult += ";";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BD"))
				arabicResult += "?";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BE"))
				arabicResult += "ﺀ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("BF")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("8C"))
				arabicResult += "ﺁ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C0")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("8B"))
				arabicResult += "ﺃ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C2")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("8D"))
				arabicResult += "ﺇ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C1"))
				arabicResult += "ﺆ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("B3"))
				arabicResult += "ﺋ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("B9"))
				arabicResult += "ﺉ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C4"))
				arabicResult += "ا";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9F"))
				arabicResult += "ﺎ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C5"))
				arabicResult += "ﺏ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("8E"))
				arabicResult += "ﺑ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("6E")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("B0"))
				arabicResult += "ﺔ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("8F"))
				arabicResult += "ﺗ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C7"))
				arabicResult += "ﺕ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("90"))
				arabicResult += "ث";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("91"))
				arabicResult += "ﺟ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("92"))
				arabicResult += "ﺝ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("93"))
				arabicResult += "ﺣ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("94"))
				arabicResult += "ﺡ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("95"))
				arabicResult += "ﺧ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("96"))
				arabicResult += "ﺥ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CC"))
				arabicResult += "ﺩ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CD"))
				arabicResult += "ﺫ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CE"))
				arabicResult += "ﺭ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("CF"))
				arabicResult += "ﺯ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("97"))
				arabicResult += "ﺳ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D0"))
				arabicResult += "ﺱ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("98"))
				arabicResult += "ﺸ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D1"))
				arabicResult += "ﺵ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("99"))
				arabicResult += "ﺻ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D2"))
				arabicResult += "ﺹ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("8A"))
				arabicResult += "ﺿ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D3"))
				arabicResult += "ﺽ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D4"))
				arabicResult += "ﻁ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D5"))
				arabicResult += "ﻅ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9A"))
				arabicResult += "ﻋ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9B"))
				arabicResult += "ﻌ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9C"))
				arabicResult += "ﻊ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D6"))
				arabicResult += "ﻉ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9D"))
				arabicResult += "ﻏ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("9E"))
				arabicResult += "ﻐ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A0"))
				arabicResult += "ﻎ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D7"))
				arabicResult += "ﻍ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("2D"))
				arabicResult += "۰";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A1")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("A2"))
				arabicResult += "ﻓ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("D9"))
				arabicResult += "ﻑ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A5"))
				arabicResult += "ﻗ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A4")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("DA"))
				arabicResult += "ﻕ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A6"))
				arabicResult += "ﮐ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DB"))
				arabicResult += "ﻙ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A7"))
				arabicResult += "ﻟ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DC"))
				arabicResult += "ﻝ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A8"))
				arabicResult += "ﻣ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DD"))
				arabicResult += "ﻡ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("A9"))
				arabicResult += "ﻧ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("DE"))
				arabicResult += "ﻥ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("AA")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("AC"))
				arabicResult += "ﻪ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("AB"))
				arabicResult += "ﻬ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E0"))
				arabicResult += "ﻭ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E5")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("E1"))
				arabicResult += "ﻯ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("B1"))
				arabicResult += "ﯽ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E6"))
				arabicResult += "ﻳ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("AD"))
				arabicResult += "ﻲ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C7")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("B4")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("B5"))
				arabicResult += "ﻻ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C3")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("B6")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("B7"))
				arabicResult += "ﻷ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C2")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("B8")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("BB"))
				arabicResult += "ﻵ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("C5")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("E3")
					|| splittedString[loopIterator].trim().equalsIgnoreCase("E4"))
				arabicResult += "ﻹ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("20"))
				arabicResult += " ";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("2E"))
				arabicResult += ".";
			else if (splittedString[loopIterator].trim().equalsIgnoreCase("E2"))
				arabicResult += "ﻱ";

		}
		return arabicResult;
	}



}
