package ir.iconish.sanjinehsub.data.model;

import java.util.List;

public class FullContract {
    private String lookupsCurrencyCodes;
    private int numberOfOpenContracts;
    private int numberOfTerminatedContracts;
    private int outstandingAmount;
    private int overdueAmount;
    private int key;

    public int getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(int overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    List<DetailContract> detailContractList;


    public String getLookupsCurrencyCodes() {
        return lookupsCurrencyCodes;
    }

    public void setLookupsCurrencyCodes(String lookupsCurrencyCodes) {
        this.lookupsCurrencyCodes = lookupsCurrencyCodes;
    }

    public int getNumberOfOpenContracts() {
        return numberOfOpenContracts;
    }

    public void setNumberOfOpenContracts(int numberOfOpenContracts) {
        this.numberOfOpenContracts = numberOfOpenContracts;
    }

    public int getNumberOfTerminatedContracts() {
        return numberOfTerminatedContracts;
    }

    public void setNumberOfTerminatedContracts(int numberOfTerminatedContracts) {
        this.numberOfTerminatedContracts = numberOfTerminatedContracts;
    }

    public int getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(int outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<DetailContract> getDetailContractList() {
        return detailContractList;
    }

    public void setDetailContractList(List<DetailContract> detailContractList) {
        this.detailContractList = detailContractList;
    }
}
