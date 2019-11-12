package ir.iconish.sanjinehsub.data.model;

import androidx.annotation.NonNull;

/**
 * @author s.shaloudegi
 * @date 11/12/2019
 */
public enum StoreEnum {

    CAFEBAZAAR(0, "CAFEBAZAAR"),

    CHARKHOONE(1, "CHARKHOONE");

    private int key;
    private String value;

    StoreEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    @NonNull
    public static StoreEnum fromValue(int key) {
        switch (key) {
            case 0:
                return CAFEBAZAAR;
            case 1:
                return CHARKHOONE;

        }
        return CAFEBAZAAR;
    }

    public String getValue() {
        return this.value;
    }

    public int getKey() {
        return this.key;
    }


}
