package ir.iconish.sanjinehsub.data.model;

/**
 * @author s.shaloudegi
 * @date 8/21/2019
 */
public class NewsItem {
    private int id;
    private String title;
    private String decribtion;
    private String imgUrl;
    private int drawbleId;


    public String getDecribtion() {
        return decribtion;
    }

    public void setDecribtion(String decribtion) {
        this.decribtion = decribtion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawbleId() {
        return drawbleId;
    }

    public void setDrawbleId(int drawbleId) {
        this.drawbleId = drawbleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
