package ir.iconish.sanjinehsub.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;

public class ButtonHelper {

    public static void toggleButtonStatus(Button button,boolean enable) {

        if (enable)
            button.getBackground().setColorFilter(null);
        else
        button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);


        button.setEnabled(enable);

    }

     public static void toggleAppCompatButtonStatus(AppCompatButton button, boolean enable) {

        if (enable)
            button.getBackground().setColorFilter(null);
        else
            button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);


        button.setEnabled(enable);

    }




}
