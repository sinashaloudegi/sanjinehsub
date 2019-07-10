package ir.iconish.sanjinehsub.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum ReportStateEnum {
    START(1, "ثبت درخواست"),
    NOT_AVAILABLE_REPORT(2, "گزارش موجود نیست"),
    NOT_VALID_NTCODE(3, "  کد ملی نامعتبر است"),
    NO_MATCH_MOBILE(4, "موبایل متعلق به این کد ملی نیست"),
    WAITE_PAY(5, "منتظر پرداخت "),
    PAY_SUCCESS(6, "پرداخت موفقیت آمیز"),
    REPORT_NOT_RECIVED(7, "گزارشی دریافت نشد "),
    FINISH(8, "گزارش ارسال شد"),
    SEND_REPORT_FAIL(9, " خطا در ارسال گزارش"),
    PAY_FAIL(10, " خطا در پرداخت"),
    VERIFY_CODE_NOT_VALID(11, "   verify code  نامعتبر"),
    SEND_VERIFY_CODE(12, "   verify code  ارسال"),
    WAITE_FOR_OWNERMOBILE(13, " در انتظار دریافت شماره تلفن مالک "),
    SEND_OTP_PAY_MCI(14, "  ارسال otp پرداخت"),
    REPORT_COUNT_LIMITED(15, "تعداد گزارش محدود میباشد "),
    GOTO_IPG(16, "انتقال به درگاه "),
    NTCODE_NOT_VALID(17, "ntcode اشتباه "),
    MOBILE_NOT_VALID(18, "mobile اشتباه "),
    SEND_OTP_PAY_MTN(19, "SEND_OTP_PAY_MTN "),
    SEND_OTP_PAY_RIGHTEL(20, "SEND_OTP_PAY_RIGHTEL"),
    USER_HAVE_COMPLETE_REPORT(21, "کاربر دارای گزارش است"),
    USER_NOT_HAVE_REPORT(22, "کاربر گزارشی ندارد"),
    USER_HAVE_SIMPLE_XXX_REPORT(23, "کاربر گزارش XXX ساده دارد"),
    USER_HAVE_COMPLETE_XXX_REPORT(24, "کاربر گزارش XXX کامل دارد"),
    SHAHKAR_NOT_RESPONSE(25, "پاسخی از شاهکار دریافت نشد");


    private String value;
    private Integer id;

    ReportStateEnum(Integer id, String value) {
        this.value = value;
        this.id = id;
    }

    @Nullable
    public static ReportStateEnum fromValue(Integer value) {
        if (value == 1) {
            return ReportStateEnum.START;
        } else if (value == 2) {
            return ReportStateEnum.NOT_AVAILABLE_REPORT;
        } else if (value == 3) {
            return ReportStateEnum.NOT_VALID_NTCODE;
        } else if (value == 4) {
            return ReportStateEnum.NO_MATCH_MOBILE;
        } else if (value == 5) {
            return ReportStateEnum.WAITE_PAY;
        } else if (value == 6) {
            return ReportStateEnum.PAY_SUCCESS;
        } else if (value == 7) {
            return ReportStateEnum.REPORT_NOT_RECIVED;
        } else if (value == 8) {
            return ReportStateEnum.FINISH;
        } else if (value == 9) {
            return ReportStateEnum.SEND_REPORT_FAIL;
        } else if (value == 10) {
            return ReportStateEnum.PAY_FAIL;
        } else if (value == 11) {
            return ReportStateEnum.VERIFY_CODE_NOT_VALID;
        } else if (value == 12) {
            return ReportStateEnum.SEND_VERIFY_CODE;
        } else if (value == 13) {
            return ReportStateEnum.WAITE_FOR_OWNERMOBILE;
        } else if (value == 14) {
            return ReportStateEnum.SEND_OTP_PAY_MCI;
        } else if (value == 15) {
            return ReportStateEnum.REPORT_COUNT_LIMITED;
        } else if (value == 16) {
            return ReportStateEnum.GOTO_IPG;
        } else if (value == 17) {
            return ReportStateEnum.NTCODE_NOT_VALID;
        } else if (value == 18) {
            return ReportStateEnum.MOBILE_NOT_VALID;
        } else if (value == 19) {
            return ReportStateEnum.SEND_OTP_PAY_MTN;
        } else if (value == 20) {
            return ReportStateEnum.SEND_OTP_PAY_RIGHTEL;
        }else if (value == 21) {
            return ReportStateEnum.USER_HAVE_COMPLETE_REPORT;
        }else if (value == 22) {
            return ReportStateEnum.USER_NOT_HAVE_REPORT;
        }else if (value == 23) {
            return ReportStateEnum.USER_HAVE_SIMPLE_XXX_REPORT;
        }else if (value == 24) {
            return ReportStateEnum.USER_HAVE_COMPLETE_XXX_REPORT;
        }else if (value == 25) {
            return ReportStateEnum.SHAHKAR_NOT_RESPONSE;
        } else {
            return null;
        }

    }

    public String getValue() {
        return value;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.value;
    }

}
