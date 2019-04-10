package ir.iconish.sanjinehsub.data.model;

public  enum ResponseCodeEnum {
    UnknownError(0, " خطا"),
    INTERNAL_ERROR(10, "بروز خطا"),
    BAD_REQUEST(20, "ورودی های خود را کنترل کنید"),
    LoginSuccessfully(1000, " ورود  به حساب موفقیت آمیز"),
    TokenNull(1001, "توکن خالی"),
    UserNull(1002, "کاربر null است"),
    AccountNull(1003, "حساب کاربری ندارد"),
    LoginFail(1004, "خطا در ورود به حساب"),
    InvalidToken(1005, "توکن نامعتبر است"),
    InvalidRole(1006, "نقش نامعتبر است"),
    Register_Fail(1007, "خطا در ثبت نام"),
    INCORRECT_PASSWORD(1008, "پسورد اشتباه است"),
    NO_SUPPORT_VAS_OPERATOR(1009, "اپراتور پشتیبانی نمیشود "),
    USER_EXIST(1010, "کاربر موجود است"),
    USERISNEW(1011, "کاربر جدید  "),
    VERIFY_SUCCESS_AND_EXIST(1012, "VERIFY_SUCCESS_AND_EXIST    "),
    VERIFY_SUCCESS_AND_NEW(1013, "VERIFY_SUCCESS_AND_NEW    "),
    VERIFY_FAIL(1014, "  VERIFY_FAIL  "),
    Success(9999, "درخواست موفقیت آمیز");

    private int value;
    private String name;

    private ResponseCodeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public static ResponseCodeEnum fromValue(int val) {
        switch(val) {
            case 0:
                return UnknownError;
            case 10:
                return INTERNAL_ERROR;
            case 20:
                return BAD_REQUEST;
            case 1000:
                return LoginSuccessfully;
            case 1001:
                return TokenNull;
            case 1002:
                return UserNull;
            case 1003:
                return AccountNull;
            case 1004:
                return LoginFail;
            case 1005:
                return InvalidToken;
            case 1006:
                return InvalidRole;
            case 1007:
                return Register_Fail;
            case 1008:
                return INCORRECT_PASSWORD;
            case 1009:
                return NO_SUPPORT_VAS_OPERATOR;
            case 1010:
                return USER_EXIST;
            case 1011:
                return USERISNEW;
            case 1012:
                return VERIFY_SUCCESS_AND_EXIST;
            case 1013:
                return VERIFY_SUCCESS_AND_NEW;
            case 1014:
                return VERIFY_FAIL;
            case 9999:
                return Success;
            default:
                return INTERNAL_ERROR;
        }
    }

    public String getDescr() {
        return this.name;
    }

    public String getName() {
        return fromValue(this.value).toString();
    }
}