package org.kst.corelib.misc;

public class DF9394Response {

	private String cardSchemeId;
	private String bankCode;
	private int debitCount;
	private double debitAmount;
	private int creditCount;
	private double creditAmount;
	private double naqdAmount;
	private double cashAdvanceAmount;
	private int authCount;
	private int totalCount;
	private double totalAmount;


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


	public int getDebitCount() {
		return debitCount;
	}


	public void setDebitCount(int debitCount) {
		this.debitCount = debitCount;
	}


	public double getDebitAmount() {
		return debitAmount;
	}


	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}


	public int getCreditCount() {
		return creditCount;
	}


	public void setCreditCount(int creditCount) {
		this.creditCount = creditCount;
	}


	public double getCreditAmount() {
		return creditAmount;
	}


	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}


	public double getNaqdAmount() {
		return naqdAmount;
	}


	public void setNaqdAmount(double naqdAmount) {
		this.naqdAmount = naqdAmount;
	}


	public double getCashAdvanceAmount() {
		return cashAdvanceAmount;
	}


	public void setCashAdvanceAmount(double cashAdvanceAmount) {
		this.cashAdvanceAmount = cashAdvanceAmount;
	}


	public int getAuthCount() {
		return authCount;
	}


	public void setAuthCount(int authCount) {
		this.authCount = authCount;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}


	@Override
	public String toString() {
		return "DF9394Response [cardSchemeId=" + cardSchemeId + ", bankCode=" + bankCode + ", debitCount=" + debitCount + ", debitAmount=" + debitAmount + ", creditCount=" + creditCount + ", creditAmount=" + creditAmount + ", naqdAmount="
				+ naqdAmount + ", cashAdvanceAmount=" + cashAdvanceAmount + ", authCount=" + authCount + ", totalCount=" + totalCount + ", totalAmount=" + totalAmount + "]";
	}

	
	
}
