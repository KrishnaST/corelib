package org.kst.corelib.misc;

public class DF95Response {

	private int totalCardSchemeCount;
	private String cardSchemeId;
	private String bankCode;
	private int purchaseOffCount;
	private double purchaseOffAmount;
	private int purchaseOnCount;
	private double purchaseOnAmount;
	private int purchaseNAQDCount;
	private double purchaseNAQDAmount;
	private int reversalCount;
	private double reversalAmount;
	private int refundCount;
	private double refundAmount;
	private int completionCount;
	private double completionAmount;


	public int getTotalCardSchemeCount() {
		return totalCardSchemeCount;
	}


	public void setTotalCardSchemeCount(int totalCardSchemeCount) {
		this.totalCardSchemeCount = totalCardSchemeCount;
	}


	public String getCardSchemeId() {
		return cardSchemeId;
	}


	public void setCardSchemeId(String cardSchemeId) {
		this.cardSchemeId = cardSchemeId;
	}


	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public int getPurchaseOffCount() {
		return purchaseOffCount;
	}


	public void setPurchaseOffCount(int purchaseOffCount) {
		this.purchaseOffCount = purchaseOffCount;
	}


	public double getPurchaseOffAmount() {
		return purchaseOffAmount;
	}


	public void setPurchaseOffAmount(double purchaseOffAmount) {
		this.purchaseOffAmount = purchaseOffAmount;
	}


	public int getPurchaseOnCount() {
		return purchaseOnCount;
	}


	public void setPurchaseOnCount(int purchaseOnCount) {
		this.purchaseOnCount = purchaseOnCount;
	}


	public double getPurchaseOnAmount() {
		return purchaseOnAmount;
	}


	public void setPurchaseOnAmount(double purchaseOnAmount) {
		this.purchaseOnAmount = purchaseOnAmount;
	}


	public int getPurchaseNAQDCount() {
		return purchaseNAQDCount;
	}


	public void setPurchaseNAQDCount(int purchaseNAQDCount) {
		this.purchaseNAQDCount = purchaseNAQDCount;
	}


	public double getPurchaseNAQDAmount() {
		return purchaseNAQDAmount;
	}


	public void setPurchaseNAQDAmount(double purchaseNAQDAmount) {
		this.purchaseNAQDAmount = purchaseNAQDAmount;
	}


	public int getReversalCount() {
		return reversalCount;
	}


	public void setReversalCount(int reversalCount) {
		this.reversalCount = reversalCount;
	}


	public double getReversalAmount() {
		return reversalAmount;
	}


	public void setReversalAmount(double reversalAmount) {
		this.reversalAmount = reversalAmount;
	}


	public int getRefundCount() {
		return refundCount;
	}


	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}


	public double getRefundAmount() {
		return refundAmount;
	}


	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}


	public int getCompletionCount() {
		return completionCount;
	}


	public void setCompletionCount(int completionCount) {
		this.completionCount = completionCount;
	}


	public double getCompletionAmount() {
		return completionAmount;
	}


	public void setCompletionAmount(double completionAmount) {
		this.completionAmount = completionAmount;
	}


	@Override
	public String toString() {
		return "DF95Response [totalCardSchemeCount=" + totalCardSchemeCount + ", cardSchemeId=" + cardSchemeId + ", bankCode=" + bankCode + ", purchaseOffCount=" + purchaseOffCount + ", purchaseOffAmount=" + purchaseOffAmount + ", purchaseOnCount="
				+ purchaseOnCount + ", purchaseOnAmount=" + purchaseOnAmount + ", purchaseNAQDCount=" + purchaseNAQDCount + ", purchaseNAQDAmount=" + purchaseNAQDAmount + ", reversalCount=" + reversalCount + ", reversalAmount=" + reversalAmount
				+ ", refundCount=" + refundCount + ", refundAmount=" + refundAmount + ", completionCount=" + completionCount + ", completionAmount=" + completionAmount + "]";
	}

	
	
}
