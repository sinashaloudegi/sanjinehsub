package ir.iconish.sanjinehsub.data.model;

import androidx.annotation.NonNull;

public class Inquiry {

    private String  lookupsSubscriberType;
    private String  key;
    private int last1Month;
    private int last1Year;

    public String getLookupsSubscriberType() {
        return lookupsSubscriberType;
    }

    public void setLookupsSubscriberType(String lookupsSubscriberType) {
        this.lookupsSubscriberType = lookupsSubscriberType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getLast1Month() {
        return last1Month;
    }

    public void setLast1Month(int last1Month) {
        this.last1Month = last1Month;
    }

    public int getLast1Year() {
        return last1Year;
    }

    public void setLast1Year(int last1Year) {
        this.last1Year = last1Year;
    }

    @NonNull
    @Override
    public String toString() {
        return "Inquiry{" +
                "lookupsSubscriberType='" + lookupsSubscriberType + '\'' +
                ", key='" + key + '\'' +
                ", last1Month=" + last1Month +
                ", last1Year=" + last1Year +
                '}';
    }
}
