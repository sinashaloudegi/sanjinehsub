package ir.iconish.sanjinehsub.data.model;

public  class BazaarPurchaseInfo  {

	private String purchaseitemtype;
	private String purchasepackagename;
	private String purchasesku;
	private Long purchasetime ;
	private Integer purchasestate;
	private String developerpayload;
	private String purchasetoken;
	private String purchaseorderid;
	private String purchaseoriginaljson;
	private String purchasesignature;
	private String msisdn;


	public String getPurchaseitemtype() {
		return purchaseitemtype;
	}

	public void setPurchaseitemtype(String purchaseitemtype) {
		this.purchaseitemtype = purchaseitemtype;
	}

	public String getPurchasepackagename() {
		return purchasepackagename;
	}

	public void setPurchasepackagename(String purchasepackagename) {
		this.purchasepackagename = purchasepackagename;
	}

	public String getPurchasesku() {
		return purchasesku;
	}

	public void setPurchasesku(String purchasesku) {
		this.purchasesku = purchasesku;
	}

	public Long getPurchasetime() {
		return purchasetime;
	}

	public void setPurchasetime(Long purchasetime) {
		this.purchasetime = purchasetime;
	}

	public Integer getPurchasestate() {
		return purchasestate;
	}

	public void setPurchasestate(Integer purchasestate) {
		this.purchasestate = purchasestate;
	}

	public String getDeveloperpayload() {
		return developerpayload;
	}

	public void setDeveloperpayload(String developerpayload) {
		this.developerpayload = developerpayload;
	}

	public String getPurchasetoken() {
		return purchasetoken;
	}

	public void setPurchasetoken(String purchasetoken) {
		this.purchasetoken = purchasetoken;
	}

	public String getPurchaseorderid() {
		return purchaseorderid;
	}

	public void setPurchaseorderid(String purchaseorderid) {
		this.purchaseorderid = purchaseorderid;
	}

	public String getPurchaseoriginaljson() {
		return purchaseoriginaljson;
	}

	public void setPurchaseoriginaljson(String purchaseoriginaljson) {
		this.purchaseoriginaljson = purchaseoriginaljson;
	}

	public String getPurchasesignature() {
		return purchasesignature;
	}

	public void setPurchasesignature(String purchasesignature) {
		this.purchasesignature = purchasesignature;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	@Override
	public String toString() {
		return "BazaarPurchaseInfo{" +
			", purchaseitemtype='" + purchaseitemtype + '\'' +
			", purchasepackagename='" + purchasepackagename + '\'' +
			", purchasesku='" + purchasesku + '\'' +
			", purchasetime=" + purchasetime +
			", purchasestate=" + purchasestate +
			", developerpayload='" + developerpayload + '\'' +
			", purchasetoken='" + purchasetoken + '\'' +
			", purchaseorderid='" + purchaseorderid + '\'' +
			", purchaseoriginaljson='" + purchaseoriginaljson + '\'' +
			", purchasesignature='" + purchasesignature + '\'' +
			", msisdn='" + msisdn + '\'' +
			'}';
	}
}