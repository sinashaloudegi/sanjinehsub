package ir.iconish.sanjinehsub.data.model;

import androidx.annotation.NonNull;

public class AppConfig {


    private int marketEnumId;
    private String marketEnumString;
    private String marketKey;
    private int timerDuration;

    public int getMarketEnumId() {
        return marketEnumId;
    }

    public void setMarketEnumId(int marketEnumId) {
        this.marketEnumId = marketEnumId;
    }

    public String getMarketEnumString() {
        return marketEnumString;
    }

    public void setMarketEnumString(String marketEnumString) {
        this.marketEnumString = marketEnumString;
    }

    public String getMarketKey() {
        return marketKey;
    }

    public void setMarketKey(String marketKey) {
        this.marketKey = marketKey;
    }

    public int getTimerDuration() {
        return timerDuration;
    }

    public void setTimerDuration(int timerDuration) {
        this.timerDuration = timerDuration;
    }

    @NonNull
    @Override
    public String toString() {
        return "AppConfig{" +
                "marketEnumId=" + marketEnumId +
                ", marketEnumString='" + marketEnumString + '\'' +
                ", marketKey='" + marketKey + '\'' +
                ", timerDuration=" + timerDuration +
                '}';
    }
}
