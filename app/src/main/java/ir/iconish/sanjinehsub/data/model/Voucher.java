package ir.iconish.sanjinehsub.data.model;

/**
 * @author s.shaloudegi
 * @date 7/31/2019
 */
public class Voucher {


    long id;
    String description;

    public Voucher() {
    }

    public Voucher(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
