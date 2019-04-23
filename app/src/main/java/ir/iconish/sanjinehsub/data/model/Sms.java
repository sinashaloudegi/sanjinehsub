package ir.iconish.model.creditscoreclient.dto;


import ir.iconish.common.api.info.BaseInfo;

public class SMSDTO extends BaseInfo {

    private Integer reportStateId;
    private String reportStateValue;

    public Integer getReportStateId() {
        return reportStateId;
    }

    public void setReportStateId(Integer reportStateId) {
        this.reportStateId = reportStateId;
    }

    public String getReportStateValue() {
        return reportStateValue;
    }

    public void setReportStateValue(String reportStateValue) {
        this.reportStateValue = reportStateValue;
    }

    @Override
    public String toString() {
        return "SMSDTO{" +
                "reportStateId=" + reportStateId +
                ", reportStateValue='" + reportStateValue + '\'' +
                '}';
    }
}