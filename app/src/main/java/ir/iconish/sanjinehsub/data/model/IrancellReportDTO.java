package ir.iconish.sanjinehsub.data.model;

/**
 * @author s.shaloudegi
 * @date 11/13/2019
 */
public class IrancellReportDTO {
    String mobile;
    String otherMobile;
    String ntCode;

    int personTypeId;


    public IrancellReportDTO() {
    }

    public IrancellReportDTO(String mobile, String otherMobile, String ntCode, int personTypeId) {
        this.mobile = mobile;
        this.otherMobile = otherMobile;
        this.ntCode = ntCode;
        this.personTypeId = personTypeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtherMobile() {
        return otherMobile;
    }

    public void setOtherMobile(String otherMobile) {
        this.otherMobile = otherMobile;
    }

    public String getNtCode() {
        return ntCode;
    }

    public void setNtCode(String ntCode) {
        this.ntCode = ntCode;
    }

    public int getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(int personTypeId) {
        this.personTypeId = personTypeId;
    }


    @Override
    public String toString() {
        return "IrancellReportDTO{" +
                "mobile='" + mobile + '\'' +
                ", otherMobile='" + otherMobile + '\'' +
                ", ntCode='" + ntCode + '\'' +
                ", personTypeId=" + personTypeId +
                ", getPersonTypeId=" + +
                '}';
    }
}
