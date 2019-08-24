package ir.iconish.sanjinehsub.data.model;

/**
 * @author s.shaloudegi
 * @date 8/13/2019
 */
public class CoinPrice {

    String name;
    String rate;
    int[] date;
    int[] price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int[] getDate() {
        return date;
    }

    public void setDate(int[] date) {
        this.date = date;
    }

    public int[] getPrice() {
        return price;
    }

    public void setPrice(int[] price) {
        this.price = price;
    }
}
