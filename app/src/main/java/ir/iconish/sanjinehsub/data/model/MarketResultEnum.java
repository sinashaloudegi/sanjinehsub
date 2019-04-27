package ir.iconish.sanjinehsub.data.model;


public enum MarketResultEnum {

  MATCH(2000, "MATCH"),
  NOT_MATCH(2001, "NOT_MATCH"),
  CREDITSOCRE_ERROR(3000, "CREDITSOCRE_ERROR"),
  CREDITSOCRE_API_ERROR(3001, "CREDITSOCRE_API_ERROR"),
  BALANCE_ERROR(4000, "BALANCE_ERROR"),
  BALANCE_API_ERROR(4001, "BALANCE_API_ERROR"),
  UNKNOWN_ERROR(5000, "UNKNOWN_ERROR");


  private String name;

  private Integer id;


   MarketResultEnum(Integer id, String name) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public static MarketResultEnum fromValue(int value) {
   if (value == 2000) {
      return MarketResultEnum.MATCH;
    } if (value == 2001) {
      return MarketResultEnum.NOT_MATCH;
    } if (value == 3000) {
      return MarketResultEnum.CREDITSOCRE_ERROR;
    }
    if (value == 4000){
      return MarketResultEnum.BALANCE_ERROR;
    }
    return MarketResultEnum.UNKNOWN_ERROR;
  }
}
