package com.visit24.therapist.util;

public class TextFormatter {
    public static   String applyThousandSeparators(int money) {
        String s = null;
        try {
            s = String.format("%,d",money);
        } catch (NumberFormatException e) {

        }
        return s;
    }
}
