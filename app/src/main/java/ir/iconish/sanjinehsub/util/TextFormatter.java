package ir.iconish.sanjinehsub.util;

import androidx.annotation.Nullable;

public class TextFormatter {
    @Nullable
    public static   String applyThousandSeparators(int money) {
        String s = null;
        try {
            s = String.format("%,d",money);
        } catch (NumberFormatException e) {

        }
        return s;
    }
}
