package ir.iconish.sanjinehsub.data.model;

import java.util.ArrayList;
import java.util.List;

public class FullInstallment {
    private String negativeContractStatus;
    private String lookupsCurrencyCodes;
    private String purposeOfTheCredit;
    private String reportsContractDataCreditor;
    private String roleOfConnectedSubject;
    private String lookupsTypeOfFinancingInstalments;

    public String getLookupsTypeOfFinancingInstalments() {
        return lookupsTypeOfFinancingInstalments;
    }

    public void setLookupsTypeOfFinancingInstalments(String lookupsTypeOfFinancingInstalments) {
        this.lookupsTypeOfFinancingInstalments = lookupsTypeOfFinancingInstalments;
    }

    private long typesContractDatesStart;
    private long typesContractDatesExpectedEnd;
    private long reportsLastUpdate;
    private String phaseOfOperation;




    public String getNegativeContractStatus() {
        return negativeContractStatus;
    }

    public void setNegativeContractStatus(String negativeContractStatus) {
        this.negativeContractStatus = negativeContractStatus;
    }

    public String getLookupsCurrencyCodes() {
        return lookupsCurrencyCodes;
    }

    public void setLookupsCurrencyCodes(String lookupsCurrencyCodes) {
        this.lookupsCurrencyCodes = lookupsCurrencyCodes;
    }

    public String getPurposeOfTheCredit() {
        return purposeOfTheCredit;
    }

    public void setPurposeOfTheCredit(String purposeOfTheCredit) {
        this.purposeOfTheCredit = purposeOfTheCredit;
    }

    public String getReportsContractDataCreditor() {
        return reportsContractDataCreditor;
    }

    public void setReportsContractDataCreditor(String reportsContractDataCreditor) {
        this.reportsContractDataCreditor = reportsContractDataCreditor;
    }

    public String getRoleOfConnectedSubject() {
        return roleOfConnectedSubject;
    }

    public void setRoleOfConnectedSubject(String roleOfConnectedSubject) {
        this.roleOfConnectedSubject = roleOfConnectedSubject;
    }

    public long getTypesContractDatesStart() {
        return typesContractDatesStart;
    }

    public void setTypesContractDatesStart(long typesContractDatesStart) {
        this.typesContractDatesStart = typesContractDatesStart;
    }

    public long getTypesContractDatesExpectedEnd() {
        return typesContractDatesExpectedEnd;
    }

    public void setTypesContractDatesExpectedEnd(long typesContractDatesExpectedEnd) {
        this.typesContractDatesExpectedEnd = typesContractDatesExpectedEnd;
    }

    public long getReportsLastUpdate() {
        return reportsLastUpdate;
    }

    public void setReportsLastUpdate(long reportsLastUpdate) {
        this.reportsLastUpdate = reportsLastUpdate;
    }

    public String getPhaseOfOperation() {
        return phaseOfOperation;
    }

    public void setPhaseOfOperation(String phaseOfOperation) {
        this.phaseOfOperation = phaseOfOperation;
    }

}
