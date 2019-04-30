package ir.iconish.sanjinehsub.data.model;
public enum CreditResponseEnum {
    SUCCESS(100L, "موفقیت آمیز"),
    NOT_MATCH_MOBILE(101L, "موبایل متعلق به این کد ملی نیست"),
    FAIL_SEND_OTP(102L, "خطا در ارسال OTP"),
    NOT_VALID_VERIFYCODE(103L, "کد اعتبار سنجی دریافتی نامعتبر است"),
    SEND_OTP_PAY_MCI(104L, "در انتظار دریافت OTP از کاربر"),
    WALLET_PAY_FAIL(105L, "خطا در پرداخت از اعتبار  "),
    NO_REPORT(106L, "گزارشی یافت نشد "),
    LIMITED_COUNT(107L, "تعداد گزارش محدود میباشد "),
    GOTO_IPG(108L, "انتقال به درگاه "),
    OTP_VALID(109L, "otp صحیح "),
    OTP_NOT_VALID(110L, "otp اشتباه "),
    NTCODE_NOT_VALID(111L, "ntcode اشتباه "),
    MOBILE_NOT_VALID(112L, "mobile اشتباه "),
    NOT_FOUND_REQUEST(113L, "درخواست شما یافت نشد "),
    WAITING_PAYMENT(114L, "منتظر پرداخت سیمکارت "),
    SEND_OTP_PAY_MTN(115L, "در انتظار دریافت OTP از طریق sms"),
    SEND_OTP_PAY_RIGHTEL(116L, "در انتظار دریافت OTP از طریق sms"),
    EXCEPTION_OCCURED(117L, "EXCEPTION_OCCURED"),
    MOBILE_MATCH(118L, "موبایل متعلق به این کد ملی است"),
    SHAHKAR_NOT_RESPONSE(119L, "سرویس شاهکار پاسخ نمیدهد");


    private String value;
    private Long id;

    CreditResponseEnum(Long id, String value) {
        this.value = value;
        this.id = id;
    }

    public static CreditResponseEnum fromValue(Long value) {
        if (value == 100L) {
            return CreditResponseEnum.SUCCESS;
        } else if (value == 101L) {
            return CreditResponseEnum.NOT_MATCH_MOBILE;
        } else if (value == 102L) {
            return CreditResponseEnum.FAIL_SEND_OTP;
        } else if (value == 103L) {
            return CreditResponseEnum.NOT_VALID_VERIFYCODE;
        } else if (value == 104L) {
            return CreditResponseEnum.SEND_OTP_PAY_MCI;
        } else if (value == 105L) {
            return CreditResponseEnum.WALLET_PAY_FAIL;
        } else if (value == 106L) {
            return CreditResponseEnum.NO_REPORT;
        } else if (value == 107L) {
            return CreditResponseEnum.LIMITED_COUNT;
        } else if (value == 108L) {
            return CreditResponseEnum.GOTO_IPG;
        } else if (value == 109L) {
            return CreditResponseEnum.OTP_VALID;
        } else if (value == 110L) {
            return CreditResponseEnum.OTP_NOT_VALID;
        } else if (value == 111L) {
            return CreditResponseEnum.NTCODE_NOT_VALID;
        } else if (value == 112L) {
            return CreditResponseEnum.MOBILE_NOT_VALID;
        } else if (value == 113L) {
            return CreditResponseEnum.NOT_FOUND_REQUEST;
        } else if (value == 114L) {
            return CreditResponseEnum.WAITING_PAYMENT;
        } else if (value == 115L) {
            return CreditResponseEnum.SEND_OTP_PAY_MTN;
        } else if (value == 116L) {
            return CreditResponseEnum.SEND_OTP_PAY_RIGHTEL;
        } else if (value == 117L) {
            return CreditResponseEnum.EXCEPTION_OCCURED;
        } else if (value == 118L) {
            return CreditResponseEnum.MOBILE_MATCH;
        } else if (value == 119L) {
            return CreditResponseEnum.SHAHKAR_NOT_RESPONSE;
        } else {
            return null;
        }

    }

    public String getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

    public String toString() {
        return this.id.toString();
    }
}
