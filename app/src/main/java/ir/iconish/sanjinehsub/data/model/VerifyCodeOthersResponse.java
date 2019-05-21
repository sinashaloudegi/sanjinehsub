package ir.iconish.sanjinehsub.data.model;

public class VerifyCodeOthersResponse {

  private int statusCode;
  private String description;
  private String noReportReqToken;

  public String getNoReportReqToken() {
    return noReportReqToken;
  }

  public void setNoReportReqToken(String noReportReqToken) {
    this.noReportReqToken = noReportReqToken;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "VerifyCodeOthersResponse{" +
            "statusCode=" + statusCode +
            ", description='" + description + '\'' +
            ", noReportReqToken='" + noReportReqToken + '\'' +
            '}';
  }
}
