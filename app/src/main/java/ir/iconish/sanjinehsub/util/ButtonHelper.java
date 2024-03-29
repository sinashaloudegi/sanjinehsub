package ir.iconish.sanjinehsub.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;

import androidx.annotation.NonNull;

public class ButtonHelper {

    public static void toggleButtonStatus(@NonNull Button button, boolean enable) {

        if (enable) {
            button.getBackground().setColorFilter(null);
        } else {
            button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }


        button.setEnabled(enable);

    }

    public static void toggleAppCompatButtonStatus(@NonNull Button button, boolean enable) {

        if (enable) {
            button.getBackground().setColorFilter(null);
        } else {
            button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }


        button.setEnabled(enable);

    }




}
