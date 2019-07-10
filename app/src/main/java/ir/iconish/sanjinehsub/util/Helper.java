package ir.iconish.sanjinehsub.util;

import androidx.annotation.NonNull;

public class Helper {
    public static boolean validationNationalCode(@NonNull String code) {
        //check length
        long nationalCode = Long.parseLong(code);
        byte[] arrayNationalCode = new byte[10];

        //extract digits from number
        for (int i = 0; i < 10 ; i++) {
            arrayNationalCode[i] = (byte) (nationalCode % 10);
            nationalCode = nationalCode / 10;
        }

        //Checking the control digit
        int sum = 0;
        for (int i = 9; i > 0; i--) {
            sum += arrayNationalCode[i] * (i+1);
        }
        int temp = sum % 11;
        if (temp < 2) {
            return arrayNationalCode[0] == temp;
        } else {
            return arrayNationalCode[0] == 11 - temp;
        }
    }
}
