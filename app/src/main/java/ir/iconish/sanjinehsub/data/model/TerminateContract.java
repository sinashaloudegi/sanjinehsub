package ir.iconish.sanjinehsub.data.model;

import java.util.List;

public class TerminateContract {

    private String negativeContractStatus;
    private String typeOfFinancingInstalments;
    private String purposeOfTheCredit;
    private long typesContractDatesStart;
    private long typesContractDatesExpectedEnd;
    private long typesContractDatesRealEnd;
    private String currency;
    private String roleOfConnectedSubject;
    private String reportsContractDataCreditor;
    private String periodicityOfPayments;
    private String methodOfPayment;
    private String typeOfInstalments;

    private String collateralType;
    private String collateralAmount;
    private  int relationsAmountsOverdue;
    private  int relationsAmountsOutstanding;
    private  int relationsAmountsTotalCredit;
    private  int relationsAmountsStandardPeriodicalInstalment;
    private  int contractNumberOfInstalments;

    public String getCollateralType() {
        return collateralType;
    }

    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    public String getCollateralAmount() {
        return collateralAmount;
    }

    public void setCollateralAmount(String collateralAmount) {
        this.collateralAmount = collateralAmount;
    }

    public String getNegativeContractStatus() {
        return negativeContractStatus;
    }

    public void setNegativeContractStatus(String negativeContractStatus) {
        this.negativeContractStatus = negativeContractStatus;
    }

    public String getTypeOfFinancingInstalments() {
        return typeOfFinancingInstalments;
    }

    public void setTypeOfFinancingInstalments(String typeOfFinancingInstalments) {
        this.typeOfFinancingInstalments = typeOfFinancingInstalments;
    }

    public String getPurposeOfTheCredit() {
        return purposeOfTheCredit;
    }

    public void setPurposeOfTheCredit(String purposeOfTheCredit) {
        this.purposeOfTheCredit = purposeOfTheCredit;
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

    public long getTypesContractDatesRealEnd() {
        return typesContractDatesRealEnd;
    }

    public void setTypesContractDatesRealEnd(long typesContractDatesRealEnd) {
        this.typesContractDatesRealEnd = typesContractDatesRealEnd;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRoleOfConnectedSubject() {
        return roleOfConnectedSubject;
    }

    public void setRoleOfConnectedSubject(String roleOfConnectedSubject) {
        this.roleOfConnectedSubject = roleOfConnectedSubject;
    }

    public String getReportsContractDataCreditor() {
        return reportsContractDataCreditor;
    }

    public void setReportsContractDataCreditor(String reportsContractDataCreditor) {
        this.reportsContractDataCreditor = reportsContractDataCreditor;
    }

    public String getPeriodicityOfPayments() {
        return periodicityOfPayments;
    }

    public void setPeriodicityOfPayments(String periodicityOfPayments) {
        this.periodicityOfPayments = periodicityOfPayments;
    }

    public String getMethodOfPayment() {
        return methodOfPayment;
    }

    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public String getTypeOfInstalments() {
        return typeOfInstalments;
    }

    public void setTypeOfInstalments(String typeOfInstalments) {
        this.typeOfInstalments = typeOfInstalments;
    }



    public int getRelationsAmountsOverdue() {
        return relationsAmountsOverdue;
    }

    public void setRelationsAmountsOverdue(int relationsAmountsOverdue) {
        this.relationsAmountsOverdue = relationsAmountsOverdue;
    }

    public int getRelationsAmountsOutstanding() {
        return relationsAmountsOutstanding;
    }

    public void setRelationsAmountsOutstanding(int relationsAmountsOutstanding) {
        this.relationsAmountsOutstanding = relationsAmountsOutstanding;
    }

    public int getRelationsAmountsTotalCredit() {
        return relationsAmountsTotalCredit;
    }

    public void setRelationsAmountsTotalCredit(int relationsAmountsTotalCredit) {
        this.relationsAmountsTotalCredit = relationsAmountsTotalCredit;
    }

    public int getRelationsAmountsStandardPeriodicalInstalment() {
        return relationsAmountsStandardPeriodicalInstalment;
    }

    public void setRelationsAmountsStandardPeriodicalInstalment(int relationsAmountsStandardPeriodicalInstalment) {
        this.relationsAmountsStandardPeriodicalInstalment = relationsAmountsStandardPeriodicalInstalment;
    }

    public int getContractNumberOfInstalments() {
        return contractNumberOfInstalments;
    }

    public void setContractNumberOfInstalments(int contractNumberOfInstalments) {
        this.contractNumberOfInstalments = contractNumberOfInstalments;
    }
}
