package ir.iconish.sanjinehsub.util;

/**
 * Created by mm on 3/6/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;




import java.lang.reflect.Field;

import ir.iconish.sanjinehsub.R;

public final class FontsOverride {

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {






        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), fontAssetName);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
        }






        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void setToolbarFont(Activity activity, CollapsingToolbarLayout collapsingToolbarLayout){
        Typeface tf = ResourcesCompat.getFont(activity, R.font.iranyekanwebregular);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
    }
}