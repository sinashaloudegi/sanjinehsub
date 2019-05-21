package ir.iconish.sanjinehsub.data.model;

public class DetailContract {
    private String contractType;
    private String creditorName;
    private String currency;
    private int totalOpenConract;
    private int outstandingAmount;
    private int overdueAmount;
    private int totalTerminateContract;


    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTotalOpenConract() {
        return totalOpenConract;
    }

    public void setTotalOpenConract(int totalOpenConract) {
        this.totalOpenConract = totalOpenConract;
    }

    public int getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(int outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public int getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(int overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public int getTotalTerminateContract() {
        return totalTerminateContract;
    }

    public void setTotalTerminateContract(int totalTerminateContract) {
        this.totalTerminateContract = totalTerminateContract;
    }
}
