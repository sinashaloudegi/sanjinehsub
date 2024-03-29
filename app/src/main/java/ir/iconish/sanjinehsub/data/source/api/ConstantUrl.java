package ir.iconish.sanjinehsub.data.source.api;

import androidx.annotation.NonNull;

public class ConstantUrl {
    public static final String LOGIN = "v1/register/register/";
    public static final String CHECK_PASSWORD = "v1/register/login";
    public static final String GET_BALANCE = "cafebazaar/user/balance/";
    public static final String FORGET_PASSWORD = "security/forgetpassword";
    public static final String CONFIRM_REGISTER = "v1/security/confirmregister";
    public static final String CHECK_REPORT = "icredit/getavailablereport/";
    // TODO: 11/12/2019 cafe-charkhone
    public static final String CAFEBAZAAR_REGISTERPURCHASEINFO = "cafebazaar/registerpurchaseinfo";
    public static final String CHARKHOONE_REGISTERPURCHASEINFO = "irancell/subscribe";
    // public static final String SEND_VERIFYCODE = "icredit/v1/sendVerifyCode";
    public static final String SEND_VERIFYCODE = "cafebazaar/verifycode";
    public static final String Confirm_VERIFYCODE = "icredit/confirmverifycode/";
    public static final String CHANGHE_PASSWORD = "security/changepassword";
    // TODO: 11/12/2019 cafe-charkhone
    public static final String CAFEBAZAAR_CONFIG = "cafebazaar/config";
    public static final String CHARKHOONE_CONFIG = "charkhoone/config";

    public static final String ARCHIVE = "icredit/allarchivedreport/";
    public static final String REPORTS = "icredit/v1/getarchivedbytoken";
    public static final String CONTENT = "content/getallarticle";
    public static final String KHOSHHESABAN_PROFILE = "khoshhesaban/getkhoshhesabanprofile";

    //_______________________________________________________________________________________//

    //public static String BASE_SECURITY="http://192.168.110.43:1033/";
    @NonNull
    public static String BASE_SECURITY = "http://security.iconish.ir/";

    //_______________________________________________________________________________________//

    //public static String BASE_CREDITSCORE="http://192.168.110.43:1043/";
    @NonNull
    public static String BASE_CREDITSCORE = "http://creditscore.iconish.ir/";

    //_______________________________________________________________________________________//

    //     public static String BASE_MARKET = "http://192.168.110.55:6065/";
    @NonNull
    public static String BASE_MARKET = "http://market.iconish.ir/";


    //_______________________________________________________________________________________//

    @NonNull
    public static String BASE_CONTENT = "http://content.iconish.ir/";


    //_______________________________________________________________________________________//

    @NonNull
    public static String BASE_KHOSHHESABAN = "http://topup.iconish.ir/voucher/suggestion/11";


}
