package ir.iconish.sanjinehsub.data.source.api;

import androidx.annotation.NonNull;

public class ConstantUrl {
    //public static String BASE="http://192.168.110.43:1033/";
    public static final String LOGIN = "v1/register/register/";
    public static final String CHECK_PASSWORD = "v1/register/login";
    public static final String GET_BALANCE = "cafebazaar/user/balance/";
    public static final String FORGET_PASSWORD = "security/forgetpassword";
    public static final String CONFIRM_REGISTER = "v1/security/confirmregister";
    public static final String CHECK_REPORT = "icredit/getavailablereport/";
    public static final String REGISTER_PURCHASEINFO = "cafebazaar/registerpurchaseinfo";
    // public static final String SEND_VERIFYCODE = "icredit/v1/sendVerifyCode";
    public static final String SEND_VERIFYCODE = "cafebazaar/verifycode";
    public static final String Confirm_VERIFYCODE = "icredit/confirmverifycode/";
    public static final String CHANGHE_PASSWORD = "security/changepassword";
    public static final String APP_CONFIG = "cafebazaar/config";
    public static final String ARCHIVE = "icredit/allarchivedreport/";
    public static final String REPORTS = "icredit/v1/getarchivedbytoken";
    //public static String BASE="http://192.168.110.43:1033/";
    @NonNull
    public static String BASE = "http://security.iconish.ir/";
    //public static String BASE_CREDIT="http://192.168.110.43:1043/";
    @NonNull
    public static String BASE_CREDIT = "http://creditscore.iconish.ir/";
    //public static String BASE_MARKET="http://market.iconish.ir:8085/";
//    public static String BASE_MARKET = "http://192.168.110.55:6065/";
//    @NonNull
    public static String BASE_MARKET = "http://market.iconish.ir/";


}
