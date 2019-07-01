package ir.iconish.sanjinehsub.data.model;

public class CreditScorePreProcess {


    private String reqToken;
    private int reportStateId;
    private String reportDescryption;


    public String getReqToken() {
        return reqToken;
    }

    public void setReqToken(String reqToken) {
        this.reqToken = reqToken;
    }

    public int getReportStateId() {
        return reportStateId;
    }

    public void setReportStateId(int reportStateId) {
        this.reportStateId = reportStateId;
    }

    public String getReportDescryption() {
        return reportDescryption;
    }

    public void setReportDescryption(String reportDescryption) {
        this.reportDescryption = reportDescryption;
    }

    @Override
    public String toString() {
        return "CreditScorePreProcess{" +
                ", reqToken='" + reqToken + '\'' +
                ", reportStateId=" + reportStateId +
                ", reportDescryption='" + reportDescryption + '\'' +
                '}';
    }
}
