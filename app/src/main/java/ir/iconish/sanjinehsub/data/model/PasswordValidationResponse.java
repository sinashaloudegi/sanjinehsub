package ir.iconish.sanjinehsub.data.model;

public class PasswordValidationResponse {

    private int respobseStatusCode;
    private String descryptions;
    private String token;





    public int getRespobseStatusCode() {
        return respobseStatusCode;
    }

    public void setRespobseStatusCode(int respobseStatusCode) {
        this.respobseStatusCode = respobseStatusCode;
    }

    public String getDescryptions() {
        return descryptions;
    }

    public void setDescryptions(String descryptions) {
        this.descryptions = descryptions;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
