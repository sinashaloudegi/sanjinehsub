package ir.iconish.sanjinehsub.data.model;

import androidx.annotation.NonNull;

/**
 * @author s.shaloudegi
 * @date 7/3/2019
 */
public class NumberOfSanjineh {

    int balance;
    int unitValue;

    public int getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(int unitValue) {
        this.unitValue = unitValue;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @NonNull
    @Override
    public String toString() {
        return "NumberOfSanjineh{" +
                "balance=" + balance +
                ", unitValue=" + unitValue +
                '}';
    }
}

