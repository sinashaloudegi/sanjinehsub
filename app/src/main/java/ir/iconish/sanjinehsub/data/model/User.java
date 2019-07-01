package ir.iconish.sanjinehsub.data.model;

public class User {
    private LoginStatusEnum responseCodeEnum;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private long userId;

    public LoginStatusEnum getResponseCodeEnum() {
        return responseCodeEnum;
    }

    public void setResponseCodeEnum(LoginStatusEnum responseCodeEnum) {
        this.responseCodeEnum = responseCodeEnum;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


}
