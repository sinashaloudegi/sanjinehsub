package ir.iconish.model.iwallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ir.iconish.dto.iwallet.enums.ChannelEnum;
import ir.iconish.model.iwallet.enums.YesNoEnum;

@Entity
@Table(name="iwbazaarpurchaseinfo")
public  class BazaarPurchaseInfo extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2553407174517962189L;

	@Id
	@SequenceGenerator(name="SEQ_IWBAZAARPURCHASEINFO_GENERATOR", sequenceName="SEQ_IWBAZAARPURCHASEINFO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_IWBAZAARPURCHASEINFO_GENERATOR")
	private Long purchaseid;


	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date purchaseregdate;


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

	public Long getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(Long purchaseid) {
		this.purchaseid = purchaseid;
	}

	public Date getPurchaseregdate() {
		return purchaseregdate;
	}

	public void setPurchaseregdate(Date purchaseregdate) {
		this.purchaseregdate = purchaseregdate;
	}

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
			"purchaseid=" + purchaseid +
			", purchaseregdate=" + purchaseregdate +
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