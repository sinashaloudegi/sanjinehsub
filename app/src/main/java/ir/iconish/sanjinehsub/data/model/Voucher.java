package ir.iconish.sanjinehsub.data.model;

/**
 * @author s.shaloudegi
 * @date 7/31/2019
 */
public class Voucher {


    String serviceActionId;
    String description;

    public String getServiceActionId() {
        return serviceActionId;
    }

    public void setServiceActionId(String serviceActionId) {
        this.serviceActionId = serviceActionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Voucher{" +
                "serviceActionId=" + serviceActionId +
                ", description='" + description + '\'' +
                '}';
    }
}
