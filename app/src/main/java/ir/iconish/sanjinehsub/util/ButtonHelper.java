package com.visit24.therapist.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;

public class ButtonHelper {

    public static void toggleButtonStatus(Button button,boolean enable) {

        if (enable)
            button.getBackground().setColorFilter(null);
        else
        button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);


        button.setEnabled(enable);

    }


}
