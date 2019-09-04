package ir.iconish.sanjinehsub.data.model;

/**
 * @author s.shaloudegi
 * @date 8/31/2019
 */
public class SearchResult {
    private int id;
    private String title;

    public SearchResult(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
