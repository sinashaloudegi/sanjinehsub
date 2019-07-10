package ir.iconish.sanjinehsub.data.model;


import androidx.annotation.NonNull;

public class Sms  {

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

    @NonNull
    @Override
    public String toString() {
        return "SMSDTO{" +
                "reportStateId=" + reportStateId +
                ", reportStateValue='" + reportStateValue + '\'' +
                '}';
    }
}