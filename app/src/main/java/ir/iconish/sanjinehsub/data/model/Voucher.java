package ir.iconish.sanjinehsub.data.model;

/**
 * @author s.shaloudegi
 * @date 7/31/2019
 */
public class Voucher {


    long id;
    String name;
    String score;
    String prizeTypeId;
    String price;
    String unitId;
    String discount;
    String imageUrl;
    String description;


    public Voucher() {
    }

    public Voucher(long id, String name, String score, String prizeTypeId, String price, String unitId, String discount, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.prizeTypeId = prizeTypeId;
        this.price = price;
        this.unitId = unitId;
        this.discount = discount;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPrizeTypeId() {
        return prizeTypeId;
    }

    public void setPrizeTypeId(String prizeTypeId) {
        this.prizeTypeId = prizeTypeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
