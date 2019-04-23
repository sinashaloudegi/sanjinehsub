package ir.iconish.rest.model;

public class RegisterPurchaseInfoResultDto {
  private boolean purchaseResult;
  private Integer userBalance;

  public boolean isPurchaseResult() {
    return purchaseResult;
  }

  public void setPurchaseResult(boolean purchaseResult) {
    this.purchaseResult = purchaseResult;
  }

  public Integer getUserBalance() {
    return userBalance;
  }

  public void setUserBalance(Integer userBalance) {
    this.userBalance = userBalance;
  }
}
