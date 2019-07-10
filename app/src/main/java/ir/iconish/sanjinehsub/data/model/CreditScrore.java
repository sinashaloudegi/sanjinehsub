package ir.iconish.sanjinehsub.data.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class CreditScrore {

    private String riskGrade;
    private String riskGradeTitle;
    private String reason;
    private String resoneDesc;
    private String scoreRenge;
    private Person person;
private List<SummaryContract> contractList;
private List<Inquiry> inquiryList;
private  FullContract fullContract;
private Map<FullInstallment,List<DetailInstallment>> fullInstallmentListMap;


private List<TerminateContract> terminateContracts;
    public Map<FullInstallment, List<DetailInstallment>> getFullInstallmentListMap() {
        return fullInstallmentListMap;
    }

    public void setFullInstallmentListMap(Map<FullInstallment, List<DetailInstallment>> fullInstallmentListMap) {
        this.fullInstallmentListMap = fullInstallmentListMap;
    }

    public List<TerminateContract> getTerminateContracts() {
        return terminateContracts;
    }

    public void setTerminateContracts(List<TerminateContract> terminateContracts) {
        this.terminateContracts = terminateContracts;
    }

    public FullContract getFullContract() {
        return fullContract;
    }

    public void setFullContract(FullContract fullContract) {
        this.fullContract = fullContract;
    }

    public List<Inquiry> getInquiryList() {
        return inquiryList;
    }

    public void setInquiryList(List<Inquiry> inquiryList) {
        this.inquiryList = inquiryList;
    }

    public List<SummaryContract> getContractList() {
        return contractList;
    }

    public void setContractList(List<SummaryContract> contractList) {
        this.contractList = contractList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getRiskGradeTitle() {
        return riskGradeTitle;
    }

    public void setRiskGradeTitle(String riskGradeTitle) {
        this.riskGradeTitle = riskGradeTitle;
    }


    public String getScoreRenge() {
        return scoreRenge;
    }

    public void setScoreRenge(String scoreRenge) {
        this.scoreRenge = scoreRenge;
    }

    private int icsScore;




    public String getRiskGrade() {
        return riskGrade;
    }

    public void setRiskGrade(String riskGrade) {
        this.riskGrade = riskGrade;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResoneDesc() {
        return resoneDesc;
    }

    public void setResoneDesc(String resoneDesc) {
        this.resoneDesc = resoneDesc;
    }


    public int getIcsScore() {
        return icsScore;
    }

    public void setIcsScore(int icsScore) {
        this.icsScore = icsScore;
    }

    @NonNull
    @Override
    public String toString() {
        return "CreditScrore{" +
                "riskGrade='" + riskGrade + '\'' +
                ", riskGradeTitle='" + riskGradeTitle + '\'' +
                ", reason='" + reason + '\'' +
                ", resoneDesc='" + resoneDesc + '\'' +
                ", scoreRenge='" + scoreRenge + '\'' +
                ", person=" + person +
                ", icsScore=" + icsScore +
                '}';
    }
}
