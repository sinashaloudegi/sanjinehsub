package ir.iconish.sanjinehsub.data.model;

public class Contract {

    private long reportsLastUpdate;
    private String negativeSubjectStatus;
    private String reportsCreditor;


    public long getReportsLastUpdate() {
        return reportsLastUpdate;
    }

    public void setReportsLastUpdate(long reportsLastUpdate) {
        this.reportsLastUpdate = reportsLastUpdate;
    }

    public String getNegativeSubjectStatus() {
        return negativeSubjectStatus;
    }

    public void setNegativeSubjectStatus(String negativeSubjectStatus) {
        this.negativeSubjectStatus = negativeSubjectStatus;
    }

    public String getReportsCreditor() {
        return reportsCreditor;
    }

    public void setReportsCreditor(String reportsCreditor) {
        this.reportsCreditor = reportsCreditor;
    }
}
