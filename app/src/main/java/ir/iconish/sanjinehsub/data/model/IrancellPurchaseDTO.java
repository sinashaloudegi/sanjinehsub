package ir.iconish.sanjinehsub.data.model;

import java.io.Serializable;


public class IrancellPurchaseDTO implements Serializable {

    private String itemType;
    private String orderId;
    private Long purchaseTime;
    private Integer purchaseState;
    private String developerPayload;
    private String token;
    private String originalJson;
    private String signature;
    private Boolean isAutoRenewing;
    private String msisdn;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Integer getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(Integer purchaseState) {
        this.purchaseState = purchaseState;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOriginalJson() {
        return originalJson;
    }

    public void setOriginalJson(String originalJson) {
        this.originalJson = originalJson;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Boolean getAutoRenewing() {
        return isAutoRenewing;
    }

    public void setAutoRenewing(Boolean autoRenewing) {
        isAutoRenewing = autoRenewing;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "IrancellPurchaseDTO{" +
                "itemType='" + itemType + '\'' +
                ", orderId='" + orderId + '\'' +
                ", purchaseTime=" + purchaseTime +
                ", purchaseState=" + purchaseState +
                ", developerPayload='" + developerPayload + '\'' +
                ", token='" + token + '\'' +
                ", originalJson='" + originalJson + '\'' +
                ", signature='" + signature + '\'' +
                ", isAutoRenewing=" + isAutoRenewing +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
