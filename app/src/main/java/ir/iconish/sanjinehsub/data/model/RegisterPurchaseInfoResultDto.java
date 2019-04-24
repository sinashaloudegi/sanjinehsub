package ir.iconish.sanjinehsub.data.model;

public class RegisterPurchaseInfoResultDto {
  private String reqToken;
  private MarketResultDto marketResultDto;

  public MarketResultDto getMarketResultDto() {
    return marketResultDto;
  }

  public void setMarketResultDto(MarketResultDto marketResultDto) {
    this.marketResultDto = marketResultDto;
  }

  public String getReqToken() {
    return reqToken;
  }

  public void setReqToken(String reqToken) {
    this.reqToken = reqToken;
  }

  @Override
  public String toString() {
    return "RegisterPurchaseInfoResultDto{" +
      "reqToken='" + reqToken + '\'' +
      ", marketResultDto=" + marketResultDto +
      '}';
  }

  public static class MarketResultDto{
    private int marketResultEnumId;
    private String marketResultEnumName;

    public int getMarketResultEnumId() {
      return marketResultEnumId;
    }

    public void setMarketResultEnumId(int marketResultEnumId) {
      this.marketResultEnumId = marketResultEnumId;
    }

    public String getMarketResultEnumName() {
      return marketResultEnumName;
    }

    public void setMarketResultEnumName(String marketResultEnumName) {
      this.marketResultEnumName = marketResultEnumName;
    }

    @Override
    public String toString() {
      return "MarketResultDto{" +
        "marketResultEnumId=" + marketResultEnumId +
        ", marketResultEnumName='" + marketResultEnumName + '\'' +
        '}';
    }
  }
}
